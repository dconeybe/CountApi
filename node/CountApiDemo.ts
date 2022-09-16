import { expect } from 'chai';

import {
  AggregateField,
  Firestore,
} from "./CountApi"

export async function Demo0_NormalQuery(db: Firestore) {
  const query = db.collection("games/halo/players");
  const snapshot = await query.get();
  expect(snapshot.size).to.equal(5000000);
}

export async function Demo1_CountOfDocumentsInACollection(db: Firestore) {
  const collection = db.collection("games/halo/players");
  const snapshot = await collection.count().get();
  expect(snapshot.data().count).to.equal(5000000);
}

export async function Demo2_CountOfDocumentsInACollectionWithFilter(db: Firestore) {
  const collection = db.collection("games/halo/players");
  const snapshot = await collection.where("online", "==", true).count().get();
  expect(snapshot.data().count).to.equal(2000);
}

export async function Demo3_MultipleAggregations(db: Firestore) {
  const collection = db.collection("games/halo/players");
  const query = await collection.aggregate({
    num_players: AggregateField.count(),
    min_age: AggregateField.min("age"),
    score: AggregateField.sum("points")
  });
  const snapshot = await query.get();
  const num_players: number = snapshot.data().num_players;
  const min_age = snapshot.data().min_age ?? 0;
  const total_points: number = snapshot.data().score ?? 0;
  console.log(
    `Found ${num_players} players, ` +
    `the youngest being ${min_age} years old ` +
    `with a total of ${total_points} points.`
  );
}
