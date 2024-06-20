package com.aidokay.db

import cats.effect.IO
import com.aidokay.model.{BBResult, QueryResult}
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import zio.{UIO, ULayer, ZIO, ZLayer}


trait QueryRunnerService[I, R] extends QueryRunner[I, QueryResult] {
  self: Querier[I, UIO] {type T = List[QueryResult]} =>
  type Aux = UIO[List[BBResult]]

  def executeWith(params: Option[I], handler: List[QueryResult] => Aux): Aux =
    prepareWith(params).flatMap(handler)
}

object QueryRunnerService:
  val live: ULayer[QueryRunnerService[List[String], List[BBResult]]] =
    ZLayer.succeed[QueryRunnerService[List[String], List[BBResult]]](
      new QueryRunnerService[List[String], List[BBResult]] with InMemQuery
    )
end QueryRunnerService
