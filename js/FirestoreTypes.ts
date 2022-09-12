////////////////////////////////////////////////////////////////////////////////
// Types defined already in the Firestore SDK.
////////////////////////////////////////////////////////////////////////////////

// DocumentFieldValue doesn't actually exist today; however, discussing this API
// with Sebastian, we thought we should add it for code readability.
export type DocumentFieldValue = any;

export interface DocumentData {
  [field: string]: DocumentFieldValue;
}

export class Firestore {
  type = "Firestore";
}

export interface SnapshotOptions {
  readonly serverTimestamps?: 'estimate' | 'previous' | 'none';
}

export class SnapshotMetadata {
  constructor(readonly hasPendingWrites: boolean, readonly fromCache: boolean) {
  }
}

export class Query<T = DocumentData> {
  type: "Query" | "CollectionReference";

  constructor(type: "Query" | "CollectionReference") {
    this.type = type;
  }
}

export class CollectionReference<T> extends Query<T> {
  constructor() {
    super("CollectionReference");
  }
}

export class FieldPath {
  type = "FieldPath";
}

export class QuerySnapshot {
  type = "QuerySnapshot";
  constructor(readonly size: number) {
  }
}

export class Filter {
  type = "Filter";
}

export function collection(db: Firestore, path: string): CollectionReference<DocumentData> {
  throw new Error("not implemented");
}

export function query<T>(coll: CollectionReference<T>, filter: Filter): Query<T> {
  throw new Error("not implemented");
}

export function getDocs(query: Query): Promise<QuerySnapshot> {
  throw new Error("not implemented");
}

export function where(field: string, op: "==", value: DocumentFieldValue): Filter {
  throw new Error("not implemented");
}
