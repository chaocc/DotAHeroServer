//
//  ClientViewController.m
//  HeroClient
//
//  Created by Li Dongyu on 8/15/13.
//  Copyright (c) 2013 Maid Con. All rights reserved.
//

#import "ClientViewController.h"
#import "EsController.h"
#import "Card.h"

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

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _esControl = [[EsController alloc] initWithClientViewController:self];
    }
    return self;
}
#pragma mark - table delegates

//@property (strong) NSMutableArray* players;
//@property (strong) Player* player;
//@property (strong) NSMutableArray* handCards;
//@property (strong) NSMutableArray* happeningOrChoosing;
//@property (assign) NSInteger timer;
//@property (strong) NSString* happeningMessageText;

//@property (weak) IBOutlet NSTableView *handCardTable;
//@property (weak) IBOutlet NSTableView *playersTable;
//@property (weak) IBOutlet NSTableView *choosingTable;
//@property (weak) IBOutlet NSTableView *selfInfoTable;
//@property (weak) IBOutlet NSTextField *countDown;
//@property (weak) IBOutlet NSTextField *happeningMessage;
-(NSView*)tableView:(NSTableView*)tableView viewForTableColumn:(NSTableColumn *)tableColumn row:(NSInteger)row{
    
    NSTableCellView* cellView = [tableView makeViewWithIdentifier:tableColumn.identifier owner:self];
    
    if ([tableView isEqualTo:_selfInfoTable]) {//for player player's personal info
        if([tableColumn.identifier isEqualToString:@"self_property"]){
            if (row ==0) {
                cellView.textField.stringValue = @"hero";
            }else if (row ==1) {
                cellView.textField.stringValue = @"hp";
            }else if(row ==2){
                cellView.textField.stringValue = @"sp";
            }else if(row ==3){
                cellView.textField.stringValue = @"weapon";
            }else if(row ==4){
                cellView.textField.stringValue = @"armor";
            }
        }else if([tableColumn.identifier isEqualTo:@"self_property_value"]){
            
        }
    }
    

    else if([tableView isEqualTo:_handCardTable]) {//for player handcards
        Card* card = [_handCards objectAtIndex:row];
        if([tableColumn.identifier isEqualTo:@"suits_hand"]){
            cellView.textField.stringValue = [NSString stringWithFormat:@"%ld", card.suits];
        }else if([tableColumn.identifier isEqualTo:@"point_hand"]){
            cellView.textField.stringValue = [NSString stringWithFormat:@"%ld", card.point];
        }else if([tableColumn.identifier isEqualTo:@"name_hand"]){
            cellView.textField.identifier = card.name;
        }
    }
    
    else if([tableView isEqualTo:_playersTable]){//for player list
        Player* player = [_players objectAtIndex:row];
        if([tableColumn.identifier isEqualTo:@"name_otherp"]){
            cellView.textField.stringValue = player.name;
        }else if([tableColumn.identifier isEqualTo:@"hp_otherp"]){
            cellView.textField.stringValue = [NSString stringWithFormat:@"%ld",player.hp];
        }else if([tableColumn.identifier isEqualTo:@"sp_otherp"]){
            cellView.textField.stringValue = [NSString stringWithFormat:@"%ld",player.sp];
        }else if([tableColumn.identifier isEqualTo:@"weapon_otherp"]){
            NSInteger weapon = player.weapon;
            cellView.textField.stringValue = [NSString stringWithFormat:@"%ld",player.weapon];
        }else if([tableColumn.identifier isEqualTo:@"armor_otherp"]){
            NSInteger weapon = player.armor;
            cellView.textField.stringValue = [NSString stringWithFormat:@"%ld",player.armor];
        }
    }
    
    else if([tableView isEqualTo:_choosingTable]){//for choosing cards
        Card* card = [_happeningOrChoosing objectAtIndex:row];
        if([tableColumn.identifier isEqualTo:@"suits_choosing"]){
            cellView.textField.stringValue = [NSString stringWithFormat:@"%ld",card.suits];
        }else if([tableColumn.identifier isEqualTo:@"point_choosing"]){
            cellView.textField.stringValue = [NSString stringWithFormat:@"%ld",card.point];
        }else if([tableColumn.identifier isEqualTo:@"name_choosing"]){
            cellView.textField.stringValue = card.name;
        }
    }
    
    return cellView;
}
-(NSInteger)numberOfRowsInTableView:(NSTableView*)tableView{
    
    if ([tableView isEqualTo:_choosingTable]) {
        return [_happeningOrChoosing count];
    }else if([tableView isEqualTo:_playersTable]){
        return [_players count];
    }else if([tableView isEqualTo:_handCardTable]){
        return [_handCards count];
    }else if([tableView isEqualTo:_selfInfoTable]){
        return 5;
    }
    return 0;
}
-(NSIndexSet*)selectedRowIndexes{
    return 0;
}
-(void)tableViewSelectionDidChange:(NSNotification*)notification{
    NSTableView *selectedTable = (NSTableView*)[notification object];
    // although we check which table sent the message
    // we still need check all tables which rows are selected
    if ([selectedTable isEqualTo:_handCardTable]) {
        
    }else if([selectedTable isEqualTo:_playersTable]){
        
    }else if([selectedTable isEqualTo:_choosingTable]){
        
    }else if([selectedTable isEqualTo:_selfInfoTable]){
        
    }
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





#pragma mark - update data and update ui according to server 
-(void)setChoosingCandidates:(NSArray*)choosingCandidates{
    [_happeningOrChoosing setArray:choosingCandidates];
    [_choosingTable reloadData];
}
-(void)reloadData{
    
}

@end
