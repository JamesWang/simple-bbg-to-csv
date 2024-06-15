package com.aidokay.bb.convert

import com.aidokay.bb.convert.Constants.{END_OF_DATA, START_OF_DATA}
import zio.{ULayer, ZIO, ZLayer}

import java.io.IOException
import scala.io.Source

trait DataLoader[O] {
  def load(from: String): O
}

