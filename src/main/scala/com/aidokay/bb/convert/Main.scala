package com.aidokay.bb.convert

import com.aidokay.db.QueryRunnerService
import com.aidokay.model.BBResult
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}
object Main extends ZIOAppDefault {

  val file = "src/main/resources/example.bbg"
  private val myApp = for {
    loader <- ZIO.service[FileDataLoader]
    writer <- ZIO.service[FileOutputCreator]
    runner <- ZIO.service[QueryRunnerService[List[String], List[BBResult]]]
    list <- loader.load(file)
    results <- runner.executeWith(Some(list), result => ZIO.succeed(result.map(r => BBResult(r.id, r.product, r.isin, 1))))
    _ <- ZIO.attemptBlockingIO(writer.create(results, ""))
  } yield ()

  def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    myApp.provide(FileDataLoader.live ++ FileOutputCreator.live ++ QueryRunnerService.live)
}
