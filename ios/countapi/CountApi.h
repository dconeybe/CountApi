#ifndef FIRESTORE_COUNTAPI_H_
#define FIRESTORE_COUNTAPI_H_

#import <Foundation/Foundation.h>

#import "FirestoreTypes.h"

NS_ASSUME_NONNULL_BEGIN

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

@property(nonatomic, readonly) FIRAggregateQuery* count;

-(FIRAggregateQuery *)aggregate:(NSArray<FIRAggregateField *> *)aggregations;

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
- (instancetype)init NS_UNAVAILABLE;
+ (instancetype)aggregateFieldForCount NS_SWIFT_NAME(count());
+ (instancetype)aggregateFieldForMinOfField:(NSString *)field NS_SWIFT_NAME(min(_:));
+ (instancetype)aggregateFieldForMinOfFieldPath:(FIRFieldPath *)fieldPath NS_SWIFT_NAME(min(_:));
+ (instancetype)aggregateFieldForMaxOfField:(NSString *)field NS_SWIFT_NAME(max(_:));
+ (instancetype)aggregateFieldForMaxOfFieldPath:(FIRFieldPath *)fieldPath NS_SWIFT_NAME(max(_:));
+ (instancetype)aggregateFieldForSumOfField:(NSString *)field NS_SWIFT_NAME(sum(_:));
+ (instancetype)aggregateFieldForSumOfFieldPath:(FIRFieldPath *)fieldPath NS_SWIFT_NAME(sum(_:));
+ (instancetype)aggregateFieldForAverageOfField:(NSString *)field NS_SWIFT_NAME(average(_:));
+ (instancetype)aggregateFieldForAverageOfFieldPath:(FIRFieldPath *)fieldPath NS_SWIFT_NAME(average(_:));

@end

////////////////////////////////////////////////////////////////////////////////

NS_SWIFT_NAME(AggregateQuery)
@interface FIRAggregateQuery : NSObject

@property(nonatomic, readonly) FIRQuery * query;

-(void)getAggregationWithCompletion:
  (void (^ )(
    FIRAggregateQuerySnapshot *_Nullable snapshot,
    NSError *_Nullable error
  ))completion
  NS_SWIFT_NAME(getAggregation(source:));

-(void)getAggregationWithSource:(FIRAggregateSource)source
  completion:(void (^)(
    FIRAggregateQuerySnapshot *_Nullable snapshot,
    NSError *_Nullable error
  ))completion
  NS_SWIFT_NAME(getAggregation(source:completion:));

@end

////////////////////////////////////////////////////////////////////////////////

NS_SWIFT_NAME(AggregateQuerySnapshot)
@interface FIRAggregateQuerySnapshot : NSObject

@property(nonatomic, readonly) FIRAggregateQuery * query;
@property(nonatomic, readonly) FIRSnapshotMetadata * metadata;
@property(nonatomic, readonly) NSNumber * count;

- (NSDictionary<id, id> *)aggregations;

// Based on NSUserDefaults
- (NSDictionary<id, id> *)aggregationsWithServerTimestampBehavior:
    (FIRServerTimestampBehavior)serverTimestampBehavior;

- (nullable NSNumber *)valueForSumAggregateField:(FIRAggregateField *)aggregateField NS_SWIFT_NAME(getSum(_:));
- (nullable NSNumber *)valueForAverageAggregateField:(FIRAggregateField *)aggregateField NS_SWIFT_NAME(getAverage(_:));

- (nullable id)valueForField:(id)field NS_SWIFT_NAME(get(_:));

- (nullable id)valueForField:(id)field
     serverTimestampBehavior:(FIRServerTimestampBehavior)serverTimestampBehavior
    NS_SWIFT_NAME(get(_:serverTimestampBehavior:));

- (nullable id)objectForKeyedSubscript:(id)field;

@end

NS_ASSUME_NONNULL_END

#endif  // FIRESTORE_COUNTAPI_H_
