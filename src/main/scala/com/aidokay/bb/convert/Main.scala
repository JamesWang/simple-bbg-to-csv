package com.aidokay.bb.convert

import com.aidokay.bb.convert.OutputCreator.*
import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.direct.defer
import zio.direct.run

object Main extends ZIOAppDefault {

  val file = "src/main/resources/example.bbg"
  private val myApp: ZIO[FileOutputCreator with FileDataLoader, Throwable, Unit] =
    defer:
      val loader = ZIO.service[FileDataLoader].run
      val writer = ZIO.service[FileOutputCreator].run
      val list   = loader.load(file).run
      val l1     = list.zipWithIndex.map((s, i) => ExOutResult(i, s))
      val l2     = l1.map(_.asCsv())
      Console.printLine(l2.mkString("-\n")).run
      writer.create(l1, "C:\\temp\\out.txt").run


  def run: ZIO[Any with ZIOAppArgs, Any, Any] =
    myApp.provide(
      FileDataLoader.live,
      FileOutputCreator.live
    )
}
