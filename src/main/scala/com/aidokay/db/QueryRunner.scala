package com.aidokay.db

trait QueryRunner[I, R] {
  type Aux
  def executeWith(param: Option[I], handler: List[R] => Aux): Aux
}
