package com.aidokay.db

import cats.effect.IO
import com.aidokay.model.QueryResult
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import zio.{UIO, ZIO, ZLayer}
import com.aidokay.model.asJsonStr

object QueryRunnerShow:
  val live = ZLayer.succeed[QueryRunnerShow[List[String]]](new QueryRunnerShow[List[String]] with InMemQuery)

class QueryRunnerShow[I] extends QueryRunner[I, QueryResult] {
  self: Querier[I, UIO] {type T = List[QueryResult]} =>
  type Aux = UIO[List[String]]

  def execute(params: Option[I]): UIO[List[String]] =
    executeWith(params, resultAsJson)

  def executeWith(params: Option[I], handler: List[QueryResult] => Aux): Aux =
    prepareWith(params).flatMap(handler)

  private def resultAsJson(result: List[QueryResult]): UIO[List[String]] =
    import io.circe.Decoder.decodeJson
    import io.circe.syntax.*

    if (result.nonEmpty) {
      given resultEncoder: Encoder[QueryResult] = deriveEncoder[QueryResult]

      given resultsEncoder: Encoder[List[QueryResult]] = deriveEncoder[List[QueryResult]]

      println(result.asJson.noSpaces)
      ZIO.succeed(result.map(_.asJsonStr()).map(x => decodeJson.decodeJson(x.asJson).map(_.noSpaces).getOrElse("")))
    } else {
      println("No value returned")
      ZIO.succeed(List[String]())
    }
}
