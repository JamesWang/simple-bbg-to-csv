package com.aidokay.bb.convert

import com.aidokay.bb.convert.OutputCreator.*
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault, Console}
object Main extends ZIOAppDefault {

  val file = "src/main/resources/example.bbg"
  val myApp: ZIO[FileOutputCreator with FileDataLoader, Throwable, Unit] = for {
    loader <- ZIO.service[FileDataLoader]
    //writer <- ZIO.service[FileOutputCreator]
    list <- loader.load(file)
    l2 <- ZIO.attempt(list.zipWithIndex.map((s, i) => ExOutResult(i, s)))
    l3 <- ZIO.attempt(l2.map(_.asCsv()))
    _ <- Console.printLine(l3.mkString("\n"))
  } yield ()

  def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    myApp.provide(FileDataLoader.live ++ FileOutputCreator.live)
}
