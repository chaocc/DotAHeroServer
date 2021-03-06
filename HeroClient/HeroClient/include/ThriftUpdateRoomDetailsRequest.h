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


@interface ThriftUpdateRoomDetailsRequest : NSObject <NSCoding> {
  int32_t __zoneId;
  int32_t __roomId;
  BOOL __capacityUpdate;
  int32_t __capacity;
  BOOL __roomDescriptionUpdate;
  NSString * __roomDescription;
  BOOL __roomNameUpdate;
  NSString * __roomName;
  BOOL __passwordUpdate;
  NSString * __password;
  BOOL __hiddenUpdate;
  BOOL __hidden;

  BOOL __zoneId_isset;
  BOOL __roomId_isset;
  BOOL __capacityUpdate_isset;
  BOOL __capacity_isset;
  BOOL __roomDescriptionUpdate_isset;
  BOOL __roomDescription_isset;
  BOOL __roomNameUpdate_isset;
  BOOL __roomName_isset;
  BOOL __passwordUpdate_isset;
  BOOL __password_isset;
  BOOL __hiddenUpdate_isset;
  BOOL __hidden_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=zoneId, setter=setZoneId:) int32_t zoneId;
@property (nonatomic, getter=roomId, setter=setRoomId:) int32_t roomId;
@property (nonatomic, getter=capacityUpdate, setter=setCapacityUpdate:) BOOL capacityUpdate;
@property (nonatomic, getter=capacity, setter=setCapacity:) int32_t capacity;
@property (nonatomic, getter=roomDescriptionUpdate, setter=setRoomDescriptionUpdate:) BOOL roomDescriptionUpdate;
@property (nonatomic, strong, getter=roomDescription, setter=setRoomDescription:) NSString * roomDescription;
@property (nonatomic, getter=roomNameUpdate, setter=setRoomNameUpdate:) BOOL roomNameUpdate;
@property (nonatomic, strong, getter=roomName, setter=setRoomName:) NSString * roomName;
@property (nonatomic, getter=passwordUpdate, setter=setPasswordUpdate:) BOOL passwordUpdate;
@property (nonatomic, strong, getter=password, setter=setPassword:) NSString * password;
@property (nonatomic, getter=hiddenUpdate, setter=setHiddenUpdate:) BOOL hiddenUpdate;
@property (nonatomic, getter=hidden, setter=setHidden:) BOOL hidden;
#endif

- (id) initWithZoneId: (int32_t) zoneId roomId: (int32_t) roomId capacityUpdate: (BOOL) capacityUpdate capacity: (int32_t) capacity roomDescriptionUpdate: (BOOL) roomDescriptionUpdate roomDescription: (NSString *) roomDescription roomNameUpdate: (BOOL) roomNameUpdate roomName: (NSString *) roomName passwordUpdate: (BOOL) passwordUpdate password: (NSString *) password hiddenUpdate: (BOOL) hiddenUpdate hidden: (BOOL) hidden;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (int32_t) zoneId;
- (void) setZoneId: (int32_t) zoneId;
- (BOOL) zoneIdIsSet;

- (int32_t) roomId;
- (void) setRoomId: (int32_t) roomId;
- (BOOL) roomIdIsSet;

- (BOOL) capacityUpdate;
- (void) setCapacityUpdate: (BOOL) capacityUpdate;
- (BOOL) capacityUpdateIsSet;

- (int32_t) capacity;
- (void) setCapacity: (int32_t) capacity;
- (BOOL) capacityIsSet;

- (BOOL) roomDescriptionUpdate;
- (void) setRoomDescriptionUpdate: (BOOL) roomDescriptionUpdate;
- (BOOL) roomDescriptionUpdateIsSet;

- (NSString *) roomDescription;
- (void) setRoomDescription: (NSString *) roomDescription;
- (BOOL) roomDescriptionIsSet;

- (BOOL) roomNameUpdate;
- (void) setRoomNameUpdate: (BOOL) roomNameUpdate;
- (BOOL) roomNameUpdateIsSet;

- (NSString *) roomName;
- (void) setRoomName: (NSString *) roomName;
- (BOOL) roomNameIsSet;

- (BOOL) passwordUpdate;
- (void) setPasswordUpdate: (BOOL) passwordUpdate;
- (BOOL) passwordUpdateIsSet;

- (NSString *) password;
- (void) setPassword: (NSString *) password;
- (BOOL) passwordIsSet;

- (BOOL) hiddenUpdate;
- (void) setHiddenUpdate: (BOOL) hiddenUpdate;
- (BOOL) hiddenUpdateIsSet;

- (BOOL) hidden;
- (void) setHidden: (BOOL) hidden;
- (BOOL) hiddenIsSet;

@end

@interface ThriftUpdateRoomDetailsRequestConstants : NSObject {
}
@end
