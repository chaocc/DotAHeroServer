//
//  Autogenerated by CocoaTouchApiGenerator
//
//  DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//



#import "EsMessage.h"
#import "EsMessageType.h"
#import "EsRequest.h"
#import "EsResponse.h"
#import "EsEvent.h"
#import "EsEntity.h"
#import "EsObject.h"
#import "ThriftRemoveRoomOperatorRequest.h"

/**
 * This request can be made by a current room operator to remove operator status from another user in the room. By default the first user in a room is a room operator. The operator has the power to kick users from the room and to give operator status to other users, and to revoke operator status 
 from other users. You know when your operator status changes because the 'action' property on the RoomUserUpdateEvent is OperatorGranted or OperatorRevoked.
 * 
 * This example shows how one operator can remove operator status from another user.
 <pre>
private var _es:ElectroServer;
private var _room:Room;

private function initialize():void {
        _es.engine.addEventListener(MessageType.RoomUserUpdateEvent.name, onRoomUserUpdateEvent);

        var rror:RemoveRoomOperatorRequest = new RemoveRoomOperatorRequest();
        rror.zoneId = _room.zoneId;
        rror.roomId = _room.id;
        rror.userName = "mike";

        _es.engine.send(rror);
}

private function onRoomUserUpdateEvent(e:RoomUserUpdateEvent):void {
        switch (e.action) {
                case RoomUserUpdateAction.OperatorGranted:
                        trace(e.userName + " was granted operator status.");
                        break;
                case RoomUserUpdateAction.OperatorRevoked:
                        trace(e.userName + " had operator status revoked.");
                        break;
        }
}
 </pre>
 */
@interface EsRemoveRoomOperatorRequest : EsRequest {
@private
	BOOL zoneId_set_;
	int32_t zoneId_;
	BOOL roomId_set_;
	int32_t roomId_;
	BOOL userName_set_;
	NSString* userName_;
}

/**
 * Id of the zone that contains the room that contains the user in question.
 */
@property(nonatomic) int32_t zoneId;
/**
 * Id of the room that contains the user in question.
 */
@property(nonatomic) int32_t roomId;
@property(strong,nonatomic) NSString* userName;

- (id) init;
- (id) initWithThriftObject: (id) thriftObject;
- (void) fromThrift: (id) thriftObject;
- (ThriftRemoveRoomOperatorRequest*) toThrift;
- (id) newThrift;
@end
