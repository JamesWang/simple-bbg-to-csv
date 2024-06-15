package com.aidokay.bb.convert

import zio.{Chunk, ULayer, ZIO, ZLayer, ZOutputStream}
import com.aidokay.bb.convert.OutputCreator.*

object FileOutputCreator {
  val live: ULayer[FileOutputCreator] = ZLayer.succeed[FileOutputCreator](new FileOutputCreator)
}
class FileOutputCreator extends OutputCreator[OutputResult] {

  private def asCsvString(data: List[OutputResult]): Array[Byte] =
    data.map(rr => rr.asCsv()).mkString("\n").getBytes

  override def create(result: List[OutputResult], file: String): Unit = {
    val fileWriter = ZIO.writeFileOutputStream(file)
    val writeAlgo =
      for {
        data <- ZIO.service[List[OutputResult]]
        writer <- ZIO.service[ZOutputStream]
        _ <- ZIO.attemptBlockingIO({
          writer.write(Chunk.fromArray(asCsvString(data)))
        })
      } yield ()
    writeAlgo.provide(ZLayer.succeed(result), ZLayer.scoped(fileWriter))
  }
}
