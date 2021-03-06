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
#import "ThriftErrorType.h"

@interface ThriftServerKickUserEvent : NSObject <NSCoding> {
  int __error;
  ThriftFlattenedEsObjectRO * __esObject;

  BOOL __error_isset;
  BOOL __esObject_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=error, setter=setError:) int error;
@property (nonatomic, strong, getter=esObject, setter=setEsObject:) ThriftFlattenedEsObjectRO * esObject;
#endif

- (id) initWithError: (int) error esObject: (ThriftFlattenedEsObjectRO *) esObject;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (int) error;
- (void) setError: (int) error;
- (BOOL) errorIsSet;

- (ThriftFlattenedEsObjectRO *) esObject;
- (void) setEsObject: (ThriftFlattenedEsObjectRO *) esObject;
- (BOOL) esObjectIsSet;

@end

@interface ThriftServerKickUserEventConstants : NSObject {
}
@end
