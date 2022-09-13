#ifndef FIRESTORE_COUNTAPI_H_
#define FIRESTORE_COUNTAPI_H_

#import <Foundation/Foundation.h>

#import "FirestoreTypes.h"

////////////////////////////////////////////////////////////////////////////////
// Forward declarations to make the compiler happy.
////////////////////////////////////////////////////////////////////////////////

@class FIRAggregateField;
@class FIRAggregateQuery;
@class FIRAggregateQuerySnapshot;

////////////////////////////////////////////////////////////////////////////////
// The methods added to the existing Query class.
////////////////////////////////////////////////////////////////////////////////

@interface FIRQuery : NSObject

@property(nonatomic, readonly) FIRAggregateQuery* _Nonnull count;

-(FIRAggregateQuery * _Nonnull)aggregate:(NSArray<FIRAggregateField *> * _Nonnull)aggregations;

@end

@interface FIRQuery (BaseMethodsNotPartOfTheApiProposal)

- (void)getDocumentsWithCompletion:
    (void (^)(FIRQuerySnapshot *_Nullable snapshot, NSError *_Nullable error))completion
    NS_SWIFT_NAME(getDocuments(completion:));

- (FIRQuery *)queryWhereField:(NSString *)field
                    isEqualTo:(id)value NS_SWIFT_NAME(whereField(_:isEqualTo:));
@end

NS_SWIFT_NAME(CollectionReference)
@interface FIRCollectionReference : FIRQuery
@end

////////////////////////////////////////////////////////////////////////////////
// New aggregation types.
////////////////////////////////////////////////////////////////////////////////

typedef NS_ENUM(NSUInteger, FIRAggregateSource) {
  FIRAggregateSourceDefault,
  FIRAggregateSourceServer,
  FIRAggregateSourceCache,
} NS_SWIFT_NAME(AggregateSource);

////////////////////////////////////////////////////////////////////////////////

NS_SWIFT_NAME(AggregateField)
@interface FIRAggregateField : NSObject

/** :nodoc: */
- (instancetype _Nonnull)init NS_UNAVAILABLE;
+ (instancetype _Nonnull)aggregateFieldForCount NS_SWIFT_NAME(count());
+ (instancetype _Nonnull)aggregateFieldForMinOfField:(NSString * _Nonnull)field NS_SWIFT_NAME(min(_:));
+ (instancetype _Nonnull)aggregateFieldForMinOfFieldPath:(FIRFieldPath * _Nonnull)fieldPath NS_SWIFT_NAME(min(_:));
+ (instancetype _Nonnull)aggregateFieldForMaxOfField:(NSString * _Nonnull)field NS_SWIFT_NAME(max(_:));
+ (instancetype _Nonnull)aggregateFieldForMaxOfFieldPath:(FIRFieldPath * _Nonnull)fieldPath NS_SWIFT_NAME(max(_:));
+ (instancetype _Nonnull)aggregateFieldForSumOfField:(NSString * _Nonnull)field NS_SWIFT_NAME(sum(_:));
+ (instancetype _Nonnull)aggregateFieldForSumOfFieldPath:(FIRFieldPath * _Nonnull)fieldPath NS_SWIFT_NAME(sum(_:));
+ (instancetype _Nonnull)aggregateFieldForAverageOfField:(NSString * _Nonnull)field NS_SWIFT_NAME(average(_:));
+ (instancetype _Nonnull)aggregateFieldForAverageOfFieldPath:(FIRFieldPath * _Nonnull)fieldPath NS_SWIFT_NAME(average(_:));

@end

////////////////////////////////////////////////////////////////////////////////

NS_SWIFT_NAME(A_NonnullggregateQuery)
@interface FIRAggregateQuery : NSObject

@property(nonatomic, readonly) FIRQuery * _Nonnull query;

-(void)getAggregationWithCompletion:
  (void (^  _Nonnull)(
    FIRAggregateQuerySnapshot *_Nullable snapshot,
    NSError *_Nullable error
  ))completion
  NS_SWIFT_NAME(getAggregation(source:));

-(void)getAggregationWithSource:(FIRAggregateSource)source
  completion:(void (^ _Nonnull)(
    FIRAggregateQuerySnapshot *_Nullable snapshot,
    NSError *_Nullable error
  ))completion
  NS_SWIFT_NAME(getAggregation(source:completion:));

@end

////////////////////////////////////////////////////////////////////////////////

NS_SWIFT_NAME(AggregateQuerySnapshot)
@interface FIRAggregateQuerySnapshot : NSObject

@property(nonatomic, readonly) FIRAggregateQuery * _Nonnull query;
@property(nonatomic, readonly) FIRSnapshotMetadata * _Nonnull metadata;
@property(nonatomic, readonly) NSNumber * _Nonnull count;

- (NSDictionary<id, id> * _Nonnull)aggregations;

// Based on NSUserDefaults
- (NSDictionary<id, id> * _Nonnull)aggregationsWithServerTimestampBehavior:
    (FIRServerTimestampBehavior)serverTimestampBehavior;

- (nullable NSNumber *)valueForSumAggregateField:(FIRAggregateField * _Nonnull)aggregateField NS_SWIFT_NAME(getSum(_:));
- (nullable NSNumber *)valueForAverageAggregateField:(FIRAggregateField * _Nonnull)aggregateField NS_SWIFT_NAME(getAverage(_:));

- (nullable id)valueForField:(id _Nonnull)field NS_SWIFT_NAME(get(_:));

- (nullable id)valueForField:(id _Nonnull)field
     serverTimestampBehavior:(FIRServerTimestampBehavior)serverTimestampBehavior
    NS_SWIFT_NAME(get(_:serverTimestampBehavior:));

- (nullable id)objectForKeyedSubscript:(id _Nonnull)field;

@end

#endif  // FIRESTORE_COUNTAPI_H_
