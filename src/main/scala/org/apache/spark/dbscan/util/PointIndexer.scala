package org.apache.spark.dbscan.util

import org.apache.commons.math3.ml.distance.DistanceMeasure
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.dbscan._
import org.apache.spark.dbscan.spatial.{Box, Point, PointSortKey}
import org.apache.spark.rdd.RDD

private [dbscan] class PointIndexer (val numberOfPartitions: Int, val currentPartition: Int) {

  val multiplier = computeMultiplier (numberOfPartitions)
  var currentIndex = 0

  def getNextIndex = {
    currentIndex += 1
    currentIndex * multiplier + currentPartition
  }

  def computeMultiplier (numberOfPartitions: Int) = {
    val numberOfDigits = Math.floor (java.lang.Math.log10 (numberOfPartitions)) + 1

    Math.round (Math.pow (10, numberOfDigits))
  }

}

private [dbscan] object PointIndexer {

  def addMetadataToPoints (
      data: RawDataSet,
      boxes: Broadcast[Iterable[Box]],
      dimensions: Broadcast[Int],
      distanceMeasure: DistanceMeasure): RDD[(PointSortKey, Point)] = {

    val numPartitions = data.partitions.length
    val origin = Point (Array.fill (dimensions.value)(0.0))

    data.mapPartitionsWithIndex( (partitionIndex, points) => {

      val pointIndexer = new PointIndexer (numPartitions, partitionIndex)

      points.map (pt => {

        val pointIndex = pointIndexer.getNextIndex
        val box = boxes.value.find( _.isPointWithin(pt) )
        val distanceFromOrigin = distanceMeasure.compute(pt.coordinates.toArray, origin.coordinates.toArray)
        val boxId = box match {
          case existingBox: Some[Box] => existingBox.get.boxId
          case _ => 0 // throw an exception?
        }

        val newPoint = Point (pt.coordinates, pointIndex, boxId, distanceFromOrigin,
            pt.precomputedNumberOfNeighbors, pt.clusterId)

        (new PointSortKey (newPoint), newPoint)
      })
    })

  }
}
