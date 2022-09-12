#import <Foundation/Foundation.h>

#import "CountApi.h"

void Demo1_CountOfDocumentsInACollection(FIRQuery *query) {
  [query.count getAggregationWithSource:FIRAggregateSourceServer
      completion:^(FIRAggregateQuerySnapshot *snapshot, NSError *) {
        NSLog(@"%@", snapshot.count);
      }
  ];
}

void Demo2_MinAndMax(FIRQuery *query) {
  FIRAggregateQuery* aggregateQuery = [query aggregate:@[
    [FIRAggregateField aggregateFieldForCount],
    [FIRAggregateField aggregateFieldForMinOfField:@"age"],
    [FIRAggregateField aggregateFieldForSumOfField:@"salary"],
  ]];
  [aggregateQuery getAggregationWithSource:FIRAggregateSourceServer
      completion:^(FIRAggregateQuerySnapshot *snapshot, NSError *) {
        NSLog(@"%@", snapshot.count);
      }
  ];
}
