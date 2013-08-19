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
#import "ThriftRoomListEntry.h"

@interface EsRoomListEntry : EsEntity {
@private
	BOOL roomId_set_;
	int32_t roomId_;
	BOOL zoneId_set_;
	int32_t zoneId_;
	BOOL roomName_set_;
	NSString* roomName_;
	BOOL userCount_set_;
	int32_t userCount_;
	BOOL roomDescription_set_;
	NSString* roomDescription_;
	BOOL capacity_set_;
	int32_t capacity_;
	BOOL hasPassword_set_;
	BOOL hasPassword_;
}

@property(nonatomic) int32_t roomId;
@property(nonatomic) int32_t zoneId;
@property(strong,nonatomic) NSString* roomName;
@property(nonatomic) int32_t userCount;
@property(strong,nonatomic) NSString* roomDescription;
@property(nonatomic) int32_t capacity;
@property(nonatomic) BOOL hasPassword;

- (id) init;
- (id) initWithThriftObject: (id) thriftObject;
- (void) fromThrift: (id) thriftObject;
- (ThriftRoomListEntry*) toThrift;
- (id) newThrift;
@end
