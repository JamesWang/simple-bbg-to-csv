package com.aidokay.bb.convert

trait DataLoader[O] {
  def load(from: String): O
}

