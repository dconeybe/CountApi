#import <Foundation/Foundation.h>

////////////////////////////////////////////////////////////////////////////////
// Forward declarations to make the compiler happy.
////////////////////////////////////////////////////////////////////////////////

@class FIRAggregateField;
@class FIRAggregateQuery;
@class FIRAggregateQuerySnapshot;
@class FIRCountAggregateField;
@class FIRFieldPath;
@class FIRMinAggregateField;
@class FIRSnapshotMetadata;
@class FIRSumAggregateField;
@class FIRTimestamp;

typedef NS_ENUM(NSInteger, FIRServerTimestampBehavior) {
  FIRServerTimestampBehaviorNone,
  FIRServerTimestampBehaviorEstimate,
  FIRServerTimestampBehaviorPrevious
} NS_SWIFT_NAME(ServerTimestampBehavior);

////////////////////////////////////////////////////////////////////////////////
// The methods added to the existing Query class.
////////////////////////////////////////////////////////////////////////////////

@interface FIRQuery : NSObject

@property(nonatomic, readonly) FIRAggregateQuery *count;

-(FIRAggregateQuery*)aggregate:(NSArray<FIRAggregateField*> *)aggregations;

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
@interface FIRAggregateField : NSObject <NSCopying>

/** :nodoc: */
- (instancetype)init NS_UNAVAILABLE;
+ (FIRCountAggregateField *)aggregateFieldForCount NS_SWIFT_NAME(count());
+ (FIRMinAggregateField *)aggregateFieldForMinOfField:(NSString *)ofField NS_SWIFT_NAME(min(_:));
+ (FIRMinAggregateField *)aggregateFieldForMinOfFieldPath:(FIRFieldPath *)ofFieldPath NS_SWIFT_NAME(min(_:));
+ (FIRSumAggregateField *)aggregateFieldForSumOfField:(NSString *)ofField NS_SWIFT_NAME(sum(_:));
+ (FIRSumAggregateField *)aggregateFieldForSumOfFieldPath:(FIRFieldPath *)ofFieldPath NS_SWIFT_NAME(sum(_:));

@end

NS_SWIFT_NAME(CountAggregateField)
@interface FIRCountAggregateField : FIRAggregateField
- (instancetype)init NS_UNAVAILABLE;
@end

NS_SWIFT_NAME(MinAggregateField)
@interface FIRMinAggregateField : FIRAggregateField
- (instancetype)init NS_UNAVAILABLE;
@end

NS_SWIFT_NAME(SumAggregateField)
@interface FIRSumAggregateField : FIRAggregateField
- (instancetype)init NS_UNAVAILABLE;
@end

////////////////////////////////////////////////////////////////////////////////

NS_SWIFT_NAME(AggregateQuery)
@interface FIRAggregateQuery : NSObject

@property(nonatomic, readonly) FIRQuery *query;

-(void)getAggregationWithCompletion:
  (void (^)(
    FIRAggregateQuerySnapshot *_Nullable snapshot,
    NSError *_Nullable error
  ))completion
  NS_SWIFT_NAME(aggregation(source:));

-(void)getAggregationWithSource:(FIRAggregateSource)source
  completion:(void (^)(
    FIRAggregateQuerySnapshot *_Nullable snapshot,
    NSError *_Nullable error
  ))completion
  NS_SWIFT_NAME(aggregation(source:completion:));

@end

////////////////////////////////////////////////////////////////////////////////

NS_SWIFT_NAME(AggregateQuerySnapshot)
@interface FIRAggregateQuerySnapshot : NSObject

@property(nonatomic, readonly) FIRAggregateQuery *query;
@property(nonatomic, readonly) FIRSnapshotMetadata *metadata;
@property(nonatomic, readonly, nullable) NSNumber *count;

- (NSDictionary<id, id> *)aggregations;

// Based on NSUserDefaults
- (NSDictionary<id, id> *)aggregationsWithServerTimestampBehavior:
    (FIRServerTimestampBehavior)serverTimestampBehavior;

- (nullable id)valueForField:(id)field NS_SWIFT_NAME(value(forField:));

- (nullable id)valueForField:(id)field
     serverTimestampBehavior:(FIRServerTimestampBehavior)serverTimestampBehavior
    NS_SWIFT_NAME(value(forField:serverTimestampBehavior:));

- (nullable id)objectForKeyedSubscript:(id)field;

@end
