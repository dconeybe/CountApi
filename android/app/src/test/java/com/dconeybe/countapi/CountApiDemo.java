package com.dconeybe.countapi;

import static com.dconeybe.countapi.AggregateField.count;
import static com.dconeybe.countapi.AggregateField.min;
import static com.dconeybe.countapi.AggregateField.sum;
import static com.google.common.truth.Truth.assertThat;

public final class CountApiDemo {

  public void Demo0_NormalQuery(FirebaseFirestore db) {
    CollectionReference query = db.collection("games/halo/players");
    QuerySnapshot snapshot = query.get().getResult();
    assertThat(snapshot.size()).isEqualTo(5_000_000);
  }

  public void Demo1_CountOfDocumentsInACollection(FirebaseFirestore db) {
    AggregateQuery countQuery = db.collection("games/halo/players").count();
    AggregateQuerySnapshot snapshot = countQuery.get(AggregateSource.SERVER).getResult();
    assertThat(snapshot.getCount()).isEqualTo(5_000_000);
  }

  public void Demo2_CountOfDocumentsInACollectionWithFilter(FirebaseFirestore db) {
    Query query = db.collection("games/halo/players").whereEqualTo("online", true);
    AggregateQuery countQuery = query.count();
    AggregateQuerySnapshot snapshot = countQuery.get(AggregateSource.SERVER).getResult();
    assertThat(snapshot.getCount()).isEqualTo(2000);
  }

  public void Demo3_MultipleAggregations(FirebaseFirestore db) {
    Query query = db.collection("games/halo/players");
    AggregateQuery aggregateQuery = query.aggregate(count(), min("age"), sum("score"));
    AggregateQuerySnapshot snapshot = aggregateQuery.get().getResult();

    Object minAge = snapshot.get(min("age"));
    long minAgeStr = (minAge instanceof Long) ? (Long) minAge : 0;
    Double totalPoints = snapshot.get(sum("salary"));
    double totalPointsDbl = totalPoints != null ? totalPoints : 0.0;

    System.out.println(
        "Found " + snapshot.getCount() + " players,"
        + " the youngest being " + minAgeStr
        + " with a total of " + (long) totalPointsDbl + " points"
    );
  }

}
