package com.aidokay.json

sealed trait Json

final case class JsonObject(get: Map[String, Json]) extends Json

final case class JsonString(get: String) extends Json

final case class JsonInt(get: Int) extends Json

case object JsNull extends Json

trait JsonWriter[A]:
  def write(value: A): Json


object Json:
  given jsonStringWriter: JsonWriter[String] = (value: String) => JsonString(value)
  given jsonIntWriter: JsonWriter[Int] = (value: Int) => JsonInt(value)

  def toJson[A: JsonWriter](value: A): Json = summon[JsonWriter[A]].write(value)