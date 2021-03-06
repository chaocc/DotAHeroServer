//
//  Autogenerated by CocoaTouchApiGenerator
//
//  DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//



#import "EsConnectionClosedEvent.h"
#import "EsThriftUtil.h"

@implementation EsConnectionClosedEvent

@synthesize connectionId = connectionId_;

- (id) initWithThriftObject: (id) thriftObject {
	if ((self = [super init])) {
		self.messageType = EsMessageType_ConnectionClosedEvent;
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
	ThriftConnectionClosedEvent* t = (ThriftConnectionClosedEvent*) thriftObject;
	if ([t connectionIdIsSet]) {
		self.connectionId = t.connectionId;
	}
}

- (ThriftConnectionClosedEvent*) toThrift {
	ThriftConnectionClosedEvent* _t = [[ThriftConnectionClosedEvent alloc] init];;
	if (connectionId_set_) {
		int32_t _connectionId;
		_connectionId = self.connectionId;
		_t.connectionId = _connectionId;
	}
	return _t;
}

- (id) newThrift {
	return [[ThriftConnectionClosedEvent alloc] init];
}

- (void) setConnectionId: (int32_t) connectionId {
	connectionId_ = connectionId;
	connectionId_set_ = true;
}


@end
