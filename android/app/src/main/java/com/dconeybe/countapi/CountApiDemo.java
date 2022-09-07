package com.dconeybe.countapi;

import static com.dconeybe.countapi.AggregateField.count;
import static com.dconeybe.countapi.AggregateField.min;
import static com.dconeybe.countapi.AggregateField.sum;

public final class CountApiDemo {

  private CountApiDemo() {
  }

  public static void countDemo(Query query) {
    AggregateQuerySnapshot snapshot = query.count().get(AggregateSource.SERVER).getResult();
    System.out.println("Found " + snapshot.getCount() + " documents");
  }

  public static void aggregateDemo(Query query) {
    AggregateQuery aggregateQuery = query.aggregate(count(), min("age"), sum("salary"));
    AggregateQuerySnapshot snapshot = aggregateQuery.get().getResult();

    Object minAge = snapshot.getOr(min("age"), "unknown");
    double totalSalary = snapshot.getDouble(sum("salary"), Double.NaN);
    System.out.println(
        "Found " + snapshot.getCount() + " people,"
        + " the youngest being " + minAge
        + " earning a total of $" + totalSalary
    );
  }

}
