//
//  Autogenerated by CocoaTouchApiGenerator
//
//  DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//



#import "EsDeleteUserVariableRequest.h"
#import "EsThriftUtil.h"

@implementation EsDeleteUserVariableRequest

@synthesize name = name_;

- (id) initWithThriftObject: (id) thriftObject {
	if ((self = [super init])) {
		self.messageType = EsMessageType_DeleteUserVariableRequest;
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
	ThriftDeleteUserVariableRequest* t = (ThriftDeleteUserVariableRequest*) thriftObject;
	if ([t nameIsSet] && t.name != nil) {
		self.name = t.name;
	}
}

- (ThriftDeleteUserVariableRequest*) toThrift {
	ThriftDeleteUserVariableRequest* _t = [[ThriftDeleteUserVariableRequest alloc] init];;
	if (name_set_ && name_ != nil) {
		NSString* _name;
		_name = self.name;
		_t.name = _name;
	}
	return _t;
}

- (id) newThrift {
	return [[ThriftDeleteUserVariableRequest alloc] init];
}

- (void) setName: (NSString*) name {
	name_ = name;
	name_set_ = true;
}


@end
