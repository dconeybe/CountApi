package com.dconeybe.countapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Map;

////////////////////////////////////////////////////////////////////////////////
// Types defined already in the Firestore SDK.
// They are copied here so that the code that follows will compile.
////////////////////////////////////////////////////////////////////////////////

abstract class Task<TResult> {

  public abstract TResult getResult();

}

class FieldPath {
}

class SnapshotMetadata {
}

enum ServerTimestampBehavior {
  NONE,
  ESTIMATE,
  PREVIOUS;

  static final ServerTimestampBehavior DEFAULT = ServerTimestampBehavior.NONE;
}

class Timestamp {
}

class Blob {
}

class GeoPoint {
}

class DocumentReference {
}

////////////////////////////////////////////////////////////////////////////////
// The methods added to the existing Query class.
////////////////////////////////////////////////////////////////////////////////

class Query {

  @NonNull
  AggregateQuery count() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  AggregateQuery aggregate(@NonNull AggregateField aggregateField1, @NonNull AggregateField... aggregateFields) {
    throw new RuntimeException("not implemented");
  }

}

////////////////////////////////////////////////////////////////////////////////
// New aggregation types.
////////////////////////////////////////////////////////////////////////////////

abstract class AggregateField {

  @NonNull
  static CountAggregateField count() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  static MinAggregateField min(String field) {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  static MinAggregateField min(FieldPath fieldPath) {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  static MaxAggregateField max(String field) {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  static MaxAggregateField max(FieldPath fieldPath) {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  static SumAggregateField sum(String field) {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  static SumAggregateField sum(FieldPath fieldPath) {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  static AverageAggregateField average(String field) {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  static AverageAggregateField average(FieldPath fieldPath) {
    throw new RuntimeException("not implemented");
  }

  static class CountAggregateField extends AggregateField {
  }

  static class MinAggregateField extends AggregateField {
  }

  static class MaxAggregateField extends AggregateField {
  }

  static class SumAggregateField extends AggregateField {
  }

  static class AverageAggregateField extends AggregateField {
  }

}

enum AggregateSource {
  DEFAULT,
  SERVER,
  CACHE,
}

class AggregateQuery {

  @NonNull
  Query getQuery() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  Task<AggregateQuerySnapshot> get() {
    return get(AggregateSource.DEFAULT);
  }

  @NonNull
  Task<AggregateQuerySnapshot> get(@NonNull AggregateSource aggregateSource) {
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

class AggregateQuerySnapshot {

  @NonNull
  AggregateQuery getQuery() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  SnapshotMetadata getMetadata() {
    throw new RuntimeException("not implemented");
  }

  @NonNull
  public Map<AggregateField, Object> getAggregations() {
    return getAggregations(ServerTimestampBehavior.DEFAULT);
  }

  @NonNull
  public Map<AggregateField, Object> getAggregations(ServerTimestampBehavior stb) {
    throw new RuntimeException("not implemented");
  }

  public boolean contains(@NonNull AggregateField field) {
    return getAggregations().containsKey(field);
  }

  long getCount() {
    return get(AggregateField.count());
  }

  // Special overload for "count" because it always evaluates to an integer, and is never undefined.
  long get(@NonNull AggregateField.CountAggregateField countAggregateField) {
    //noinspection ConstantConditions
    return getLong(countAggregateField);
  }

  // Special overload for "sum" because it always evaluates to a double.
  @Nullable
  Double get(@NonNull AggregateField.SumAggregateField sumAggregateField) {
    return getDouble(sumAggregateField);
  }

  // Special overload for "average" because it always evaluates to a double.
  @Nullable
  Double get(@NonNull AggregateField.AverageAggregateField averageAggregateField) {
    return getDouble(averageAggregateField);
  }

  boolean isUndefined(@NonNull AggregateField aggregateField) {
    throw new RuntimeException("not implemented");
  }

  boolean isOutOfRange(@NonNull AggregateField aggregateField) {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Object get(@NonNull AggregateField aggregateField) {
    return get(aggregateField, ServerTimestampBehavior.DEFAULT);
  }

  @Nullable
  Object get(@NonNull AggregateField aggregateField, @NonNull ServerTimestampBehavior stb) {
    // Return either the value, or UNDEFINED or OUT_OF_RANGE.
    throw new RuntimeException("not implemented");
  }

  @Nullable
  <T> T get(@NonNull AggregateField aggregateField, @NonNull Class<T> valueType) {
    return valueType.cast(get(aggregateField));
  }

  @Nullable
  <T> T get(@NonNull AggregateField aggregateField, @NonNull Class<T> valueType, @NonNull ServerTimestampBehavior stb) {
    return valueType.cast(get(aggregateField));
  }

  @Nullable
  Date getDate(@NonNull AggregateField aggregateField) {
    return getDate(aggregateField, ServerTimestampBehavior.DEFAULT);
  }

  @Nullable
  Date getDate(@NonNull AggregateField aggregateField, @NonNull ServerTimestampBehavior stb) {
    return get(aggregateField, Date.class, stb);
  }

  @Nullable
  Double getDouble(@NonNull AggregateField aggregateField) {
    return get(aggregateField, Double.class);
  }

  @Nullable
  Long getLong(@NonNull AggregateField aggregateField) {
    return get(aggregateField, Long.class);
  }

  @Nullable
  String getString(@NonNull AggregateField aggregateField) {
    return get(aggregateField, String.class);
  }

  @Nullable
  Timestamp getTimestamp(@NonNull AggregateField aggregateField) {
    return getTimestamp(aggregateField, ServerTimestampBehavior.DEFAULT);
  }

  @Nullable
  Timestamp getTimestamp(@NonNull AggregateField aggregateField, @NonNull ServerTimestampBehavior stb) {
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
