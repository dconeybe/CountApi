package com.dconeybe.countapi;

import androidx.annotation.NonNull;

// This just shows the two methods that will be added to the existing Query class.
public class Query extends com.dconeybe.countapi.firestore.Query {

  @NonNull
  public AggregateQuery count() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  public AggregateQuery aggregate(@NonNull AggregateField aggregateField1, @NonNull AggregateField... aggregateFields) {
    throw new RuntimeException("not implemented");
  }

}
