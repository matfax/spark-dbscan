package org.apache.spark.dbscan.spatial

import org.apache.spark.dbscan.{ClusterId, TempPointId}

/** A subclass of [[Point]] used during calculation of clusters within one partition
  */
private [dbscan] class PartiallyMutablePoint (p: Point, val tempId: TempPointId) extends Point (p) {

  var transientClusterId: ClusterId = p.clusterId
  var visited: Boolean = false

  def toImmutablePoint: Point = Point (this.coordinates, this.pointId, this.boxId, this.distanceFromOrigin,
    this.precomputedNumberOfNeighbors, this.transientClusterId)

}
