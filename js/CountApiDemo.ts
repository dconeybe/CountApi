import { expect } from 'chai';

import {
  Firestore,
  collection,
  count,
  getAggregate,
  getCountFromServer,
  getDocs,
  min,
  query,
  sum,
  where
} from "./CountApi"

export async function Demo0_NormalQuery(db: Firestore) {
  const query_ = collection(db, "games/halo/players");
  const snapshot = await getDocs(query_);
  expect(snapshot.size).to.equal(5000000);
}

export async function Demo1_CountOfDocumentsInACollection(db: Firestore) {
  const coll = collection(db, "games/halo/players");
  const snapshot = await getCountFromServer(coll);
  expect(snapshot.data().count).to.equal(5000000);
}

export async function Demo2_CountOfDocumentsInACollectionWithFilter(db: Firestore) {
  const coll = collection(db, "games/halo/players");
  const query_ = query(coll, where("online", "==", true));
  const snapshot = await getCountFromServer(query_);
  expect(snapshot.data().count).to.equal(2000);
}

export async function Demo3_MultipleAggregations(db: Firestore) {
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
