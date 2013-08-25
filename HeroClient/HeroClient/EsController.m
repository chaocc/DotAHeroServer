#import "EsController.h"
#import "EsObject.h"
#import "EsLoginResponse.h"
#import "EsConnectionResponse.h"
#import "EsRoom.h"
#import "ElectroServer.h"
#import "ClientViewController.h"

#define GamePluginName @"GamePlugin"
#define RoomPluginName @"RoomPlugin"
#define Action @"action"

@implementation EsController


- (id)initWithClientViewController:(ClientViewController*)clientViewController{
    if (self = [super init]) {
        _es = [[ElectroServer alloc] init];
        [_es.engine addEventListenerWithTarget:self action:@selector(onConnectionResponse:) eventIdentifier:EsMessageType_ConnectionResponse];
        [_es.engine addEventListenerWithTarget:self action:@selector(onLoginResponse:) eventIdentifier:EsMessageType_LoginResponse];
        [_es.engine addEventListenerWithTarget:self action:@selector(onPluginMessageEvent:) eventIdentifier:EsMessageType_PluginMessageEvent];
        [_es.engine addEventListenerWithTarget:self action:@selector(onJoinRoomEvent:) eventIdentifier:EsMessageType_JoinRoomEvent];
        NSString* filePath = [[NSBundle mainBundle] pathForResource:@"settings"
                                                             ofType:@"xml"];
        NSLog(@"====>>%@", filePath);
        [_es loadAndConnect:filePath];
        _clientViewController = clientViewController;
    }
    return self;
}

- (void)onConnectionResponse:(EsConnectionResponse *)e{
    NSAssert(e.successful, @"Connnection Failed in %@", NSStringFromSelector(_cmd));
    if (e.successful) {
        EsLoginRequest *lr = [[EsLoginRequest alloc] init];
        lr.userName = @"Sol";
        [_es.engine sendMessage:lr];
    }
    [_clientViewController reloadData];
}


- (void)onLoginResponse:(EsLoginResponse *)e{
    NSAssert(e.successful, @"Login Failed in %@", NSStringFromSelector(_cmd));
    if (e.successful) {
        [self joinRoom];
    }
}


- (void)joinRoom{
    EsCreateRoomRequest *crr = [[EsCreateRoomRequest alloc] init];
    crr.roomName = @"TestRoom";
    crr.zoneName = @"TestZone";
    
    EsPluginListEntry *pleRoom = [[EsPluginListEntry alloc] init];
    pleRoom.extensionName = @"HeroServer";
    pleRoom.pluginHandle = RoomPluginName;
    pleRoom.pluginName = RoomPluginName;
    
    EsPluginListEntry *pleGame = [[EsPluginListEntry alloc] init];
    pleGame.extensionName = @"HeroServer";
    pleGame.pluginHandle = GamePluginName;
    pleGame.pluginName = GamePluginName;
    
    crr.plugins = [NSMutableArray arrayWithObjects:pleRoom, pleGame, nil];
    
    [_es.engine sendMessage:crr];
}

- (void)onJoinRoomEvent:(EsJoinRoomEvent *)e{
    _room = [[_es.managerHelper.zoneManager zoneById:e.zoneId] roomById:e.roomId];
    [self sendStartGameRequest];
    
}


- (void)sendStartGameRequest{
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:2 forKey:Action];
    [self sendPluginRequestWithObject:obj];
    
}


- (void)sendPluginRequestWithObject:(EsObject *)obj{
    EsPluginRequest *pr = [[EsPluginRequest alloc] init];
    pr.pluginName = GamePluginName;
    pr.roomId = _room.roomId;
    pr.zoneId = _room.zoneId;
    pr.parameters = obj;
    [_es.engine sendMessage:pr];
}


- (void)onPluginMessageEvent:(EsPluginMessageEvent *)e{
    EsObject *obj = e.parameters;
//    NSString* action = [obj stringWithKey:Action];
    int action = [obj intWithKey:Action];
    NSLog(@"Receive plugin message with action(%i)", action);
    
    if (action == 3) {
        NSArray* choosingCandidates = [obj intArrayWithKey:@"hero_candidates"];
        [_clientViewController setChoosingCandidates:choosingCandidates];
    }
}


@end
