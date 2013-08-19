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

#import "ThriftFlattenedEsObject.h"

#import "ThriftPluginListEntry.h"


@implementation ThriftPluginListEntryConstants
+ (void) initialize {
}
@end

@implementation ThriftPluginListEntry

- (id) initWithExtensionName: (NSString *) extensionName pluginName: (NSString *) pluginName pluginHandle: (NSString *) pluginHandle pluginId: (int32_t) pluginId parameters: (ThriftFlattenedEsObject *) parameters
{
  self = [super init];
  __extensionName = extensionName;
  __extensionName_isset = YES;
  __pluginName = pluginName;
  __pluginName_isset = YES;
  __pluginHandle = pluginHandle;
  __pluginHandle_isset = YES;
  __pluginId = pluginId;
  __pluginId_isset = YES;
  __parameters = parameters;
  __parameters_isset = YES;
  return self;
}

- (id) initWithCoder: (NSCoder *) decoder
{
  self = [super init];
  if ([decoder containsValueForKey: @"extensionName"])
  {
    __extensionName = [decoder decodeObjectForKey: @"extensionName"];
    __extensionName_isset = YES;
  }
  if ([decoder containsValueForKey: @"pluginName"])
  {
    __pluginName = [decoder decodeObjectForKey: @"pluginName"];
    __pluginName_isset = YES;
  }
  if ([decoder containsValueForKey: @"pluginHandle"])
  {
    __pluginHandle = [decoder decodeObjectForKey: @"pluginHandle"];
    __pluginHandle_isset = YES;
  }
  if ([decoder containsValueForKey: @"pluginId"])
  {
    __pluginId = [decoder decodeInt32ForKey: @"pluginId"];
    __pluginId_isset = YES;
  }
  if ([decoder containsValueForKey: @"parameters"])
  {
    __parameters = [decoder decodeObjectForKey: @"parameters"];
    __parameters_isset = YES;
  }
  return self;
}

- (void) encodeWithCoder: (NSCoder *) encoder
{
  if (__extensionName_isset)
  {
    [encoder encodeObject: __extensionName forKey: @"extensionName"];
  }
  if (__pluginName_isset)
  {
    [encoder encodeObject: __pluginName forKey: @"pluginName"];
  }
  if (__pluginHandle_isset)
  {
    [encoder encodeObject: __pluginHandle forKey: @"pluginHandle"];
  }
  if (__pluginId_isset)
  {
    [encoder encodeInt32: __pluginId forKey: @"pluginId"];
  }
  if (__parameters_isset)
  {
    [encoder encodeObject: __parameters forKey: @"parameters"];
  }
}


- (NSString *) extensionName {
  return __extensionName;
}

- (void) setExtensionName: (NSString *) extensionName {
  __extensionName = extensionName;
  __extensionName_isset = YES;
}

- (BOOL) extensionNameIsSet {
  return __extensionName_isset;
}

- (void) unsetExtensionName {
  __extensionName = nil;
  __extensionName_isset = NO;
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

- (NSString *) pluginHandle {
  return __pluginHandle;
}

- (void) setPluginHandle: (NSString *) pluginHandle {
  __pluginHandle = pluginHandle;
  __pluginHandle_isset = YES;
}

- (BOOL) pluginHandleIsSet {
  return __pluginHandle_isset;
}

- (void) unsetPluginHandle {
  __pluginHandle = nil;
  __pluginHandle_isset = NO;
}

- (int32_t) pluginId {
  return __pluginId;
}

- (void) setPluginId: (int32_t) pluginId {
  __pluginId = pluginId;
  __pluginId_isset = YES;
}

- (BOOL) pluginIdIsSet {
  return __pluginId_isset;
}

- (void) unsetPluginId {
  __pluginId_isset = NO;
}

- (ThriftFlattenedEsObject *) parameters {
  return __parameters;
}

- (void) setParameters: (ThriftFlattenedEsObject *) parameters {
  __parameters = parameters;
  __parameters_isset = YES;
}

- (BOOL) parametersIsSet {
  return __parameters_isset;
}

- (void) unsetParameters {
  __parameters = nil;
  __parameters_isset = NO;
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
          [self setExtensionName: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 2:
        if (fieldType == TType_STRING) {
          NSString * fieldValue = [inProtocol readString];
          [self setPluginName: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 3:
        if (fieldType == TType_STRING) {
          NSString * fieldValue = [inProtocol readString];
          [self setPluginHandle: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 4:
        if (fieldType == TType_I32) {
          int32_t fieldValue = [inProtocol readI32];
          [self setPluginId: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 5:
        if (fieldType == TType_STRUCT) {
          ThriftFlattenedEsObject *fieldValue = [[ThriftFlattenedEsObject alloc] init];
          [fieldValue read: inProtocol];
          [self setParameters: fieldValue];
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
  [outProtocol writeStructBeginWithName: @"ThriftPluginListEntry"];
  if (__extensionName_isset) {
    if (__extensionName != nil) {
      [outProtocol writeFieldBeginWithName: @"extensionName" type: TType_STRING fieldID: 1];
      [outProtocol writeString: __extensionName];
      [outProtocol writeFieldEnd];
    }
  }
  if (__pluginName_isset) {
    if (__pluginName != nil) {
      [outProtocol writeFieldBeginWithName: @"pluginName" type: TType_STRING fieldID: 2];
      [outProtocol writeString: __pluginName];
      [outProtocol writeFieldEnd];
    }
  }
  if (__pluginHandle_isset) {
    if (__pluginHandle != nil) {
      [outProtocol writeFieldBeginWithName: @"pluginHandle" type: TType_STRING fieldID: 3];
      [outProtocol writeString: __pluginHandle];
      [outProtocol writeFieldEnd];
    }
  }
  if (__pluginId_isset) {
    [outProtocol writeFieldBeginWithName: @"pluginId" type: TType_I32 fieldID: 4];
    [outProtocol writeI32: __pluginId];
    [outProtocol writeFieldEnd];
  }
  if (__parameters_isset) {
    if (__parameters != nil) {
      [outProtocol writeFieldBeginWithName: @"parameters" type: TType_STRUCT fieldID: 5];
      [__parameters write: outProtocol];
      [outProtocol writeFieldEnd];
    }
  }
  [outProtocol writeFieldStop];
  [outProtocol writeStructEnd];
}

- (NSString *) description {
  NSMutableString * ms = [NSMutableString stringWithString: @"ThriftPluginListEntry("];
  [ms appendString: @"extensionName:"];
  [ms appendFormat: @"\"%@\"", __extensionName];
  [ms appendString: @",pluginName:"];
  [ms appendFormat: @"\"%@\"", __pluginName];
  [ms appendString: @",pluginHandle:"];
  [ms appendFormat: @"\"%@\"", __pluginHandle];
  [ms appendString: @",pluginId:"];
  [ms appendFormat: @"%i", __pluginId];
  [ms appendString: @",parameters:"];
  [ms appendFormat: @"%@", __parameters];
  [ms appendString: @")"];
  return [NSString stringWithString: ms];
}

@end

