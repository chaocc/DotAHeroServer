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

#import "ThriftFlattenedEsObjectRO.h"

#import "ThriftAggregatePluginMessageEvent.h"


@implementation ThriftAggregatePluginMessageEventConstants
+ (void) initialize {
}
@end

@implementation ThriftAggregatePluginMessageEvent

- (id) initWithPluginName: (NSString *) pluginName esObjects: (NSArray *) esObjects originZoneId: (int32_t) originZoneId originRoomId: (int32_t) originRoomId
{
  self = [super init];
  __pluginName = pluginName;
  __pluginName_isset = YES;
  __esObjects = esObjects;
  __esObjects_isset = YES;
  __originZoneId = originZoneId;
  __originZoneId_isset = YES;
  __originRoomId = originRoomId;
  __originRoomId_isset = YES;
  return self;
}

- (id) initWithCoder: (NSCoder *) decoder
{
  self = [super init];
  if ([decoder containsValueForKey: @"pluginName"])
  {
    __pluginName = [decoder decodeObjectForKey: @"pluginName"];
    __pluginName_isset = YES;
  }
  if ([decoder containsValueForKey: @"esObjects"])
  {
    __esObjects = [decoder decodeObjectForKey: @"esObjects"];
    __esObjects_isset = YES;
  }
  if ([decoder containsValueForKey: @"originZoneId"])
  {
    __originZoneId = [decoder decodeInt32ForKey: @"originZoneId"];
    __originZoneId_isset = YES;
  }
  if ([decoder containsValueForKey: @"originRoomId"])
  {
    __originRoomId = [decoder decodeInt32ForKey: @"originRoomId"];
    __originRoomId_isset = YES;
  }
  return self;
}

- (void) encodeWithCoder: (NSCoder *) encoder
{
  if (__pluginName_isset)
  {
    [encoder encodeObject: __pluginName forKey: @"pluginName"];
  }
  if (__esObjects_isset)
  {
    [encoder encodeObject: __esObjects forKey: @"esObjects"];
  }
  if (__originZoneId_isset)
  {
    [encoder encodeInt32: __originZoneId forKey: @"originZoneId"];
  }
  if (__originRoomId_isset)
  {
    [encoder encodeInt32: __originRoomId forKey: @"originRoomId"];
  }
}


- (NSString *) pluginName {
  return __pluginName;
}

- (void) setPluginName: (NSString *) pluginName {
  __pluginName = pluginName;
  __pluginName_isset = YES;
}

- (BOOL) pluginNameIsSet {
  return __pluginName_isset;
}

- (void) unsetPluginName {
  __pluginName = nil;
  __pluginName_isset = NO;
}

- (NSArray *) esObjects {
  return __esObjects;
}

- (void) setEsObjects: (NSArray *) esObjects {
  __esObjects = esObjects;
  __esObjects_isset = YES;
}

- (BOOL) esObjectsIsSet {
  return __esObjects_isset;
}

- (void) unsetEsObjects {
  __esObjects = nil;
  __esObjects_isset = NO;
}

- (int32_t) originZoneId {
  return __originZoneId;
}

- (void) setOriginZoneId: (int32_t) originZoneId {
  __originZoneId = originZoneId;
  __originZoneId_isset = YES;
}

- (BOOL) originZoneIdIsSet {
  return __originZoneId_isset;
}

- (void) unsetOriginZoneId {
  __originZoneId_isset = NO;
}

- (int32_t) originRoomId {
  return __originRoomId;
}

- (void) setOriginRoomId: (int32_t) originRoomId {
  __originRoomId = originRoomId;
  __originRoomId_isset = YES;
}

- (BOOL) originRoomIdIsSet {
  return __originRoomId_isset;
}

- (void) unsetOriginRoomId {
  __originRoomId_isset = NO;
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
        if (fieldType == TType_STRING) {
          NSString * fieldValue = [inProtocol readString];
          [self setPluginName: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 2:
        if (fieldType == TType_LIST) {
          int _size0;
          [inProtocol readListBeginReturningElementType: NULL size: &_size0];
          NSMutableArray * fieldValue = [[NSMutableArray alloc] initWithCapacity: _size0];
          int _i1;
          for (_i1 = 0; _i1 < _size0; ++_i1)
          {
            ThriftFlattenedEsObjectRO *_elem2 = [[ThriftFlattenedEsObjectRO alloc] init];
            [_elem2 read: inProtocol];
            [fieldValue addObject: _elem2];
          }
          [inProtocol readListEnd];
          [self setEsObjects: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 3:
        if (fieldType == TType_I32) {
          int32_t fieldValue = [inProtocol readI32];
          [self setOriginZoneId: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 4:
        if (fieldType == TType_I32) {
          int32_t fieldValue = [inProtocol readI32];
          [self setOriginRoomId: fieldValue];
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
  [outProtocol writeStructBeginWithName: @"ThriftAggregatePluginMessageEvent"];
  if (__pluginName_isset) {
    if (__pluginName != nil) {
      [outProtocol writeFieldBeginWithName: @"pluginName" type: TType_STRING fieldID: 1];
      [outProtocol writeString: __pluginName];
      [outProtocol writeFieldEnd];
    }
  }
  if (__esObjects_isset) {
    if (__esObjects != nil) {
      [outProtocol writeFieldBeginWithName: @"esObjects" type: TType_LIST fieldID: 2];
      {
        [outProtocol writeListBeginWithElementType: TType_STRUCT size: [__esObjects count]];
        for (NSUInteger i4 = 0; i4 < [__esObjects count]; i4++)
        {
          [[__esObjects objectAtIndex: i4] write: outProtocol];
        }
        [outProtocol writeListEnd];
      }
      [outProtocol writeFieldEnd];
    }
  }
  if (__originZoneId_isset) {
    [outProtocol writeFieldBeginWithName: @"originZoneId" type: TType_I32 fieldID: 3];
    [outProtocol writeI32: __originZoneId];
    [outProtocol writeFieldEnd];
  }
  if (__originRoomId_isset) {
    [outProtocol writeFieldBeginWithName: @"originRoomId" type: TType_I32 fieldID: 4];
    [outProtocol writeI32: __originRoomId];
    [outProtocol writeFieldEnd];
  }
  [outProtocol writeFieldStop];
  [outProtocol writeStructEnd];
}

- (NSString *) description {
  NSMutableString * ms = [NSMutableString stringWithString: @"ThriftAggregatePluginMessageEvent("];
  [ms appendString: @"pluginName:"];
  [ms appendFormat: @"\"%@\"", __pluginName];
  [ms appendString: @",esObjects:"];
  [ms appendFormat: @"%@", __esObjects];
  [ms appendString: @",originZoneId:"];
  [ms appendFormat: @"%i", __originZoneId];
  [ms appendString: @",originRoomId:"];
  [ms appendFormat: @"%i", __originRoomId];
  [ms appendString: @")"];
  return [NSString stringWithString: ms];
}

@end

