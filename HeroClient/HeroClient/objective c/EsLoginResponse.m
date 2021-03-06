//
//  Autogenerated by CocoaTouchApiGenerator
//
//  DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//



#import "EsLoginResponse.h"
#import "EsThriftUtil.h"

@implementation EsLoginResponse

@synthesize successful = successful_;
@synthesize error = error_;
@synthesize esObject = esObject_;
@synthesize userName = userName_;
@synthesize userVariables = userVariables_;
@synthesize buddyListEntries = buddyListEntries_;

- (id) initWithThriftObject: (id) thriftObject {
	if ((self = [super init])) {
		self.messageType = EsMessageType_LoginResponse;
		self.userVariables = [NSMutableDictionary dictionary];
		self.buddyListEntries = [NSMutableDictionary dictionary];
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
	ThriftLoginResponse* t = (ThriftLoginResponse*) thriftObject;
	if ([t successfulIsSet]) {
		self.successful = t.successful;
	}
	if ([t errorIsSet]) {
		self.error = t.error;
	}
	if ([t esObjectIsSet] && t.esObject != nil) {
		self.esObject = [EsThriftUtil unflattenEsObjectRO:[[EsFlattenedEsObjectRO alloc] initWithThriftObject:t.esObject]];
	}
	if ([t userNameIsSet] && t.userName != nil) {
		self.userName = t.userName;
	}
	if ([t userVariablesIsSet] && t.userVariables != nil) {
		self.userVariables = [[NSMutableDictionary alloc] init];
		for (NSString* _tKeyVar_0 in t.userVariables) {
			ThriftFlattenedEsObjectRO* _tValVar_0 = [t.userVariables objectForKey:_tKeyVar_0];
			NSString* _keyVar_0;
			_keyVar_0 = _tKeyVar_0;
			EsObject* _valVar_0;
			_valVar_0 = [EsThriftUtil unflattenEsObjectRO:[[EsFlattenedEsObjectRO alloc] initWithThriftObject:_tValVar_0]];
			[self.userVariables setObject:_valVar_0 forKey:_keyVar_0];
		}
	}
	if ([t buddyListEntriesIsSet] && t.buddyListEntries != nil) {
		self.buddyListEntries = [[NSMutableDictionary alloc] init];
		for (NSString* _tKeyVar_0 in t.buddyListEntries) {
			ThriftFlattenedEsObjectRO* _tValVar_0 = [t.buddyListEntries objectForKey:_tKeyVar_0];
			NSString* _keyVar_0;
			_keyVar_0 = _tKeyVar_0;
			EsObject* _valVar_0;
			_valVar_0 = [EsThriftUtil unflattenEsObjectRO:[[EsFlattenedEsObjectRO alloc] initWithThriftObject:_tValVar_0]];
			[self.buddyListEntries setObject:_valVar_0 forKey:_keyVar_0];
		}
	}
}

- (ThriftLoginResponse*) toThrift {
	ThriftLoginResponse* _t = [[ThriftLoginResponse alloc] init];;
	if (successful_set_) {
		BOOL _successful;
		_successful = self.successful;
		_t.successful = _successful;
	}
	if (error_set_) {
		int _error;
		_error = self.error;
		_t.error = _error;
	}
	if (esObject_set_ && esObject_ != nil) {
		ThriftFlattenedEsObjectRO* _esObject;
		_esObject = [[EsThriftUtil flattenEsObjectRO:self.esObject] toThrift];
		_t.esObject = _esObject;
	}
	if (userName_set_ && userName_ != nil) {
		NSString* _userName;
		_userName = self.userName;
		_t.userName = _userName;
	}
	if (userVariables_set_ && userVariables_ != nil) {
		NSMutableDictionary* _userVariables;
		_userVariables = [[NSMutableDictionary alloc] init];
		for (NSString* _tKeyVar_0 in self.userVariables) {
			EsObject* _tValVar_0 = [self.userVariables objectForKey:_tKeyVar_0];
			NSString* _keyVar_0;
			_keyVar_0 = _tKeyVar_0;
			ThriftFlattenedEsObjectRO* _valVar_0;
			_valVar_0 = [[EsThriftUtil flattenEsObjectRO:_tValVar_0] toThrift];
			[_userVariables setObject:_valVar_0 forKey:_keyVar_0];
		}
		_t.userVariables = _userVariables;
	}
	if (buddyListEntries_set_ && buddyListEntries_ != nil) {
		NSMutableDictionary* _buddyListEntries;
		_buddyListEntries = [[NSMutableDictionary alloc] init];
		for (NSString* _tKeyVar_0 in self.buddyListEntries) {
			EsObject* _tValVar_0 = [self.buddyListEntries objectForKey:_tKeyVar_0];
			NSString* _keyVar_0;
			_keyVar_0 = _tKeyVar_0;
			ThriftFlattenedEsObjectRO* _valVar_0;
			_valVar_0 = [[EsThriftUtil flattenEsObjectRO:_tValVar_0] toThrift];
			[_buddyListEntries setObject:_valVar_0 forKey:_keyVar_0];
		}
		_t.buddyListEntries = _buddyListEntries;
	}
	return _t;
}

- (id) newThrift {
	return [[ThriftLoginResponse alloc] init];
}

- (void) setSuccessful: (BOOL) successful {
	successful_ = successful;
	successful_set_ = true;
}

- (void) setError: (int) error {
	error_ = error;
	error_set_ = true;
}

- (void) setEsObject: (EsObject*) esObject {
	esObject_ = esObject;
	esObject_set_ = true;
}

- (void) setUserName: (NSString*) userName {
	userName_ = userName;
	userName_set_ = true;
}

- (void) setUserVariables: (NSMutableDictionary*) userVariables {
	userVariables_ = userVariables;
	userVariables_set_ = true;
}

- (void) setBuddyListEntries: (NSMutableDictionary*) buddyListEntries {
	buddyListEntries_ = buddyListEntries;
	buddyListEntries_set_ = true;
}

- (void) dealloc {
	self.esObject = nil;
	self.userVariables = nil;
	self.buddyListEntries = nil;
}

@end
