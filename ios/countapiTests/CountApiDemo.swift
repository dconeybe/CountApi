import XCTest

public func Demo0_NormalQuery(db: Firestore) async throws {
  let query = db.collection("games/halo/players")
  let snapshot = try await query!.getDocuments()
  XCTAssertEqual(snapshot.count, 5000000)
  XCTAssertEqual(snapshot.documents[0].data()["name"] as? String, "player0")
}

public func Demo1_CountOfDocumentsInACollection(db: Firestore) async throws {
  let query = db.collection("games/halo/players")
  let snapshot = try await query!.count.getAggregation(source: .server)
  XCTAssertEqual(snapshot.count, 5000000)
}

public func Demo2_CountOfDocumentsInACollectionWithFilter(db: Firestore) async throws {
  let collection = db.collection("games/halo/players")
  let query = collection!.whereField("online", isEqualTo: true)
  let snapshot = try await query!.count.getAggregation(source: .server)
  XCTAssertEqual(snapshot.count, 2000)
}

public func Demo3_MultipleAggregations(db: Firestore) async throws {
  let collection = db.collection("games/halo/players")
  let query = collection!.aggregate([
    AggregateField.count(),
    AggregateField.min("age"),
    AggregateField.sum("score"),
  ])
  let snapshot = try await query.getAggregation(source: .server)
  let minAge = snapshot.get(AggregateField.min("age")) ?? 0
  let totalPoints = snapshot.getSum(AggregateField.sum("score")) ?? 0
  NSLog("Found %@ players, the youngest being %@ years old with a total of %@ points.",
    [snapshot.count, minAge, totalPoints]);
}
