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

class AggregateFieldValue {

  // Returns whether or not the aggregation was able to be calculated.
  // If false, then all getXXX() methods will return null.
  // Using this method helps to differentiate between the actual result of the aggregation being
  // null, or the aggregation not being able to be performed because no documents in the result set
  // defined a value for the field being aggregated.
  boolean isDefined() {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Object valueOr(@Nullable Object returnValueIfUndefined) {
    return valueOr(Object.class, returnValueIfUndefined);
  }

  @Nullable
  <T> T valueOr(Class<T> valueType, @Nullable T returnValueIfUndefined) {
    return isDefined() ? value(valueType) : returnValueIfUndefined;
  }

  @Nullable
  Object value() {
    return value(Object.class);
  }

  @Nullable
  <T> T value(Class<T> valueType) {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Blob blobValue() {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Boolean booleanValue() {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Date dateValue() {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Double doubleValue() {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  GeoPoint geoPointValue() {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Long longValue() {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  DocumentReference referenceValue() {
    throw new RuntimeException("not implemented");
  }

  @Nullable
  Timestamp timestampValue() {
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
  public Map<AggregateField, AggregateFieldValue> getAggregations() {
    return getAggregations(ServerTimestampBehavior.DEFAULT);
  }

  @NonNull
  public Map<AggregateField, AggregateFieldValue> getAggregations(ServerTimestampBehavior stb) {
    throw new RuntimeException("not implemented");
  }

  public boolean contains(AggregateField field) {
    throw new RuntimeException("not implemented");
  }

  public AggregateFieldValue get(AggregateField field) {
    throw new RuntimeException("not implemented");
  }

  public AggregateFieldValue get(AggregateField field, ServerTimestampBehavior stb) {
    throw new RuntimeException("not implemented");
  }

  long getCount() {
    return get(AggregateField.count());
  }

  public long get(AggregateField.CountAggregateField field) {
    //noinspection ConstantConditions
    return get((AggregateField) field).longValue();
  }

  // A return value of `null` indicates that the sum is undefined because there are no documents in
  // the result set of the underlying query that define a value for the key specified in the given
  // SumAggregateField.
  @Nullable
  public Double get(AggregateField.SumAggregateField field) {
    return get((AggregateField) field).doubleValue();
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
