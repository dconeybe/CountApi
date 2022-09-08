package com.dconeybe.countapi

internal inline fun <reified T>
    AggregateQuerySnapshot.getField(
      field: AggregateField
    ): T? =
      get(field, T::class.java)

internal inline fun <reified T>
    AggregateQuerySnapshot.getField(
      field: AggregateField,
      serverTimestampBehavior: ServerTimestampBehavior
    ): T? =
      get(field, T::class.java, serverTimestampBehavior)

