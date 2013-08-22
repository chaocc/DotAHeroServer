//
//  Card.h
//  HeroClient
//
//  Created by Li Dongyu on 8/16/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Card : NSObject
@property (assign) NSInteger suits;
@property (assign) NSInteger point;
@property (strong) NSString* name;
@end
