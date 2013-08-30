//
//  Autogenerated by CocoaTouchApiGenerator
//
//  DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//



#import "EsUserServerVariable.h"
#import "EsThriftUtil.h"

@implementation EsUserServerVariable

@synthesize name = name_;
@synthesize value = value_;

- (id) initWithThriftObject: (id) thriftObject {
	if ((self = [super init])) {
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
	ThriftUserServerVariable* t = (ThriftUserServerVariable*) thriftObject;
	if ([t nameIsSet] && t.name != nil) {
		self.name = t.name;
	}
	if ([t valueIsSet] && t.value != nil) {
		self.value = [EsThriftUtil unflattenEsObject:[[EsFlattenedEsObject alloc] initWithThriftObject:t.value]];
	}
}

- (ThriftUserServerVariable*) toThrift {
	ThriftUserServerVariable* _t = [[ThriftUserServerVariable alloc] init];;
	if (name_set_ && name_ != nil) {
		NSString* _name;
		_name = self.name;
		_t.name = _name;
	}
	if (value_set_ && value_ != nil) {
		ThriftFlattenedEsObject* _value;
		_value = [[EsThriftUtil flattenEsObject:self.value] toThrift];
		_t.value = _value;
	}
	return _t;
}

- (id) newThrift {
	return [[ThriftUserServerVariable alloc] init];
}

- (void) setName: (NSString*) name {
	name_ = name;
	name_set_ = true;
}

- (void) setValue: (EsObject*) value {
	value_ = value;
	value_set_ = true;
}

- (void) dealloc {
	self.value = nil;
}

@end