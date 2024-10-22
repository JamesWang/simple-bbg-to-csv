package com.aidokay.bb.convert

trait OutputCreator[I, O] {
  def create(result: List[I], file: String): O
}

object OutputCreator {
  extension (result: OutputResult)
    def asCsv(): String = result.toCsv()
}