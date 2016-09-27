package org.apache.spark.dbscan

import org.apache.commons.math3.ml.distance.EuclideanDistance
import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable

class DistanceTest extends FunSuite with Matchers {

  test ("Euclidean distance should be equal to square root of ...") {

    val pt1 = new mutable.WrappedArray.ofDouble(Array(0.0, 0.0))
    val pt2 = new mutable.WrappedArray.ofDouble(Array(1.0, 1.0))

    val distance = new EuclideanDistance ().compute(pt1.toArray, pt2.toArray)

    distance should be (java.lang.Math.sqrt(2))
  }

}
