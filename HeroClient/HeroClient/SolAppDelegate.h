//
//  SolAppDelegate.h
//  HeroClient
//
//  Created by Li Dongyu on 8/15/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "ClientViewController.h"

@interface SolAppDelegate : NSObject <NSApplicationDelegate>

@property (assign) IBOutlet NSWindow *window;
@property (nonatomic, strong) IBOutlet ClientViewController* clientViewController;

@end
