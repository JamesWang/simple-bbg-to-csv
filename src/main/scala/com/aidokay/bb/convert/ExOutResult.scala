package com.aidokay.bb.convert

case class ExOutResult(seq: Int, uniqueId: String) extends OutputResult {
  override def toCsv(): String = seq + "," + uniqueId
}
