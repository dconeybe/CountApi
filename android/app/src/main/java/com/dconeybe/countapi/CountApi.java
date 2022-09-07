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

class AggregationResult {

  static final UndefinedValue UNDEFINED = new UndefinedValue();
  static final OutOfRangeValue OUT_OF_RANGE = new OutOfRangeValue();

  @Nullable
  Object value() {
    // Return either the value, or UNDEFINED or OUT_OF_RANGE.
    throw new RuntimeException("not implemented");
  }

  @NonNull
  Object valueOr(@NonNull Object defaultValue) {
    Object value = value();
    return (value != UNDEFINED && value != OUT_OF_RANGE && value != null) ? value : defaultValue;
  }

  @Nullable
  <T> T valueAs(@NonNull Class<T> valueType) {
    Object value = value();
    return valueType.isInstance(value) ? valueType.cast(value) : null;
  }

  @NonNull
  <T> T valueAs(@NonNull Class<T> valueType, @NonNull T defaultValue) {
    T value = valueAs(valueType);
    return (value != null) ? value : defaultValue;
  }

  @Nullable
  Date dateValue() {
    return valueAs(Date.class);
  }

  @NonNull
  Date dateValue(@NonNull Date defaultValue) {
    return valueAs(Date.class, defaultValue);
  }

  @Nullable
  Double doubleValue() {
    return valueAs(Double.class);
  }

  double doubleValue(double defaultValue) {
    return valueAs(Double.class, defaultValue);
  }

  @Nullable
  Long longValue() {
    return valueAs(Long.class);
  }

  long longValue(long defaultValue) {
    return valueAs(Long.class, defaultValue);
  }

  @Nullable
  String stringValue() {
    return valueAs(String.class);
  }

  @NonNull
  String stringValue(String defaultValue) {
    return valueAs(String.class, defaultValue);
  }

  @Nullable
  Timestamp timestampValue() {
    return valueAs(Timestamp.class);
  }

  @NonNull
  Timestamp timestampValue(Timestamp defaultValue) {
    return valueAs(Timestamp.class, defaultValue);
  }

  @NonNull
  @Override
  public String toString() {
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

  static final class UndefinedValue {
  }

  static final class OutOfRangeValue {
  }

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
  public Map<AggregateField, AggregationResult> getAggregations() {
    return getAggregations(ServerTimestampBehavior.DEFAULT);
  }

  @NonNull
  public Map<AggregateField, AggregationResult> getAggregations(ServerTimestampBehavior stb) {
    throw new RuntimeException("not implemented");
  }

  public boolean contains(AggregateField field) {
    return getAggregations().containsKey(field);
  }

  public AggregationResult get(AggregateField field) {
    throw new RuntimeException("not implemented");
  }

  public AggregationResult get(AggregateField field, ServerTimestampBehavior stb) {
    throw new RuntimeException("not implemented");
  }

  long getCount() {
    return get(AggregateField.count()).longValue();
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
