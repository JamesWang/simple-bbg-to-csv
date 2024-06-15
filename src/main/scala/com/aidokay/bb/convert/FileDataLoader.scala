package com.aidokay.bb.convert

import com.aidokay.bb.convert.Constants.{END_OF_DATA, START_OF_DATA}
import zio.{ULayer, ZIO, ZLayer}

import java.io.IOException
import scala.io.Source


class FileDataLoader extends DataLoader[ZIO[Any, IOException, List[String]]] {

  override def load(from: String): ZIO[Any, IOException, List[String]] = {
    val fileReader = ZIO.fromAutoCloseable(ZIO.attemptBlockingIO(Source.fromFile(from)))
    val extractLinesAlgo =
      for {
        data <- ZIO.service[List[String]]
        lines <- {
          val start = data.indexOf(START_OF_DATA)
          val end = data.indexOf(END_OF_DATA)
          ZIO.succeed(data.slice(start + 1, end).map(line => line.split("\\|")(Constants.INTEREST_INDEX)))
        }
      } yield lines
    extractLinesAlgo.provideLayer(ZLayer.scoped(fileReader.map(_.getLines().toList)))
  }
}

object FileDataLoader {
  val live: ULayer[FileDataLoader] = ZLayer.succeed[FileDataLoader](new FileDataLoader)
}