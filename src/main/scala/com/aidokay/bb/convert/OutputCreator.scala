package com.aidokay.bb.convert

trait OutputCreator[I] {
  def create(result: List[I], file: String): Unit
}

object OutputCreator {
  extension (result: OutputResult)
    def asCsv(): String = result.toCsv()
}