package com.dconeybe.countapi

import com.dconeybe.countapi.AggregateField.*

internal fun countDemoKt(query: Query) {
  val snapshot = query.count().get(AggregateSource.SERVER).result
  println("Found " + snapshot.count + " documents")
}

internal fun aggregateDemoKt(query: Query) {
  val aggregateQuery = query.aggregate(count(), min("age"), sum("salary"))
  val snapshot = aggregateQuery.get().result

  val minAge: Long = snapshot.getField<Long>(min("age")) ?: 0
  val totalSalary: Double = snapshot.get(sum("salary")) ?: 0.0

  println(
    "Found ${snapshot.count} people, the youngest being $minAge earning a total of $totalSalary"
  )
}