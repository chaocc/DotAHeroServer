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


#import "ThriftAddBuddiesResponse.h"


@implementation ThriftAddBuddiesResponseConstants
+ (void) initialize {
}
@end

@implementation ThriftAddBuddiesResponse

- (id) initWithBuddiesAdded: (NSArray *) buddiesAdded buddiesNotAdded: (NSArray *) buddiesNotAdded
{
  self = [super init];
  __buddiesAdded = buddiesAdded;
  __buddiesAdded_isset = YES;
  __buddiesNotAdded = buddiesNotAdded;
  __buddiesNotAdded_isset = YES;
  return self;
}

- (id) initWithCoder: (NSCoder *) decoder
{
  self = [super init];
  if ([decoder containsValueForKey: @"buddiesAdded"])
  {
    __buddiesAdded = [decoder decodeObjectForKey: @"buddiesAdded"];
    __buddiesAdded_isset = YES;
  }
  if ([decoder containsValueForKey: @"buddiesNotAdded"])
  {
    __buddiesNotAdded = [decoder decodeObjectForKey: @"buddiesNotAdded"];
    __buddiesNotAdded_isset = YES;
  }
  return self;
}

- (void) encodeWithCoder: (NSCoder *) encoder
{
  if (__buddiesAdded_isset)
  {
    [encoder encodeObject: __buddiesAdded forKey: @"buddiesAdded"];
  }
  if (__buddiesNotAdded_isset)
  {
    [encoder encodeObject: __buddiesNotAdded forKey: @"buddiesNotAdded"];
  }
}


- (NSArray *) buddiesAdded {
  return __buddiesAdded;
}

- (void) setBuddiesAdded: (NSArray *) buddiesAdded {
  __buddiesAdded = buddiesAdded;
  __buddiesAdded_isset = YES;
}

- (BOOL) buddiesAddedIsSet {
  return __buddiesAdded_isset;
}

- (void) unsetBuddiesAdded {
  __buddiesAdded = nil;
  __buddiesAdded_isset = NO;
}

- (NSArray *) buddiesNotAdded {
  return __buddiesNotAdded;
}

- (void) setBuddiesNotAdded: (NSArray *) buddiesNotAdded {
  __buddiesNotAdded = buddiesNotAdded;
  __buddiesNotAdded_isset = YES;
}

- (BOOL) buddiesNotAddedIsSet {
  return __buddiesNotAdded_isset;
}

- (void) unsetBuddiesNotAdded {
  __buddiesNotAdded = nil;
  __buddiesNotAdded_isset = NO;
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
        if (fieldType == TType_LIST) {
          int _size0;
          [inProtocol readListBeginReturningElementType: NULL size: &_size0];
          NSMutableArray * fieldValue = [[NSMutableArray alloc] initWithCapacity: _size0];
          int _i1;
          for (_i1 = 0; _i1 < _size0; ++_i1)
          {
            NSString * _elem2 = [inProtocol readString];
            [fieldValue addObject: _elem2];
          }
          [inProtocol readListEnd];
          [self setBuddiesAdded: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 2:
        if (fieldType == TType_LIST) {
          int _size3;
          [inProtocol readListBeginReturningElementType: NULL size: &_size3];
          NSMutableArray * fieldValue = [[NSMutableArray alloc] initWithCapacity: _size3];
          int _i4;
          for (_i4 = 0; _i4 < _size3; ++_i4)
          {
            NSString * _elem5 = [inProtocol readString];
            [fieldValue addObject: _elem5];
          }
          [inProtocol readListEnd];
          [self setBuddiesNotAdded: fieldValue];
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
  [outProtocol writeStructBeginWithName: @"ThriftAddBuddiesResponse"];
  if (__buddiesAdded_isset) {
    if (__buddiesAdded != nil) {
      [outProtocol writeFieldBeginWithName: @"buddiesAdded" type: TType_LIST fieldID: 1];
      {
        [outProtocol writeListBeginWithElementType: TType_STRING size: [__buddiesAdded count]];
        for (NSUInteger i7 = 0; i7 < [__buddiesAdded count]; i7++)
        {
          [outProtocol writeString: [__buddiesAdded objectAtIndex: i7]];
        }
        [outProtocol writeListEnd];
      }
      [outProtocol writeFieldEnd];
    }
  }
  if (__buddiesNotAdded_isset) {
    if (__buddiesNotAdded != nil) {
      [outProtocol writeFieldBeginWithName: @"buddiesNotAdded" type: TType_LIST fieldID: 2];
      {
        [outProtocol writeListBeginWithElementType: TType_STRING size: [__buddiesNotAdded count]];
        for (NSUInteger i9 = 0; i9 < [__buddiesNotAdded count]; i9++)
        {
          [outProtocol writeString: [__buddiesNotAdded objectAtIndex: i9]];
        }
        [outProtocol writeListEnd];
      }
      [outProtocol writeFieldEnd];
    }
  }
  [outProtocol writeFieldStop];
  [outProtocol writeStructEnd];
}

- (NSString *) description {
  NSMutableString * ms = [NSMutableString stringWithString: @"ThriftAddBuddiesResponse("];
  [ms appendString: @"buddiesAdded:"];
  [ms appendFormat: @"%@", __buddiesAdded];
  [ms appendString: @",buddiesNotAdded:"];
  [ms appendFormat: @"%@", __buddiesNotAdded];
  [ms appendString: @")"];
  return [NSString stringWithString: ms];
}

@end

