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
#import "ThriftPingResponse.h"

@interface EsPingResponse : EsRequest {
@private
	BOOL globalResponseRequested_set_;
	BOOL globalResponseRequested_;
	BOOL pingRequestId_set_;
	int32_t pingRequestId_;
}

@property(nonatomic) BOOL globalResponseRequested;
@property(nonatomic) int32_t pingRequestId;

- (id) init;
- (id) initWithThriftObject: (id) thriftObject;
- (void) fromThrift: (id) thriftObject;
- (ThriftPingResponse*) toThrift;
- (id) newThrift;
@end
