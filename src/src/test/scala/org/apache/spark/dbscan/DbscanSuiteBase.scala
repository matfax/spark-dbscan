package org.apache.spark.dbscan

import org.apache.spark.dbscan.spatial.{Point, PointSortKey}
import org.apache.spark.dbscan.util.debug.Clock

import scala.collection.mutable

class DbscanSuiteBase extends SuiteBase with TestDatasets {

  var clock: Clock = _

  override def beforeEach() = {
    super.beforeEach()

    clock = new Clock()
  }

  override def afterEach() = {
    clock.logTimeSinceStart()
  }

  def groupPointsByCluster(
      clusteringResult: DbscanModel): Array[Iterable[PointCoordinates]] = {
    val clock = new Clock()
    val result = clusteringResult.clusteredPoints
      .map(x => (x.clusterId, x.coordinates))
      .groupByKey()
      .map(_._2)
      .collect()
    clock.logTimeSinceStart("Grouping points by cluster took")

    result
  }

  def groupPointsByCluster(
      it: Iterator[(PointSortKey, Point)]): Array[Array[Point]] = {

    it.map(_._2).toArray.groupBy(_.clusterId).values.toArray
  }

  def findClusterWithPoint(
      clusters: Array[Iterable[mutable.WrappedArray.ofDouble]],
      pt: Point): Option[Iterable[mutable.WrappedArray.ofDouble]] = {

    val filteredClusters = clusters.filter(_.toArray.contains(pt.coordinates))

    filteredClusters.length match {
      case 0 => None
      case _ => Some(filteredClusters(0))
    }
  }

  def findClusterWithPoint2(clusters: Array[Array[Point]],
                            pt: Point): Option[Array[Point]] = {

    val filteredClusters = clusters.filter(_.contains(pt))

    filteredClusters.length match {
      case 0 => None
      case _ => Some(filteredClusters(0))
    }
  }
}
