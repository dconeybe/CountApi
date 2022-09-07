package com.dconeybe.countapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

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

  AggregateQuery count() {
    throw new RuntimeException("not implemented");
  }

  AggregateQuery aggregate(AggregateField aggregateField1, AggregateField... aggregateFields) {
    throw new RuntimeException("not implemented");
  }

}

////////////////////////////////////////////////////////////////////////////////
// New aggregation types.
////////////////////////////////////////////////////////////////////////////////

abstract class AggregateField {

  static CountAggregateField count() {
    throw new RuntimeException("not implemented");
  }

  static MinAggregateField min(String field) {
    throw new RuntimeException("not implemented");
  }

  static MinAggregateField min(FieldPath fieldPath) {
    throw new RuntimeException("not implemented");
  }

  static SumAggregateField sum(String field) {
    throw new RuntimeException("not implemented");
  }

  static SumAggregateField sum(FieldPath fieldPath) {
    throw new RuntimeException("not implemented");
  }

  static class CountAggregateField extends AggregateField {
  }

  static class MinAggregateField extends AggregateField {
  }

  static class SumAggregateField extends AggregateField {
  }

}

enum AggregateSource {
  DEFAULT,
  SERVER,
  CACHE,
}

class AggregateQuery {

  Query getQuery() {
    throw new RuntimeException("not implemented");
  }

  Task<AggregateQuerySnapshot> get() {
    return get(AggregateSource.DEFAULT);
  }

  Task<AggregateQuerySnapshot> get(AggregateSource aggregateSource) {
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

  AggregateQuery getQuery() {
    throw new RuntimeException("not implemented");
  }

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
    return getLong(AggregateField.count());
  }

  boolean isUndefined(@NonNull AggregateField aggregateField) {
    throw new RuntimeException("not implemented");
  }

  boolean isOutOfRange(@NonNull AggregateField aggregateField) {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Object get(@NonNull AggregateField aggregateField) {
    // Return either the value, or UNDEFINED or OUT_OF_RANGE.
    throw new RuntimeException("not implemented");
  }

  @NonNull
  Object getOr(@NonNull AggregateField aggregateField, @NonNull Object defaultValue) {
    Object value = get(aggregateField);
    return (value != UNDEFINED && value != OUT_OF_RANGE && value != null) ? value : defaultValue;
  }

  @Nullable
  <T> T get(@NonNull AggregateField aggregateField, @NonNull Class<T> valueType) {
    Object value = get(aggregateField);
    return valueType.isInstance(value) ? valueType.cast(value) : null;
  }

  @NonNull
  <T> T get(@NonNull AggregateField aggregateField, @NonNull Class<T> valueType, @NonNull T defaultValue) {
    T value = get(aggregateField, valueType);
    return (value != null) ? value : defaultValue;
  }

  @Nullable
  Date getDate(@NonNull AggregateField aggregateField) {
    return get(aggregateField, Date.class);
  }

  @NonNull
  Date getDate(@NonNull AggregateField aggregateField, @NonNull Date defaultValue) {
    return get(aggregateField, Date.class, defaultValue);
  }

  @Nullable
  Double getDouble(@NonNull AggregateField aggregateField) {
    return get(aggregateField, Double.class);
  }

  double getDouble(@NonNull AggregateField aggregateField, double defaultValue) {
    return get(aggregateField, Double.class, defaultValue);
  }

  @Nullable
  Long getLong(@NonNull AggregateField aggregateField) {
    return get(aggregateField, Long.class);
  }

  long getLong(@NonNull AggregateField aggregateField, long defaultValue) {
    return get(aggregateField, Long.class, defaultValue);
  }

  @Nullable
  String getString(@NonNull AggregateField aggregateField) {
    return get(aggregateField, String.class);
  }

  @NonNull
  String getString(@NonNull AggregateField aggregateField, String defaultValue) {
    return get(aggregateField, String.class, defaultValue);
  }

  @Nullable
  Timestamp getTimestamp(@NonNull AggregateField aggregateField) {
    return get(aggregateField, Timestamp.class);
  }

  @NonNull
  Timestamp getTimestamp(@NonNull AggregateField aggregateField, Timestamp defaultValue) {
    return get(aggregateField, Timestamp.class, defaultValue);
  }

  static final class UndefinedValue {
  }

  static final class OutOfRangeValue {
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
