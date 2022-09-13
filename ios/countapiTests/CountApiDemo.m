#import <Foundation/Foundation.h>
#import <XCTest/XCTest.h>

#import "FirestoreTypes.h"
#import "CountApi.h"

// This first demo function just showcases how "normal" document queries are performed today.
void Demo0_NormalQuery(FIRFirestore* db) {
  FIRCollectionReference* query = [db collectionWithPath:@"games/halo/players"];
  [query getDocumentsWithCompletion:^(FIRQuerySnapshot *snapshot, NSError *) {
    XCTAssertEqual(snapshot.count, 5000000);
    XCTAssertEqual([snapshot.documents[0] valueForField:@"name"], @"player0");
  }];
}

void Demo1_CountOfDocumentsInACollection(FIRFirestore* db) {
  FIRCollectionReference* collection = [db collectionWithPath:@"games/halo/players"];
  [collection.count getAggregationWithSource:FIRAggregateSourceServer
      completion:^(FIRAggregateQuerySnapshot *snapshot, NSError *) {
        XCTAssertEqual(snapshot.count.intValue, 5000000);
      }
  ];
}

void Demo2_CountOfDocumentsInACollectionWithFilter(FIRFirestore* db) {
  FIRCollectionReference* collection = [db collectionWithPath:@"games/halo/players"];
  FIRQuery* query = [collection queryWhereField:@"online" isEqualTo:@YES];
  [query.count getAggregationWithSource:FIRAggregateSourceServer
      completion:^(FIRAggregateQuerySnapshot *snapshot, NSError *) {
        XCTAssertEqual(snapshot.count.intValue, 2000);
      }
  ];
}

void Demo3_MultipleAggregations(FIRFirestore* db) {
  FIRCollectionReference* collection = [db collectionWithPath:@"games/halo/players"];
  FIRAggregateQuery* aggregateQuery = [collection aggregate:@[
    [FIRAggregateField aggregateFieldForCount],
    [FIRAggregateField aggregateFieldForMinOfField:@"age"],
    [FIRAggregateField aggregateFieldForSumOfField:@"score"],
  ]];
  [aggregateQuery getAggregationWithSource:FIRAggregateSourceServer
      completion:^(FIRAggregateQuerySnapshot *snapshot, NSError *) {
        id minAge = snapshot[[FIRAggregateField aggregateFieldForMinOfField:@"age"]];
        if (minAge == nil) {
          minAge = 0;
        }
        NSNumber* totalPoints = [snapshot valueForSumAggregateField:[FIRAggregateField aggregateFieldForSumOfField:@"score"]];
        if (totalPoints == nil) {
          totalPoints = @(0);
        }
        NSLog(@"Found %@ players, the youngest being %@ years old with a total of %@ points.",
          snapshot.count, minAge, totalPoints);
      }
  ];
}

