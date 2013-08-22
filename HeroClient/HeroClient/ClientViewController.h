//
//  ClientViewController.h
//  HeroClient
//
//  Created by Li Dongyu on 8/15/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "Player.h"
#import "ElectroServer.h"
#import "EsRoom.h"
@class EsController;

@interface ClientViewController : NSViewController

@property (strong) NSMutableArray* players;
@property (strong) Player* player;
@property (strong) NSMutableArray* handCards;
@property (strong) NSMutableArray* happeningOrChoosing;
@property (assign) NSInteger timer;
@property (strong) NSString* happeningMessageText;

@property (strong) EsController* esControl;



-(void)setChoosingCandidates:(NSArray*)choosingCandidates;
-(void)reloadData;

@end
