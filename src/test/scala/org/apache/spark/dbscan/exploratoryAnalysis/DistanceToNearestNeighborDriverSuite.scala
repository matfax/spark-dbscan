package org.apache.spark.dbscan.exploratoryAnalysis

import org.apache.spark.dbscan.{DbscanSettings, SuiteBase, TestDatasets}
import org.apache.spark.dbscan.spatial.PointSortKey
import org.apache.spark.dbscan.TestDatasets


class DistanceToNearestNeighborDriverSuite extends SuiteBase with TestDatasets {

  test ("DistanceToNearestNeighborDriver should work properly") {

    val it = dataset1_1.collect().map ( x => (new PointSortKey (x), x)).iterator
    val settings = new DbscanSettings ()
    val result = DistanceToNearestNeighborDriver.calculateDistancesToNearestNeighbors(it, settings.distanceMeasure)

    // TODO: add assertions

  }

}
