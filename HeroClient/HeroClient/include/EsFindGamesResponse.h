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
#import "ThriftFindGamesResponse.h"
#import "EsFlattenedEsObject.h"
#import "ThriftFlattenedEsObject.h"
#import "EsServerGame.h"
#import "ThriftServerGame.h"

/**
 * The FindGamesRequest allows a client to request a list of games that are managed by the server. The request leads to this response which contains a list.
 The list is filtered using the 'searCriteria' property.
 * 
 * This example shows how to request a list of poker games, and capture the response.
 
<pre>
private var _es:ElectroServer;

private function initialize():void {
        _es.engine.addEventListener(MessageType.FindGamesResponse.name, onFindGamesResponse);


        var fgr:FindGamesRequest = new FindGamesRequest();
        fgr.searchCriteria = new SearchCriteria();
        fgr.searchCriteria.gameType = "PokerGame";

        _es.engine.send(qjr);
}

private function onFindGamesResponse(e:FindGamesResponse):void {
}
</pre>
 */
@interface EsFindGamesResponse : EsResponse {
@private
	BOOL games_set_;
	NSMutableArray* games_;
}

@property(strong,nonatomic) NSMutableArray* games;

- (id) init;
- (id) initWithThriftObject: (id) thriftObject;
- (void) fromThrift: (id) thriftObject;
- (ThriftFindGamesResponse*) toThrift;
- (id) newThrift;
@end
