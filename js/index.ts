////////////////////////////////////////////////////////////////////////////////
// Types defined already in the Firestore SDK
////////////////////////////////////////////////////////////////////////////////

// DocumentFieldValue doesn't actually exist today; however, discussing this API
// with Sebastian, we thought we should add it for code readability.
type DocumentFieldValue = any;

interface DocumentData {
  [field: string]: DocumentFieldValue;
}

interface SnapshotOptions {
  readonly serverTimestamps?: 'estimate' | 'previous' | 'none';
}

export class SnapshotMetadata {
  readonly hasPendingWrites: boolean;
  readonly fromCache: boolean;
}

class Query<T = DocumentData> {
  type: "Query";
}

class FieldPath {
  type: "FieldPath";
}

////////////////////////////////////////////////////////////////////////////////
// New AggregateField types.
////////////////////////////////////////////////////////////////////////////////

interface AggregateFieldBase<T> {
  // Will always be undefined; however, it is used in the fancy type mapping
  // in AggregateSpecData (below). Is there a better way to do this?
  _datum?: T;
}

// An aggregate field that counts the documents in the result set.
class CountAggregateField
    implements AggregateFieldBase<number> {
  type = "CountAggregateField";
  _datum?: number = undefined;
}

// An aggregate field that finds the minimum value of a given field in the
// result set. If no documents in the result define the given field, then the
// result will be `undefined`.
class MinAggregateField
    implements AggregateFieldBase<DocumentFieldValue | undefined> {
  type = "MinAggregateField";
  _datum?: DocumentFieldValue = undefined;
}

// An aggregate field that finds the sum of the *numeric* values of a given
// field in the result set. If no documents in the result define the given
// field, or none of the values are numeric, then the result will be `undefined`.
class SumAggregateField
    implements AggregateFieldBase<number | undefined> {
  type = "SumAggregateField";
  _datum?: number = undefined;
}

// A union type that matches the exact set of AggregateField implementations.
type AggregateField =
    CountAggregateField
  | MinAggregateField
  | SumAggregateField;

////////////////////////////////////////////////////////////////////////////////
// New AggregateField functions.
////////////////////////////////////////////////////////////////////////////////

// Creates and returns a new `CountAggregateField` object.
function count(): CountAggregateField {
  throw new Error("not implemented");
}

// Creates and returns a new `MinAggregateField` object for the given field.
function min(field: string | FieldPath): MinAggregateField {
  throw new Error("not implemented");
}

// Creates and returns a new `SumAggregateField` object for the given field.
function sum(field: string | FieldPath): SumAggregateField {
  throw new Error("not implemented");
}

// Compares two `AggregateField` instances for equality.
// They are considered "equal" if and only if they are the same type and, for
// those types that specify a field, both specify the exact same field.
function aggregateFieldEqual(left: AggregateField, right: AggregateField): boolean {
  return false;
}

////////////////////////////////////////////////////////////////////////////////
// New aggregate query and snapshot types.
////////////////////////////////////////////////////////////////////////////////

// A type whose values are all `AggregateField` objects.
// This is used as an argument to the "getter" functions, and the snapshot will
// map the same names to the corresponding values.
type AggregateSpec = { [field: string]: AggregateField };

// A type whose keys are taken from an `AggregateSpec` type, and whose values
// are the result of the aggregation performed by the corresponding
// `AggregateField` from the input `AggregateSpec`.
type AggregateSpecData<T extends AggregateSpec> = {
  [Property in keyof T]-?: T[Property]["_datum"];
};

// The result of running an aggregate query.
class AggregateQuerySnapshot<T extends AggregateSpec> {
  type: "AggregateQuerySnapshot";

  // The result's metadata (i.e. fromCache and/or hasPendingWrites).
  readonly metadata: SnapshotMetadata;

  // The underlying query over which the aggregations were performed.
  readonly query: Query<DocumentData>;

  // ignore this for API design purposes; it's here only to facilitate the
  // return type of data().
  private constructor(private readonly _data: AggregateSpecData<T>) {
  }

  // The results of the requested aggregations. The keys of the returned object
  // will be the same as those of the `AggregateSpec` object specified to the
  // aggregation method, and the values will be the corresponding aggregation
  // result.
  data(options: SnapshotOptions = {}): AggregateSpecData<T> {
    return this._data;
  }
}

////////////////////////////////////////////////////////////////////////////////
// New aggregate query and snapshot functions.
////////////////////////////////////////////////////////////////////////////////

// Counts the number of documents in the result set of the given query, without
// actually retrieving the documents. If the server cannot be reached, then the
// results will be served from the local cache.
//
// This is a convenience shorthand for:
//   getAggregate(query, { count: count() }).
function getCount(query: Query):
    Promise<AggregateQuerySnapshot<{ count: CountAggregateField }>> {
  return getAggregate(query, { count: count() });
}

// Counts the number of documents in the result set of the given query, ignoring
// any locally-cached data and any locally-pending writes and simply surfacing
// whatever the server returns. If the server cannot be reached then the
// returned promise will be rejected.
//
// This is a convenience shorthand for:
//   getAggregateFromServer(query, { count: count() })
function getCountFromServer(query: Query):
    Promise<AggregateQuerySnapshot<{ count: CountAggregateField }>> {
  return getAggregateFromServer(query, { count: count() });
}

// Counts the number of documents in the result set of the given query, only
// considering cached results from previous queries. No request will be sent to
// the server, and, therefore, no billed document reads will be incurred.
//
// This is a convenience shorthand for:
//   getAggregateFromCache(query, { count: count() })
function getCountFromCache(query: Query):
    Promise<AggregateQuerySnapshot<{ count: CountAggregateField }>> {
  throw new Error("not implemented");
}

// Runs the given aggregations over the result set of the given query, without
// actually downloading the documents in the result set.
//
// The given `aggregates` maps keys to the corresponding aggregation. The
// resulting snapshot will use the same keys and map them to the result of the
// corresponding aggregation.
//
// Example:
//
// const snapshot = await getAggregate(query, {
//   num_people: count(),
//   min_age: min("age")
// });
// const num_people: number = snapshot.data().num_people;
// const min_age = snapshot.data().min_age ?? 0;
// console.log(`Found ${num_people} people, ` +
//   `the youngest being ${min_age} years old`);
function getAggregate<T extends AggregateSpec>(query: Query, aggregates: T):
    Promise<AggregateQuerySnapshot<T>> {
  throw new Error("not implemented");
}

function getAggregateFromServer<T extends AggregateSpec>(query: Query, aggregates: T):
    Promise<AggregateQuerySnapshot<T>> {
  throw new Error("not implemented");
}

function getAggregateFromCache<T extends AggregateSpec>(query: Query, aggregates: T):
    Promise<AggregateQuerySnapshot<T>> {
  throw new Error("not implemented");
}

// Compares two `AggregateQuerySnapshot` instances for equality.
// Two `AggregateQuerySnapshot` instances are considered "equal" if they have
// the same underlying query, the same metadata, and the same data.
export function aggregateSnapshotEqual<T extends AggregateSpec>(
    left: AggregateQuerySnapshot<T>,
    right: AggregateQuerySnapshot<T>
): boolean {
  return false;
}

////////////////////////////////////////////////////////////////////////////////
// Sample code
////////////////////////////////////////////////////////////////////////////////

async function countDemo(query: Query) {
  const snapshot = await getCountFromServer(query);
  console.log(`Found ${snapshot.data().count} documents`);
}

async function aggregateDemo(query: Query) {
  const snapshot = await getAggregate(query, {
    num_people: count(),
    min_age: min("age"),
    money: sum("salary")
  });
  const num_people: number = snapshot.data().num_people;
  const min_age = snapshot.data().min_age ?? 0;
  const total_salary: number = snapshot.data().money ?? 0;
  console.log(
    `Found ${num_people} people, ` +
    `the youngest being ${min_age} years old ` +
    `earning a total of ${total_salary} dollars.`
  );
}
