package com.aidokay.db

trait Querier[I, F[_]] {
  type T

  def prepareWith(params: Option[I]): F[T]
}
