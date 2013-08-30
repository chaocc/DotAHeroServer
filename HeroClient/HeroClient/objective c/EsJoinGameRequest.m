//
//  Autogenerated by CocoaTouchApiGenerator
//
//  DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//



#import "EsJoinGameRequest.h"
#import "EsThriftUtil.h"

@implementation EsJoinGameRequest

@synthesize gameId = gameId_;
@synthesize password = password_;

- (id) initWithThriftObject: (id) thriftObject {
	if ((self = [super init])) {
		self.messageType = EsMessageType_JoinGameRequest;
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
	ThriftJoinGameRequest* t = (ThriftJoinGameRequest*) thriftObject;
	if ([t gameIdIsSet]) {
		self.gameId = t.gameId;
	}
	if ([t passwordIsSet] && t.password != nil) {
		self.password = t.password;
	}
}

- (ThriftJoinGameRequest*) toThrift {
	ThriftJoinGameRequest* _t = [[ThriftJoinGameRequest alloc] init];;
	if (gameId_set_) {
		int32_t _gameId;
		_gameId = self.gameId;
		_t.gameId = _gameId;
	}
	if (password_set_ && password_ != nil) {
		NSString* _password;
		_password = self.password;
		_t.password = _password;
	}
	return _t;
}

- (id) newThrift {
	return [[ThriftJoinGameRequest alloc] init];
}

- (void) setGameId: (int32_t) gameId {
	gameId_ = gameId;
	gameId_set_ = true;
}

- (void) setPassword: (NSString*) password {
	password_ = password;
	password_set_ = true;
}


@end