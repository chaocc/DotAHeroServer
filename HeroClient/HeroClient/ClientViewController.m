//
//  ClientViewController.m
//  HeroClient
//
//  Created by Li Dongyu on 8/15/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import "ClientViewController.h"
#import "EsController.h"

@interface ClientViewController ()

@property (weak) IBOutlet NSTableView *handCardTable;
@property (weak) IBOutlet NSTableView *playersTable;
@property (weak) IBOutlet NSTableView *choosingTable;
@property (weak) IBOutlet NSTableView *selfInfoTable;

@property (weak) IBOutlet NSTextField *countDown;
@property (weak) IBOutlet NSTextField *happeningMessage;


@property (weak) IBOutlet NSButton *confirmButton;
@property (weak) IBOutlet NSButton *cancelButton;
@property (weak) IBOutlet NSButton *dropButton;

@end

@implementation ClientViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _esControl = [[EsController alloc] initWithClientViewController:self];
    }
    
    return self;
}




#pragma mark - table delegates


-(NSView*)tableView:(NSTableView*)tableView viewForTableColumn:(NSTableColumn *)tableColumn row:(NSInteger)row{
    
    
    NSTableCellView* cellView = [tableView makeViewWithIdentifier:tableColumn.identifier owner:self];
    if([tableColumn.identifier isEqualToString:@"BugColumn"]){
//        cellView.imageView.image = doc.thumbnail;
//        cellView.textField.stringValue = doc.scaryButData.title;
    }
    return cellView;
}

-(void)tableViewSelectionDidChange:(NSNotification*)notification{
//    NSControl *obj = [notification object]; //get the object sent the notification
    
}

-(NSInteger)numberOfRowsInTableView:(NSTableView*)tableView{
    
//    return [self.bugs count];
    return 0;
}
-(NSIndexSet*)selectedRowIndexes{
    return 0;
}







#pragma mark - button actions
- (IBAction)confirmClicked:(id)sender {
    
}

- (IBAction)cancelClicked:(id)sender {
    
}

- (IBAction)dropClicked:(id)sender {
    
}

- (IBAction)reconnectClicked:(id)sender {
    NSLog(@"reconnecting...");
    _esControl = [[EsController alloc] initWithClientViewController:self];
}




@end
