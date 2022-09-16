////////////////////////////////////////////////////////////////////////////////
// Types defined already in the Firestore SDK.
////////////////////////////////////////////////////////////////////////////////

export type DocumentFieldValue = any;

export type DocumentData = {[field: string]: DocumentFieldValue};

export class Firestore {
  collection(collectionPath: string): CollectionReference<DocumentData>;
}

export class BaseQuery<T = DocumentData> {
  get(): Promise<QuerySnapshot<T>>;

  where(
    fieldPath: string | FieldPath,
    opStr: WhereFilterOp,
    value: any
  ): Query<T>;
}

export class CollectionReference<T = DocumentData> extends Query<T> {
}

export class QuerySnapshot<T = DocumentData> {
  readonly size: number;
}

export class FieldPath {
}

export type WhereFilterOp =
  | '<'
  | '<='
  | '=='
  | '!='
  | '>='
  | '>'
  | 'array-contains'
  | 'in'
  | 'not-in'
  | 'array-contains-any';

////////////////////////////////////////////////////////////////////////////////
// Types defined already in the Firestore SDK.
////////////////////////////////////////////////////////////////////////////////

export class Query<T = DocumentData> extends BaseQuery<T> {
  count(): AggregateQuery<{ count: AggregateField<number> }>;
  aggregate<T extends AggregateSpec>(aggregates: T): AggregateQuery<T>;
}

export class AggregateField<T> {
  static count(): AggregateField<number>;
  static min(fieldPath: string | FieldPath): AggregateField<DocumentFieldValue | undefined>;
  static max(fieldPath: string | FieldPath): AggregateField<DocumentFieldValue | undefined>;
  static sum(fieldPath: string | FieldPath): AggregateField<number | undefined>;
  static average(fieldPath: string | FieldPath): AggregateField<number | undefined>;
  isEqual(other: AggregateField<T>): boolean;
}

export class AggregateQuery<T extends AggregateSpec> {
  readonly query: Query<unknown>;

  get(): Promise<AggregateQuerySnapshot<T>>;

  isEqual(other: AggregateQuery<T>): boolean;
}

export class AggregateQuerySnapshot<T extends AggregateSpec> {
  readonly query: AggregateQuery<T>;

  data(): AggregateSpecData<T>;

  isEqual(other: AggregateQuerySnapshot<T>): boolean;
}

export type AggregateFieldType =
  | ReturnType<typeof AggregateField.count>
  | ReturnType<typeof AggregateField.min>
  | ReturnType<typeof AggregateField.max>
  | ReturnType<typeof AggregateField.sum>
  | ReturnType<typeof AggregateField.average>;

export interface AggregateSpec {
  [field: string]: AggregateFieldType
}

export type AggregateSpecData<T extends AggregateSpec> = {
  [P in keyof T]: T[P] extends AggregateField<infer U> ? U : never
}
