package org.apache.spark.dbscan.spatial

import org.apache.spark.dbscan.{ClusterId, DbscanModel, _}

/** Represents a point in multi-dimensional space and metadata required by the distributed DBSCAN algorithm
  *
  * @param coordinates Point's coordinates
  * @param pointId A unique identifier of the point
  * @param boxId An identifier of a partition of a data set which this point belongs to
  * @param distanceFromOrigin Distance of this point from origin
  * @param precomputedNumberOfNeighbors Number of point's neighbors
  * @param clusterId ID of a cluster which this points belongs to
  */
abstract case class Point private[Point] (
    coordinates: PointCoordinates,
    pointId: PointId = 0,
    boxId: BoxId = 0,
    distanceFromOrigin: Double = 0.0,
    precomputedNumberOfNeighbors: Long = 0,
    clusterId: ClusterId = DbscanModel.UndefinedCluster)
    extends Serializable
    with Ordered[Point] {

  private def readResolve(): Object =
    Point.apply(coordinates,
                pointId,
                boxId,
                distanceFromOrigin,
                precomputedNumberOfNeighbors,
                clusterId)
  def copy(coord: Double*): Point =
    Point.apply(coordinates,
                pointId,
                boxId,
                distanceFromOrigin,
                precomputedNumberOfNeighbors,
                clusterId)

  def this(coords: Array[Double]) = this(new PointCoordinates(coords))

  def this(pt: Point) =
    this(pt.coordinates,
         pt.pointId,
         pt.boxId,
         pt.distanceFromOrigin,
         pt.precomputedNumberOfNeighbors,
         pt.clusterId)

  def this(coords: Double*) = this(new PointCoordinates(coords.toArray))

  def withPointId(newId: PointId) = {
    Point(this.coordinates,
          newId,
          this.boxId,
          this.distanceFromOrigin,
          this.precomputedNumberOfNeighbors,
          this.clusterId)
  }

  def withBoxId(newBoxId: BoxId) = {
    Point(this.coordinates,
          this.pointId,
          newBoxId,
          this.distanceFromOrigin,
          this.precomputedNumberOfNeighbors,
          this.clusterId)
  }

  def withDistanceFromOrigin(newDistance: Double) = {
    Point(this.coordinates,
          this.pointId,
          this.boxId,
          newDistance,
          this.precomputedNumberOfNeighbors,
          this.clusterId)
  }

  def withNumberOfNeighbors(newNumber: Long) = {
    Point(this.coordinates,
          this.pointId,
          this.boxId,
          this.distanceFromOrigin,
          newNumber,
          this.clusterId)
  }

  def withClusterId(newId: ClusterId) = {
    Point(this.coordinates,
          this.pointId,
          this.boxId,
          this.distanceFromOrigin,
          this.precomputedNumberOfNeighbors,
          newId)
  }

  override def equals(that: Any): Boolean = {

    that match {
      case point: Point =>
        point.canEqual(this) &&
          this.coordinates == point.coordinates // We take only coordinates into account
      // and don't care about other attributes
      case _ =>
        false
    }
  }

  override def hashCode(): Int = {
    coordinates.hashCode() // We take only coordinates into account
    // and don't care about other attributes
  }

  override def toString: String = {
    "Point at (" + coordinates.mkString(", ") + "); id = " + pointId + "; box = " + boxId +
      "; cluster = " + clusterId + "; neighbors = " + precomputedNumberOfNeighbors
  }

  override def compare(that: Point): Int = {
    var result = 0
    var i = 0

    while (result == 0 && i < coordinates.size) {
      result = this.coordinates(i).compareTo(that.coordinates(i))
      i += 1
    }

    result
  }
}

object Point {
  def apply(coordinates: PointCoordinates,
            pointId: PointId = 0,
            boxId: BoxId = 0,
            distanceFromOrigin: Double = 0.0,
            precomputedNumberOfNeighbors: Long = 0,
            clusterId: ClusterId = DbscanModel.UndefinedCluster) =
    new Point(coordinates,
              pointId,
              boxId,
              distanceFromOrigin,
              precomputedNumberOfNeighbors,
              clusterId) {}

  def apply(coord: Double*): Point =
    new Point(coord: _*) {}

  def apply(coords: Array[Double]) = new Point(coords) {}

  def apply(pt: Point) =
    new Point(pt.coordinates,
              pt.pointId,
              pt.boxId,
              pt.distanceFromOrigin,
              pt.precomputedNumberOfNeighbors,
              pt.clusterId) {}

}
