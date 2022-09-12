package com.dconeybe.countapi

import com.dconeybe.countapi.AggregateField.*

import com.google.common.truth.Truth.assertThat

internal fun Demo0_NormalQuery(db: FirebaseFirestore) {
  val query = db.collection("games/halo/players")
  val snapshot = query.get().result
  assertThat(snapshot.size()).isEqualTo(5_000_000)
}

internal fun Demo1_CountOfDocumentsInACollection(db: FirebaseFirestore) {
  val countQuery = db.collection("games/halo/players").count()
  val snapshot = countQuery.get(AggregateSource.SERVER).result
  assertThat(snapshot.count).isEqualTo(5_000_000)
}

internal fun Demo2_CountOfDocumentsInACollectionWithFilter(db: FirebaseFirestore) {
  val query = db.collection("games/halo/players").whereEqualTo("online", true)
  val countQuery = query.count()
  val snapshot = countQuery.get(AggregateSource.SERVER).result
  assertThat(snapshot.count).isEqualTo(2000)
}

internal fun Demo3_MultipleAggregations(db: FirebaseFirestore) {
  val query = db.collection("games/halo/players")
  val aggregateQuery = query.aggregate(count(), min("age"), sum("score"))
  val snapshot = aggregateQuery.get().result

  val minAge = when (val minAge = snapshot.get(min("age"))) {
    is Long -> minAge.toLong()
    else -> 0
  }
  val totalPoints = snapshot.get(sum("salary")) ?: 0.0

  println(
      "Found ${snapshot.count} players,"
      + " the youngest being $minAge"
      + " with a total of ${totalPoints.toLong()} points"
  )
}
