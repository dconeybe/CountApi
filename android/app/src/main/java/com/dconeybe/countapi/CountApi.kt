package com.dconeybe.countapi

import com.dconeybe.countapi.firestore.ServerTimestampBehavior

inline fun <reified T>
    AggregateQuerySnapshot.getField(
      field: AggregateField
    ): T? =
      get(field, T::class.java)

inline fun <reified T>
    AggregateQuerySnapshot.getField(
      field: AggregateField,
      serverTimestampBehavior: ServerTimestampBehavior
    ): T? =
      get(field, T::class.java, serverTimestampBehavior)

