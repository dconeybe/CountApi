package com.dconeybe.countapi;

import androidx.annotation.NonNull;

import com.dconeybe.countapi.firestore.FieldPath;

public abstract class AggregateField {

  private AggregateField() {
  }

  @NonNull
  public static CountAggregateField count() {
    return new CountAggregateField();
  }

  @NonNull
  public static MinAggregateField min(String field) {
    return new MinAggregateField();
  }

  @NonNull
  public static MinAggregateField min(FieldPath fieldPath) {
    return new MinAggregateField();
  }

  @NonNull
  public static MaxAggregateField max(String field) {
    return new MaxAggregateField();
  }

  @NonNull
  public static MaxAggregateField max(FieldPath fieldPath) {
    return new MaxAggregateField();
  }

  @NonNull
  public static SumAggregateField sum(String field) {
    return new SumAggregateField();
  }

  @NonNull
  public static SumAggregateField sum(FieldPath fieldPath) {
    return new SumAggregateField();
  }

  @NonNull
  public static AverageAggregateField average(String field) {
    return new AverageAggregateField();
  }

  @NonNull
  public static AverageAggregateField average(FieldPath fieldPath) {
    return new AverageAggregateField();
  }

  public static class CountAggregateField extends AggregateField {
    private CountAggregateField() {
    }
  }

  public static class MinAggregateField extends AggregateField {
    private MinAggregateField() {
    }
  }

  public static class MaxAggregateField extends AggregateField {
    private MaxAggregateField() {
    }
  }

  public static class SumAggregateField extends AggregateField {
    private SumAggregateField() {
    }
  }

  public static class AverageAggregateField extends AggregateField {
    private AverageAggregateField() {
    }
  }


}
