package com.aidokay

import com.aidokay.json.{Json, JsonInt, JsonObject, JsonString, JsonWriter}

package object model {

  case class QueryResult(id: Int, product: String, isin: String)

  case class BBResult(id: Int, product: String, isin: String, day: Int)

  given jsonQueryResult: JsonWriter[QueryResult] = (value: QueryResult) => {
    JsonObject(
      Map(
        "id" -> JsonInt(value.id),
        "product" -> JsonString(value.product),
        "isin" -> JsonString(value.isin)
      )
    )
  }

  given jsonBBResult: JsonWriter[BBResult] = (value: BBResult) => {
    JsonObject(
      Map(
        "id" -> JsonInt(value.id),
        "product" -> JsonString(value.product),
        "isin" -> JsonString(value.isin),
        "day" -> JsonInt(value.day)
      )
    )
  }

  
  extension (qResult: QueryResult)
    def asJsonStr(): String =
      Json.toJson(qResult).toString


  extension (qResult: BBResult)
    def asJsonStr(): String =
      Json.toJson(qResult).toString
}
