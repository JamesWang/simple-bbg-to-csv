package com.aidokay.bb.convert

import com.aidokay.bb.convert.Constants.{END_OF_DATA, START_OF_DATA}
import zio.{ULayer, ZIO, ZLayer}

import java.io.IOException
import scala.io.Source


trait FileDataLoader extends DataLoader[ZIO[Any, IOException, List[String]]] {

  private case class RowsOfData(data: List[String])

  override def load(from: String): ZIO[Any, IOException, List[String]] = {
    val fileReader = ZIO.fromAutoCloseable(ZIO.attemptBlockingIO(Source.fromFile(from)))
    val extractLinesAlgo =
      for {
        rows  <- ZIO.service[RowsOfData]
        lines <- {
          val start = rows.data.indexOf(START_OF_DATA)
          val end   = rows.data.indexOf(END_OF_DATA)
          ZIO.succeed(rows.data.slice(start + 1, end).map(line => line.split("\\|")(Constants.INTEREST_INDEX)))
        }
      } yield lines
    extractLinesAlgo.provideLayer(ZLayer.scoped(fileReader.map(_.getLines().toList).map(RowsOfData.apply)))
  }
}

object FileDataLoader {
  val live: ULayer[FileDataLoader] = ZLayer.succeed[FileDataLoader](new FileDataLoader{})
}