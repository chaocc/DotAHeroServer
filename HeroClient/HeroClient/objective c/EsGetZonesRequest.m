//
//  Autogenerated by CocoaTouchApiGenerator
//
//  DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//



#import "EsGetZonesRequest.h"
#import "EsThriftUtil.h"

@implementation EsGetZonesRequest


- (id) initWithThriftObject: (id) thriftObject {
	if ((self = [super init])) {
		self.messageType = EsMessageType_GetZonesRequest;
		if (thriftObject != nil) {
			[self fromThrift: thriftObject];
		}
	}
	return self;
}

- (id) init {
	return [self initWithThriftObject: nil];
}

- (void) fromThrift: (id) thriftObject {
}

- (ThriftGetZonesRequest*) toThrift {
	ThriftGetZonesRequest* _t = [[ThriftGetZonesRequest alloc] init];;
	return _t;
}

- (id) newThrift {
	return [[ThriftGetZonesRequest alloc] init];
}


@end
