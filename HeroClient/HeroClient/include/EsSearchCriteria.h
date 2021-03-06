//
//  Autogenerated by CocoaTouchApiGenerator
//
//  DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//



#import "EsMessage.h"
#import "EsMessageType.h"
#import "EsRequest.h"
#import "EsResponse.h"
#import "EsEvent.h"
#import "EsEntity.h"
#import "EsObject.h"
#import "ThriftSearchCriteria.h"
#import "EsFlattenedEsObject.h"
#import "ThriftFlattenedEsObject.h"

@interface EsSearchCriteria : EsEntity {
@private
	BOOL gameId_set_;
	int32_t gameId_;
	BOOL locked_set_;
	BOOL locked_;
	BOOL lockedSet_set_;
	BOOL lockedSet_;
	BOOL gameType_set_;
	NSString* gameType_;
	BOOL gameDetails_set_;
	EsObject* gameDetails_;
}

@property(nonatomic) int32_t gameId;
@property(nonatomic) BOOL locked;
@property(nonatomic) BOOL lockedSet;
@property(strong,nonatomic) NSString* gameType;
@property(strong,nonatomic) EsObject* gameDetails;

- (id) init;
- (id) initWithThriftObject: (id) thriftObject;
- (void) fromThrift: (id) thriftObject;
- (ThriftSearchCriteria*) toThrift;
- (id) newThrift;
@end
