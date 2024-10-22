package com.aidokay.bb.convert

import com.aidokay.bb.convert.OutputCreator.*
import zio.{Chunk, Scope, ULayer, ZIO, ZLayer, ZOutputStream}

import java.io.IOException

object FileOutputCreator {
  val live: ULayer[FileOutputCreator] = ZLayer.succeed[FileOutputCreator](new FileOutputCreator)
}

class FileOutputCreator extends OutputCreator[OutputResult, ZIO[Any, IOException, Unit]] {

  private def asCsvString(data: List[OutputResult]): Array[Byte] =
    data.map(rr => rr.asCsv()).mkString("\n").getBytes

  override def create(result: List[OutputResult], file: String): ZIO[Any, IOException, Unit] = {
    val writeAlgo =
      for {
        writer <- ZIO.service[ZOutputStream]
        _      <- {
          val data = Chunk.fromArray(asCsvString(result))
          writer.write(data)
        }
      } yield ()
    writeAlgo.provide(ZLayer.scoped(ZIO.writeFileOutputStream(file)))
  }
}
