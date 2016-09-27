package org.apache.spark.dbscan.spatial

import java.util.Collections

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

private[dbscan] class BoxTreeItemWithPoints(
    b: Box,
    val points: mutable.Buffer[Point] =
      Collections.synchronizedList(new ArrayBuffer[Point]().asJava).asScala,
    val adjacentBoxes: mutable.Buffer[BoxTreeItemWithPoints] = Collections
      .synchronizedList(new ArrayBuffer[BoxTreeItemWithPoints]().asJava)
      .asScala)
    extends BoxTreeItemBase[BoxTreeItemWithPoints](b) {}
