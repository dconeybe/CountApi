#import "CountApi.h"

@implementation FIRAggregateField

+ (FIRCountAggregateField *)aggregateFieldForCount NS_SWIFT_NAME(count()) {
  return nil;
}

+ (FIRMinAggregateField *)aggregateFieldForMinOfField:(NSString *)ofField NS_SWIFT_NAME(min(_:)) {
  return nil;
}

+ (FIRMinAggregateField *)aggregateFieldForMinOfFieldPath:(FIRFieldPath *)ofFieldPath NS_SWIFT_NAME(min(_:)) {
  return nil;
}

+ (FIRSumAggregateField *)aggregateFieldForSumOfField:(NSString *)ofField NS_SWIFT_NAME(sum(_:)) {
  return nil;
}

+ (FIRSumAggregateField *)aggregateFieldForSumOfFieldPath:(FIRFieldPath *)ofFieldPath NS_SWIFT_NAME(sum(_:)) {
  return nil;
}


@end
