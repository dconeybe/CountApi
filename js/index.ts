import { Firestore } from "./FirestoreTypes";
import {
  Demo0_NormalQuery,
  Demo1_CountOfDocumentsInACollection,
  Demo2_CountOfDocumentsInACollectionWithFilter,
  Demo3_MultipleAggregations
} from "./CountApiDemo"

async function main() {
  const db = new Firestore();
  await Demo0_NormalQuery(db);
  await Demo1_CountOfDocumentsInACollection(db);
  await Demo2_CountOfDocumentsInACollectionWithFilter(db);
  await Demo3_MultipleAggregations(db);
}

main();