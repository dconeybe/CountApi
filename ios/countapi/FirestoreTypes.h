#import <Foundation/Foundation.h>

@class FIRCollectionReference;
@class FIRFieldPath;
@class FIRQuery;
@class FIRQueryDocumentSnapshot;
@class FIRSnapshotMetadata;
@class FIRTimestamp;

typedef NS_ENUM(NSInteger, FIRServerTimestampBehavior) {
  FIRServerTimestampBehaviorNone,
  FIRServerTimestampBehaviorEstimate,
  FIRServerTimestampBehaviorPrevious
} NS_SWIFT_NAME(ServerTimestampBehavior);

@interface FIRFirestore : NSObject
- (FIRCollectionReference *)collectionWithPath:(NSString *)collectionPath
    NS_SWIFT_NAME(collection(_:));
@end

NS_SWIFT_NAME(QuerySnapshot)
@interface FIRQuerySnapshot : NSObject
@property(nonatomic, readonly) NSInteger count;
@property(nonatomic, strong, readonly) NSArray<FIRQueryDocumentSnapshot *> *documents;
@end

NS_SWIFT_NAME(DocumentSnapshot)
@interface FIRDocumentSnapshot : NSObject
- (nullable id)valueForField:(id)field NS_SWIFT_NAME(get(_:));
@end

NS_SWIFT_NAME(QueryDocumentSnapshot)
@interface FIRQueryDocumentSnapshot : FIRDocumentSnapshot
@end
