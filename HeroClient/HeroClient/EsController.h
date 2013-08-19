//
//  EsController.h
//  HeroClient
//
//  Created by Li Dongyu on 8/16/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import <Foundation/Foundation.h>
@class ClientViewController;
@class ElectroServer;
@class EsRoom;

@interface EsController : NSObject

@property (strong) ElectroServer *es;
@property (strong) EsRoom *room;
@property (strong) ClientViewController* clientViewController;


- (id)initWithClientViewController:(ClientViewController*)clientViewController;
@end
