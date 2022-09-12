package com.dconeybe.countapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dconeybe.countapi.AggregateField;
import com.dconeybe.countapi.AggregateQuery;
import com.dconeybe.countapi.firestore.ServerTimestampBehavior;
import com.dconeybe.countapi.firestore.SnapshotMetadata;
import com.dconeybe.countapi.firestore.Timestamp;

import java.util.Date;
import java.util.Map;

public class AggregateQuerySnapshot {

  @NonNull
  public AggregateQuery getQuery() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  public SnapshotMetadata getMetadata() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  public Map<AggregateField, Object> getAggregations() {
    return getAggregations(ServerTimestampBehavior.NONE);
  }

  @NonNull
  public Map<AggregateField, Object> getAggregations(ServerTimestampBehavior stb) {
    throw new RuntimeException("not implemented");
  }

  public boolean contains(@NonNull AggregateField field) {
    return getAggregations().containsKey(field);
  }

  public long getCount() {
    return get(AggregateField.count());
  }

  // Special overload for "count" because it always evaluates to an integer, and is never undefined.
  public long get(@NonNull AggregateField.CountAggregateField countAggregateField) {
    //noinspection ConstantConditions
    return getLong(countAggregateField);
  }

  // Special overload for "sum" because it always evaluates to a double.
  @Nullable
  public Double get(@NonNull AggregateField.SumAggregateField sumAggregateField) {
    return getDouble(sumAggregateField);
  }

  // Special overload for "average" because it always evaluates to a double.
  @Nullable
  public Double get(@NonNull AggregateField.AverageAggregateField averageAggregateField) {
    return getDouble(averageAggregateField);
  }

  public boolean isUndefined(@NonNull AggregateField aggregateField) {
    throw new RuntimeException("not implemented");
  }

  public boolean isOutOfRange(@NonNull AggregateField aggregateField) {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  public Object get(@NonNull AggregateField aggregateField) {
    return get(aggregateField, ServerTimestampBehavior.NONE);
  }

  @Nullable
  public Object get(@NonNull AggregateField aggregateField, @NonNull ServerTimestampBehavior stb) {
    // Return either the value, or UNDEFINED or OUT_OF_RANGE.
    throw new RuntimeException("not implemented");
  }

  @Nullable
  public <T> T get(@NonNull AggregateField aggregateField, @NonNull Class<T> valueType) {
    return valueType.cast(get(aggregateField));
  }

  @Nullable
  public <T> T get(@NonNull AggregateField aggregateField, @NonNull Class<T> valueType, @NonNull ServerTimestampBehavior stb) {
    return valueType.cast(get(aggregateField));
  }

  @Nullable
  public Date getDate(@NonNull AggregateField aggregateField) {
    return getDate(aggregateField, ServerTimestampBehavior.NONE);
  }

  @Nullable
  public Date getDate(@NonNull AggregateField aggregateField, @NonNull ServerTimestampBehavior stb) {
    return get(aggregateField, Date.class, stb);
  }

  @Nullable
  public Double getDouble(@NonNull AggregateField aggregateField) {
    return get(aggregateField, Double.class);
  }

  @Nullable
  public Long getLong(@NonNull AggregateField aggregateField) {
    return get(aggregateField, Long.class);
  }

  @Nullable
  public String getString(@NonNull AggregateField aggregateField) {
    return get(aggregateField, String.class);
  }

  @Nullable
  public Timestamp getTimestamp(@NonNull AggregateField aggregateField) {
    return getTimestamp(aggregateField, ServerTimestampBehavior.NONE);
  }

  @Nullable
  public Timestamp getTimestamp(@NonNull AggregateField aggregateField, @NonNull ServerTimestampBehavior stb) {
    return get(aggregateField, Timestamp.class, stb);
  }

  @Override
  public boolean equals(Object o) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public int hashCode() {
    throw new RuntimeException("not implemented");
  }

}
