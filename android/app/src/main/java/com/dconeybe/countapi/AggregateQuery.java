package com.dconeybe.countapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dconeybe.countapi.firestore.Task;

public class AggregateQuery {

  @NonNull
  public Query getQuery() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  public Task<AggregateQuerySnapshot> get() {
    return get(AggregateSource.DEFAULT);
  }

  @NonNull
  public Task<AggregateQuerySnapshot> get(@NonNull AggregateSource aggregateSource) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public int hashCode() {
    throw new RuntimeException("not implemented");
  }

}
