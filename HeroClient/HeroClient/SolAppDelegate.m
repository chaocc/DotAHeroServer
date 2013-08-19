//
//  SolAppDelegate.m
//  HeroClient
//
//  Created by Li Dongyu on 8/15/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import "SolAppDelegate.h"

@implementation SolAppDelegate

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification
{
        self.clientViewController = [[ClientViewController alloc] initWithNibName:@"ClientViewController" bundle:nil];
    [self.window.contentView addSubview:self.clientViewController.view];
    self.clientViewController.view.frame = ((NSView*)self.window.contentView).bounds;
}

@end
