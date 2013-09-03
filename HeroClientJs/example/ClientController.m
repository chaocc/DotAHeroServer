/*
 * Send game plugin request with kActionChooseHero and kParamCardIdList.
 */
- (void)sendChooseHeroIdRequest
{    
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:kActionChooseHero forKey:kAction];
    NSArray *heroIds = [NSArray arrayWithObject:@(_player.selectedHeroId)];
    [obj setIntArray:heroIds forKey:kParamCardIdList];
    [self sendGamePluginRequestWithObject:obj];
    
    NSLog(@"Send game plugin request with EsObject: %@", obj);
}

/*
 * Send game plugin request with kActionUseHandCard, kParamCardIdList and kParamTargetPlayerList.
 */
- (void)sendUseHandCardRequestWithIsStrengthened:(BOOL)isStrengthened
{    
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:kActionUseHandCard forKey:kAction];    
    [obj setIntArray:_player.selectedCardIds forKey:kParamCardIdList];
    [obj setBool:isStrengthened forKey:kParamIsStrengthened];
    if (0 != _gameLayer.targetPlayerNames.count) {
        [obj setStringArray:_gameLayer.targetPlayerNames forKey:kParamTargetPlayerList];
    }
    [self sendGamePluginRequestWithObject:obj];
    
    NSLog(@"Send game plugin request with EsObject: %@", obj);
}

/*
 * Send game plugin request with kActionUseHeroSkill, kParamSelectedSkillId, kParamCardIdList and kParamTargetPlayerList.
 */
- (void)sendUseHeroSkillRequest
{    
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:kActionUseHeroSkill forKey:kAction];
    [obj setInt:_player.selectedSkillId forKey:kParamSelectedSkillId];
    if (0 != _player.selectedCardIds.count) {
        [obj setIntArray:_player.selectedCardIds forKey:kParamCardIdList];
    }
    if (0 != _gameLayer.targetPlayerNames.count) {
        [obj setStringArray:_gameLayer.targetPlayerNames forKey:kParamTargetPlayerList];
    }
    [self sendGamePluginRequestWithObject:obj];
    
    NSLog(@"Send game plugin request with EsObject: %@", obj);
}

/*
 * Send game plugin request with kActionCancel.
 */
- (void)sendCancelRequest
{    
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:kActionCancel forKey:kAction];
    [self sendGamePluginRequestWithObject:obj];
    
    NSLog(@"Send game plugin request with EsObject: %@", obj);
}

/*
 * Send game plugin request with kActionDiscard.
 */
- (void)sendDiscardRequest
{    
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:kActionDiscard forKey:kAction];
    [self sendGamePluginRequestWithObject:obj];
    
    NSLog(@"Send game plugin request with EsObject: %@", obj);
}

/*
 * Send game plugin request with kActionChooseCard.
 */
- (void)sendChooseCardRequest
{    
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:kActionChooseCard forKey:kAction];
    if (0 != _player.selectedCardIdxes.count) {
        [obj setIntArray:_player.selectedCardIdxes forKey:kParamCardIndexList];
    }
    if (0 != _player.selectedCardIds.count) {
        [obj setIntArray:_player.selectedCardIds forKey:kParamCardIdList];
    }
    [self sendGamePluginRequestWithObject:obj];
    
    NSLog(@"Send game plugin request with EsObject: %@", obj);
}

/*
 * Send game plugin request with kActionChooseColor.
 */
- (void)sendChooseColorRequest
{    
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:kActionChooseColor forKey:kAction];
    [obj setInt:_player.selectedColor forKey:kParamSelectedColor];
    [self sendGamePluginRequestWithObject:obj];
    
    NSLog(@"Send game plugin request with EsObject: %@", obj);
}

/*
 * Send game plugin request with kActionChooseSuits.
 */
- (void)sendChooseSuitsRequest
{    
    EsObject *obj = [[EsObject alloc] init];
    [obj setInt:kActionChooseSuits forKey:kAction];
    [obj setInt:_player.selectedSuits forKey:kParamSelectedSuits];
    [self sendGamePluginRequestWithObject:obj];
    
    NSLog(@"Send game plugin request with EsObject: %@", obj);
}

/*
 * Receive game plugin message event. Handle different returning actions.
 */
- (void)onGamePluginMessageEvent:(EsPluginMessageEvent *)e
{
    NSLog(@"Receive game plugin message with EsObject: %@", e.parameters);
    
    EsObject *obj = e.parameters;
    NSInteger action = [obj intWithKey:kAction];
    NSAssert(kActionInvalid != action, @"Invalid action in %@", NSStringFromSelector(_cmd));
    _gameLayer.selfPlayer.action = action;
    
//  Remaining card count
    _gameLayer.remainingCardCount = [obj intWithKey:kParamRemainingCardCount];
    
//  Receive public message with specified player name
    NSString *playerName = [obj stringWithKey:kParamPlayerName];
    _gameLayer.currPlayerName = playerName;
    _player = (_gameLayer && playerName) ? [_gameLayer playerWithName:playerName] : _gameLayer.selfPlayer;
    
//  Action handling
    switch (action) {
        case kActionStartGame:
//          TEMP
            self.users = [obj stringArrayWithKey:kParamUserList];
//            self.users = _es.managerHelper.userManager.users;
            [[BGRoomLayer sharedRoomLayer] showGameLayer];
            NSLog(@"All login users: %@", self.users);
            
            _gameLayer = [BGGameLayer sharedGameLayer];
            _playingDeck = _gameLayer.playingDeck;
            break;
            
        case kActionUpdateDeckHero:
            [_playingDeck updatePlayingDeckWithHeroIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionUpdateDeckSelectedHeros:
            [_gameLayer renderOtherPlayersHeroWithHeroIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
        
        case kActionUpdateDeckCuttedCard:
            _playingDeck.isNeedClearDeck = YES;
        case kActionUpdateDeckUsedCard:
        case kActionUpdateDeckAssigning:
            [_playingDeck updatePlayingDeckWithCardIds:[obj intArrayWithKey:kParamCardIdList]];
            _player.handCardCount = [obj intWithKey:kParamHandCardCount];
            break;
            
        case kActionUpdateDeckHandCard:
            [_playingDeck updatePlayingDeckWithCardCount:[obj intWithKey:kParamHandCardCount]
                                            equipmentIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionInitPlayerHero:
            [_player renderHeroWithHeroId:[obj intWithKey:kParamSelectedHeroId]];
            break;
            
        case kActionInitPlayerCard:
            [_player renderHandCardWithCardIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionUpdatePlayerHero:
            [_player updateHeroWithBloodPoint:[obj intWithKey:kParamHeroBloodPoint]
                                   angerPoint:[obj intWithKey:kParamHeroAngerPoint]];
            break;
        
        case kActionUpdatePlayerHand:
            [_player updateHandCardWithCardIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionUpdatePlayerEquipment:
            [_player updateEquipmentWithCardIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionChooseCardToCut:
            [_player addPlayingMenu];
            [_player addProgressBar];
            [_gameLayer addProgressBarForOtherPlayers];
            break;
            
        case kActionPlayingCard:
            _playingDeck.isNeedClearDeck = YES; // 每张卡牌结算完后需要清除桌面
        case kActionChooseCardToUse:
        case kActionChooseCardToDiscard:
        case kActionChoosingColor:
        case kActionChoosingSuits:
            [_player addProgressBar];
            if (_player.playerName == _gameLayer.selfPlayer.playerName) {
                [_player addPlayingMenu];
                [_player enableHandCardWithCardIds:[obj intArrayWithKey:kParamAvailableIdList]
                               selectableCardCount:[obj intWithKey:kParamSelectableCardCount]];
            }
            break;
            
        case kActionChooseCardToExtract:
            _player.canExtractCardCount = [obj intWithKey:kParamExtractedCardCount];
            break;
            
        default:
            break;
    }
    
    [_player clearBuffer];
}