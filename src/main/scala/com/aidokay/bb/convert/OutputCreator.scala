package com.aidokay.bb.convert

import com.aidokay.model.{BBResult, QueryResult}

trait OutputCreator[I] {
  def create(result: List[I], file: String): Unit
}

object OutputCreator {
  extension (result: BBResult)
    def asCsv(): String = {
      List(result.id.toString, result.product, result.isin, result.day.toString)
        .foldLeft("")(_ + "," + _)
    }

  extension (result: QueryResult)
    def asCsv(): String = {
      List(result.id.toString, result.product, result.isin)
        .foldLeft("")(_ + "," + _)
    }
}
