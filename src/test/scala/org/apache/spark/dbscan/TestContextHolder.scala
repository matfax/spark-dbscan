package org.apache.spark.dbscan

import org.apache.spark.SparkContext

object TestContextHolder {
  val sc = new SparkContext ("local[4]", "test")
}
