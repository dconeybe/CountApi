import { expect } from 'chai';

////////////////////////////////////////////////////////////////////////////////
// Types defined already in the Firestore SDK.
// They are copied here so that the code that follows will compile.
////////////////////////////////////////////////////////////////////////////////

// DocumentFieldValue doesn't actually exist today; however, discussing this API
// with Sebastian, we thought we should add it for code readability.
type DocumentFieldValue = any;

interface DocumentData {
  [field: string]: DocumentFieldValue;
}

class Firestore {
  type = "Firestore";
}

interface SnapshotOptions {
  readonly serverTimestamps?: 'estimate' | 'previous' | 'none';
}

export class SnapshotMetadata {
  constructor(readonly hasPendingWrites: boolean, readonly fromCache: boolean) {
  }
}

class Query<T = DocumentData> {
  type: "Query" | "CollectionReference";

  constructor(type: "Query" | "CollectionReference") {
    this.type = type;
  }
}

class CollectionReference<T> extends Query<T> {
  constructor() {
    super("CollectionReference");
  }
}

class FieldPath {
  type = "FieldPath";
}

class QuerySnapshot {
  type = "QuerySnapshot";
  constructor(readonly size: number) {
  }
}

class Filter {
  type = "Filter";
}

function collection(db: Firestore, path: string): CollectionReference<DocumentData> {
  throw new Error("not implemented");
}

function query<T>(coll: CollectionReference<T>, filter: Filter): Query<T> {
  throw new Error("not implemented");
}

function getDocs(query: Query): Promise<QuerySnapshot> {
  throw new Error("not implemented");
}

function where(field: string, op: "==", value: DocumentFieldValue): Filter {
  throw new Error("not implemented");
}

////////////////////////////////////////////////////////////////////////////////
// New AggregateField type and functions.
////////////////////////////////////////////////////////////////////////////////

class AggregateField<T> {
  type = "AggregateField";
}

// Creates and returns an aggregation that counts the documents in the result
// set.
function count(): AggregateField<number> {
  throw new Error("not implemented");
}

// Creates and returns an aggregation that finds the minimum value of a given
// field in the result set. If no documents in the result define the given
// field, then the result will be `undefined`. The "minimum" will be calculated
// as if the query results were ordered by the specified field and choosing the
// value from the first document in the result set.
function min(field: string | FieldPath):
    AggregateField<DocumentFieldValue | undefined> {
  throw new Error("not implemented");
}

// Creates and returns an aggregation that finds the maximum value of a given
// field in the result set. If no documents in the result define the given
// field, then the result will be `undefined`. The "maximum" will be calculated
// as if the query results were ordered by the specified field and choosing the
// value from the last document in the result set.
function max(field: string | FieldPath):
    AggregateField<DocumentFieldValue | undefined> {
  throw new Error("not implemented");
}

// Creates and returns an aggregation that finds the sum of the numeric values
// of a given field in the result set. If no documents in the result set define
// the given field, or none of the field values are numeric, then the result
// will be `undefined`.
function sum(field: string | FieldPath):
    AggregateField<number | undefined> {
  throw new Error("not implemented");
}

// Creates and returns an aggregation that finds the average of the numeric
// values of a given field in the result set. If no documents in the result set
// define the given field, or none of the field values are numeric, then the
// result will be `undefined`.
function average(field: string | FieldPath):
    AggregateField<number | undefined> {
  throw new Error("not implemented");
}

// Compares two `AggregateField` instances for equality.
// The two `AggregateField` instances are considered "equal" if and only if
// they were created by the same factory function (e.g. `count()`, `min()`, and
// `sum()`) with "equal" arguments.
function aggregateFieldEqual(
    left: AggregateField<unknown>,
    right: AggregateField<unknown>
): boolean {
  throw new Error("not implemented");
}

////////////////////////////////////////////////////////////////////////////////
// New aggregate query and snapshot types.
////////////////////////////////////////////////////////////////////////////////

// The union of all `AggregateField` types that are returned from the factory
// functions.
type AggregateFieldType =
  | ReturnType<typeof count>
  | ReturnType<typeof min>
  | ReturnType<typeof max>
  | ReturnType<typeof sum>
  | ReturnType<typeof average>;

// A type whose values are all `AggregateField` objects.
// This is used as an argument to the "getter" functions, and the snapshot will
// map the same names to the corresponding values.
interface AggregateSpec {
  [field: string]: AggregateFieldType
}

// A type whose keys are taken from an `AggregateSpec` type, and whose values
// are the result of the aggregation performed by the corresponding
// `AggregateField` from the input `AggregateSpec`.
type AggregateSpecData<T extends AggregateSpec> = {
  [P in keyof T]: T[P] extends AggregateField<infer U> ? U : never
}

// The result of running an aggregate query.
class AggregateQuerySnapshot<T extends AggregateSpec> {
  type = "AggregateQuerySnapshot";

  // The result's metadata (i.e. fromCache and/or hasPendingWrites).
  readonly metadata: SnapshotMetadata;

  // The underlying query over which the aggregations were performed.
  readonly query: Query<unknown>;

  // ignore this for API design purposes; it's here only to facilitate the
  // return type of data().
  private constructor(private readonly _data: AggregateSpecData<T>,
                      metadata: SnapshotMetadata,
                      query: Query<unknown>) {
    this.metadata = metadata;
    this.query = query;
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
function getCount(query: Query<unknown>):
    Promise<AggregateQuerySnapshot<{ count: AggregateField<number> }>> {
  return getAggregate(query, { count: count() });
}

// Counts the number of documents in the result set of the given query, ignoring
// any locally-cached data and any locally-pending writes and simply surfacing
// whatever the server returns. If the server cannot be reached then the
// returned promise will be rejected.
//
// This is a convenience shorthand for:
//   getAggregateFromServer(query, { count: count() })
function getCountFromServer(query: Query<unknown>):
    Promise<AggregateQuerySnapshot<{ count: AggregateField<number> }>> {
  return getAggregateFromServer(query, { count: count() });
}

// Counts the number of documents in the result set of the given query, only
// considering cached results from previous queries. No request will be sent to
// the server, and, therefore, no billed document reads will be incurred.
//
// This is a convenience shorthand for:
//   getAggregateFromCache(query, { count: count() })
function getCountFromCache(query: Query<unknown>):
    Promise<AggregateQuerySnapshot<{ count: AggregateField<number> }>> {
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
function getAggregate<T extends AggregateSpec>(
    query: Query<unknown>,
    aggregates: T): Promise<AggregateQuerySnapshot<T>> {
  throw new Error("not implemented");
}

function getAggregateFromServer<T extends AggregateSpec>(
    query: Query<unknown>,
    aggregates: T): Promise<AggregateQuerySnapshot<T>> {
  throw new Error("not implemented");
}

function getAggregateFromCache<T extends AggregateSpec>(
    query: Query<unknown>,
    aggregates: T): Promise<AggregateQuerySnapshot<T>> {
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

async function Demo0_NormalQuery(db: Firestore) {
  const query_ = collection(db, "games/halo/players");
  const snapshot = await getDocs(query_);
  expect(snapshot.size).to.equal(5000000);
}

async function Demo1_CountOfDocumentsInACollection(db: Firestore) {
  const coll = collection(db, "games/halo/players");
  const snapshot = await getCountFromServer(coll);
  expect(snapshot.data().count).to.equal(5000000);
}

async function Demo2_CountOfDocumentsInACollectionWithFilter(db: Firestore) {
  const coll = collection(db, "games/halo/players");
  const query_ = query(coll, where("online", "==", true));
  const snapshot = await getCountFromServer(query_);
  expect(snapshot.data().count).to.equal(2000);
}

async function Demo2_MultipleAggregations(db: Firestore) {
  const coll = collection(db, "games/halo/players");
  const snapshot = await getAggregate(coll, {
    num_players: count(),
    min_age: min("age"),
    score: sum("score")
  });
  const num_players: number = snapshot.data().num_players;
  const min_age = snapshot.data().min_age ?? 0;
  const total_points: number = snapshot.data().score ?? 0;
  console.log(
    `Found ${num_players} players, ` +
    `the youngest being ${min_age} years old ` +
    `with a total of ${total_points} points.`
  );
}
