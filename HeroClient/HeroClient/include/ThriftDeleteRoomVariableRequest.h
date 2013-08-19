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


@interface ThriftDeleteRoomVariableRequest : NSObject <NSCoding> {
  int32_t __zoneId;
  int32_t __roomId;
  NSString * __name;

  BOOL __zoneId_isset;
  BOOL __roomId_isset;
  BOOL __name_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=zoneId, setter=setZoneId:) int32_t zoneId;
@property (nonatomic, getter=roomId, setter=setRoomId:) int32_t roomId;
@property (nonatomic, strong, getter=name, setter=setName:) NSString * name;
#endif

- (id) initWithZoneId: (int32_t) zoneId roomId: (int32_t) roomId name: (NSString *) name;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (int32_t) zoneId;
- (void) setZoneId: (int32_t) zoneId;
- (BOOL) zoneIdIsSet;

- (int32_t) roomId;
- (void) setRoomId: (int32_t) roomId;
- (BOOL) roomIdIsSet;

- (NSString *) name;
- (void) setName: (NSString *) name;
- (BOOL) nameIsSet;

@end

@interface ThriftDeleteRoomVariableRequestConstants : NSObject {
}
@end
