/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */

#import <Foundation/Foundation.h>

#import "TProtocol.h"
#import "TApplicationException.h"
#import "TProtocolUtil.h"
#import "TProcessor.h"


#import "ThriftAddRoomOperatorRequest.h"


@implementation ThriftAddRoomOperatorRequestConstants
+ (void) initialize {
}
@end

@implementation ThriftAddRoomOperatorRequest

- (id) initWithZoneId: (int32_t) zoneId roomId: (int32_t) roomId userName: (NSString *) userName
{
  self = [super init];
  __zoneId = zoneId;
  __zoneId_isset = YES;
  __roomId = roomId;
  __roomId_isset = YES;
  __userName = userName;
  __userName_isset = YES;
  return self;
}

- (id) initWithCoder: (NSCoder *) decoder
{
  self = [super init];
  if ([decoder containsValueForKey: @"zoneId"])
  {
    __zoneId = [decoder decodeInt32ForKey: @"zoneId"];
    __zoneId_isset = YES;
  }
  if ([decoder containsValueForKey: @"roomId"])
  {
    __roomId = [decoder decodeInt32ForKey: @"roomId"];
    __roomId_isset = YES;
  }
  if ([decoder containsValueForKey: @"userName"])
  {
    __userName = [decoder decodeObjectForKey: @"userName"];
    __userName_isset = YES;
  }
  return self;
}

- (void) encodeWithCoder: (NSCoder *) encoder
{
  if (__zoneId_isset)
  {
    [encoder encodeInt32: __zoneId forKey: @"zoneId"];
  }
  if (__roomId_isset)
  {
    [encoder encodeInt32: __roomId forKey: @"roomId"];
  }
  if (__userName_isset)
  {
    [encoder encodeObject: __userName forKey: @"userName"];
  }
}


- (int32_t) zoneId {
  return __zoneId;
}

- (void) setZoneId: (int32_t) zoneId {
  __zoneId = zoneId;
  __zoneId_isset = YES;
}

- (BOOL) zoneIdIsSet {
  return __zoneId_isset;
}

- (void) unsetZoneId {
  __zoneId_isset = NO;
}

- (int32_t) roomId {
  return __roomId;
}

- (void) setRoomId: (int32_t) roomId {
  __roomId = roomId;
  __roomId_isset = YES;
}

- (BOOL) roomIdIsSet {
  return __roomId_isset;
}

- (void) unsetRoomId {
  __roomId_isset = NO;
}

- (NSString *) userName {
  return __userName;
}

- (void) setUserName: (NSString *) userName {
  __userName = userName;
  __userName_isset = YES;
}

- (BOOL) userNameIsSet {
  return __userName_isset;
}

- (void) unsetUserName {
  __userName = nil;
  __userName_isset = NO;
}

- (void) read: (id <TProtocol>) inProtocol
{
  NSString * fieldName;
  int fieldType;
  int fieldID;

  [inProtocol readStructBeginReturningName: NULL];
  while (true)
  {
    [inProtocol readFieldBeginReturningName: &fieldName type: &fieldType fieldID: &fieldID];
    if (fieldType == TType_STOP) { 
      break;
    }
    switch (fieldID)
    {
      case 1:
        if (fieldType == TType_I32) {
          int32_t fieldValue = [inProtocol readI32];
          [self setZoneId: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 2:
        if (fieldType == TType_I32) {
          int32_t fieldValue = [inProtocol readI32];
          [self setRoomId: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 3:
        if (fieldType == TType_STRING) {
          NSString * fieldValue = [inProtocol readString];
          [self setUserName: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      default:
        [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        break;
    }
    [inProtocol readFieldEnd];
  }
  [inProtocol readStructEnd];
}

- (void) write: (id <TProtocol>) outProtocol {
  [outProtocol writeStructBeginWithName: @"ThriftAddRoomOperatorRequest"];
  if (__zoneId_isset) {
    [outProtocol writeFieldBeginWithName: @"zoneId" type: TType_I32 fieldID: 1];
    [outProtocol writeI32: __zoneId];
    [outProtocol writeFieldEnd];
  }
  if (__roomId_isset) {
    [outProtocol writeFieldBeginWithName: @"roomId" type: TType_I32 fieldID: 2];
    [outProtocol writeI32: __roomId];
    [outProtocol writeFieldEnd];
  }
  if (__userName_isset) {
    if (__userName != nil) {
      [outProtocol writeFieldBeginWithName: @"userName" type: TType_STRING fieldID: 3];
      [outProtocol writeString: __userName];
      [outProtocol writeFieldEnd];
    }
  }
  [outProtocol writeFieldStop];
  [outProtocol writeStructEnd];
}

- (NSString *) description {
  NSMutableString * ms = [NSMutableString stringWithString: @"ThriftAddRoomOperatorRequest("];
  [ms appendString: @"zoneId:"];
  [ms appendFormat: @"%i", __zoneId];
  [ms appendString: @",roomId:"];
  [ms appendFormat: @"%i", __roomId];
  [ms appendString: @",userName:"];
  [ms appendFormat: @"\"%@\"", __userName];
  [ms appendString: @")"];
  return [NSString stringWithString: ms];
}

@end
