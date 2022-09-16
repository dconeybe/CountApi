import { Firestore } from "./FirestoreTypes";
import * as demo from "./CountApiDemo"

async function main() {
  const db = new Firestore();
  await demo.Demo0_NormalQuery(db);
  await demo.Demo1_CountOfDocumentsInACollection(db);
  await demo.Demo2_CountOfDocumentsInACollectionWithFilter(db);
  await demo.Demo3_MultipleAggregations(db);
}

main();