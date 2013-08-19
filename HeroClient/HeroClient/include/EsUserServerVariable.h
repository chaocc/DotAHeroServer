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
#import "ThriftUserServerVariable.h"
#import "EsFlattenedEsObject.h"
#import "ThriftFlattenedEsObject.h"

@interface EsUserServerVariable : EsEntity {
@private
	BOOL name_set_;
	NSString* name_;
	BOOL value_set_;
	EsObject* value_;
}

@property(strong,nonatomic) NSString* name;
@property(strong,nonatomic) EsObject* value;

- (id) init;
- (id) initWithThriftObject: (id) thriftObject;
- (void) fromThrift: (id) thriftObject;
- (ThriftUserServerVariable*) toThrift;
- (id) newThrift;
@end
