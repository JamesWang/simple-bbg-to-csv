package com.aidokay.db

import cats.effect.IO
import com.aidokay.model.QueryResult
import zio.{UIO, ZIO}

trait InMemQuery extends Querier[List[String], UIO]{
  type T = List[QueryResult]

  override def prepareWith(params: Option[List[String]]): UIO[List[QueryResult]] =
    ZIO.succeed(IN_MEM_DATA.map(tp3 => QueryResult(tp3._1, tp3._2, tp3._3)).toList)


  private val IN_MEM_DATA: Seq[(Int, String, String)] =
    List(
      (2451234, "C0AB2345671", "US0000000001"),
    )
}
