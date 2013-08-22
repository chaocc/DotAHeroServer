//
//  Player.h
//  HeroClient
//
//  Created by Li Dongyu on 8/16/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Player : NSObject
@property (strong) NSString* name;
@property (assign) NSInteger hero;
@property (assign) NSInteger hp;
@property (assign) NSInteger sp;
@property (assign) NSInteger weapon;
@property (assign) NSInteger armor;
@end
