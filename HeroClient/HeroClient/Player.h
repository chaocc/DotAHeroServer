//
//  Player.h
//  HeroClient
//
//  Created by Li Dongyu on 8/16/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Player : NSObject
@property (assign) NSInteger hp;
@property (assign) NSInteger sp;
@property (strong) NSString* weapon;
@property (strong) NSString* armor;
@end
