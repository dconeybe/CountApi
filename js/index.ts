////////////////////////////////////////////////////////////////////////////////
// Types defined already in the Firestore SDK
////////////////////////////////////////////////////////////////////////////////

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
  _datum?: T;
}

class CountAggregateField implements AggregateFieldBase<number> {
  type = "CountAggregateField";
  _datum?: number = undefined;
}

class MinAggregateField implements AggregateFieldBase<DocumentFieldValue | undefined> {
  type = "MinAggregateField";
  _datum?: DocumentFieldValue = undefined;
}

class SumAggregateField implements AggregateFieldBase<number | undefined> {
  type = "SumAggregateField";
  _datum?: number = undefined;
}

type AggregateField =
    CountAggregateField
  | MinAggregateField
  | SumAggregateField;

////////////////////////////////////////////////////////////////////////////////
// New AggregateField functions.
////////////////////////////////////////////////////////////////////////////////

function count(): CountAggregateField {
  throw new Error("not implemented");
}

function min(field: string | FieldPath): MinAggregateField {
  throw new Error("not implemented");
}

function sum(field: string | FieldPath): SumAggregateField {
  throw new Error("not implemented");
}

function aggregateFieldEqual(left: AggregateField, right: AggregateField): boolean {
  return false;
}

////////////////////////////////////////////////////////////////////////////////
// New aggregate query and snapshot types.
////////////////////////////////////////////////////////////////////////////////

type AggregateSpec = { [field: string]: AggregateField };

type AggregateSpecData<T extends AggregateSpec> = {
  [Property in keyof T]-?: T[Property]["_datum"];
};

class AggregateQuerySnapshot<T extends AggregateSpec> {
  type: "AggregateQuerySnapshot";

  readonly metadata: SnapshotMetadata;
  readonly query: Query<DocumentData>;

  constructor(private readonly _data: AggregateSpecData<T>) {
  }

  data(options: SnapshotOptions = {}): AggregateSpecData<T> {
    return this._data;
  }
}

////////////////////////////////////////////////////////////////////////////////
// New aggregate query and snapshot functions.
////////////////////////////////////////////////////////////////////////////////

function getCount(query: Query): Promise<AggregateQuerySnapshot<{ count: CountAggregateField }>> {
  throw new Error("not implemented");
}

function getCountFromServer(query: Query): Promise<AggregateQuerySnapshot<{ count: CountAggregateField }>> {
  throw new Error("not implemented");
}

function getCountFromCache(query: Query): Promise<AggregateQuerySnapshot<{ count: CountAggregateField }>> {
  throw new Error("not implemented");
}

function getAggregate<T extends AggregateSpec>(query: Query, aggregates: T): Promise<AggregateQuerySnapshot<T>> {
  throw new Error("not implemented");
}

function getAggregateFromServer<T extends AggregateSpec>(query: Query, aggregates: T): Promise<AggregateQuerySnapshot<T>> {
  throw new Error("not implemented");
}

function getAggregateFromCache<T extends AggregateSpec>(query: Query, aggregates: T): Promise<AggregateQuerySnapshot<T>> {
  throw new Error("not implemented");
}

export function aggregateSnapshotEqual<T extends AggregateSpec>(left: AggregateQuerySnapshot<T>, right: AggregateQuerySnapshot<T>): boolean {
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
    size: count(),
    min_age: min("age"),
    money: sum("salary")
  });

  const data = snapshot.data();
  const num_people: number = data.size;
  const youngest_age: string = data.min_age === undefined ? "unknown" : ("" + data.min_age);
  const total_salary: number = data.money === undefined ? -1 : data.money;

  console.log(`Found ${num_people} people, ` +
    `the youngest being ${youngest_age} years old, ` +
    `earning a total of ${total_salary}`);
}
