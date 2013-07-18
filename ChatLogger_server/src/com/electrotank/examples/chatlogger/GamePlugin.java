package com.electrotank.examples.chatlogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dota.component.CardModel;
import com.wolf.dota.component.CharacterEnum;
import com.wolf.dota.component.DeskModel;
import com.wolf.dota.component.Player;
import com.wolf.dota.component.constants.Commands;
import com.wolf.dota.component.constants.Params;
import com.wolf.dota.component.constants.Code;

public class GamePlugin extends BasePlugin implements Code, Commands, Params {
	private List<CharacterEnum> allCharactersForChoose = new ArrayList<CharacterEnum>();
	private List<String> players;
	private String currentPlayer;
	private Player[] realPlayers;
	private int[] playerChoseCharactors;
	private boolean gameStarted = false;
	private List<CardModel> cardStack;
	private List<Integer> dropStack;
	
	private String[] playerStates;
	private final String player_state_character_confirmed = "char_confirmed";
	private final String player_state_staked = "staked";
	private final String player_state_waiting_for_stake = "wait_stake";
	// 势力
	private Integer[] force;
	private final int force_a = 1;
	private final int force_b = 5;
	
	/******** game state start ********/
	private final int playerFlagInGameState = 1;
	private final int stateFlagInGameState = 0;
	private final int gameStage_none = -1;
	private final int playerIndex_none = -1;
	private int[] gameState = new int[2];
	{
		gameState[stateFlagInGameState] = gameStage_none;
		gameState[playerFlagInGameState] = playerIndex_none;
	}
	
	private int[] playerStakes;
	@SuppressWarnings("unused")
	private DeskModel desk;
	
	/******** action caches *********/
	private final int actionCacheNone = -1;
	private int actionCache = actionCacheNone;
	private int additionalEffect = actionCacheNone;
	private String attackerCache = "";
	
	/******** game state end ********/
	
	@Override
	public void init(EsObjectRO parameters) {
		
		initCharactorsRandomly();
		getApi().getLogger().debug("ChatPlugin initialized");
	}
	
	private void initCharactorsRandomly() {
		allCharactersForChoose = Arrays.asList(CharacterEnum.values());
		Collections.shuffle(allCharactersForChoose);
		d.debug("List<CharacterEnum> charactors is ready");
	}
	
	@Override
	public void request(String user, EsObjectRO message) {
		EsObject messageIn = new EsObject();
		messageIn.addAll(message);
		getApi().getLogger().debug(user + " requests: " + messageIn.toString());
		
		int action = messageIn.getInteger(code_action);
		
		if (action == ACTION_START_GAME && !gameStarted) {
			getApi().getLogger().debug("got action start game");
			players = new ArrayList<String>();
			reorderUsers();
			
			/* init information depending on player size and order */
			initSizes();
			chooseCharacters(messageIn);
			gameStarted = true;
			
		} else if (action == ACTION_CHOSE_CHARACTER) {
			choseCharacter(user, messageIn);
			
		} else if (action == ACTION_DRAW_CARDS) {
			dispatchHandCards(user);
			EsObject obj = new EsObject();
			obj.setInteger(code_client_action_required, ac_require_play);
			sendGamePluginMessageToUser(user, obj);
		} else if (action == ACTION_STAKE) {
			gotStakeCard(user, messageIn);
		} else if (action == ACTION_USED_CARD) {
			usedCard(user, messageIn);
		} else if (action == ACTION_HP_DAMAGED) {
			hitted(user, messageIn);
		} else if (action == ACTION_CONTINUE_PLAYING) {
			continuePlay(user, messageIn);
		} else if (action == ACTION_DROP_CARDS) {
			dropCards(user, messageIn);
			
		}
		
		else if (action == ACTION_S_LagunaBlade) {
			s_LagunaBlade(user, messageIn);
		} else if (action == ACTION_S_GodsStrength) {
			s_GodsStrength(user, messageIn);
		} else if (action == ACTION_S_ViperRaid) {
			s_ViperRaid(user, messageIn);
		} else if (action == ACTION_Mislead) {
			m_Mislead(user, messageIn);
		} else if (action == ACTION_Dispel) {
			m_Dispel(user, messageIn);
		}
		
	}
	
	private void continuePlay(String user, EsObject messageIn) {
		EsObject turner = new EsObject();
		turner.setInteger(code_client_action_required, ac_require_play);
		sendGamePluginMessageToUser(user, turner);
		
	}
	
	private void usedCard(String player, EsObject messageIn) {
		int[] cards = messageIn.getIntegerArray(USED_CARDS);
		if (cards == null || cards.length == 0) {
			EsObject obj = new EsObject();
			obj.setString(error, "no card used");
			return;
		}
		if (cards.length > 1) {
			// todo, ...
			return;
		}
		int function = CardModel.getFunctionById(cards[0]);
		switch (function) {
			case CardModel.function_id_normal_attack:
			case CardModel.function_id_flame_attack:
			case CardModel.function_id_chaos_attack: {
				attack(player, messageIn);
				break;
			}
			case CardModel.function_id_evasion: {
				evasion(player, messageIn);
				break;
			}
			case CardModel.function_id_heal: {
				heal(player, messageIn);
				break;
			}
			case CardModel.function_id_Disarm: {
				m_Disarm(player, messageIn);
				break;
			}
			case CardModel.function_id_m_ElunesArrow: {
				m_ElunesArrow(player, messageIn);
				break;
			}
			case CardModel.function_id_m_Chakra:{
			    m_Chakra(player, messageIn);
			    break;
			}
		}
		
	}
	
	private void m_Chakra(String player, EsObject messageIn) {
    
        // TODO Auto-generated method stub
        
    }

    private void m_ElunesArrow(String player, EsObject messageIn) {
		int[] cards = messageIn.getIntegerArray(USED_CARDS, new int[] {});
		if (dropStack != null) {
			for (int card : cards) {
				dropStack.add(card);
			}
		} else {
			return;
		}
		
		actionCache = cards[0];
		
		EsObject elunesArrow = new EsObject();
		elunesArrow.setInteger(code_client_action_required, ac_require_targetted_by_magic_card);
		
		if (messageIn.getBoolean(STRENGTHED, false)) {
			elunesArrow.setInteger(TARGET_SUITS, messageIn.getInteger(TARGET_SUITS));
		} else {
			elunesArrow.setInteger(TARGET_COLOR, messageIn.getInteger(TARGET_COLOR));
		}
		
		elunesArrow.setIntegerArray(USED_CARDS, messageIn.getIntegerArray(USED_CARDS, new int[] {}));
		String target = messageIn.getStringArray(TARGET_PLAYERS, new String[] {})[0];
		sendGamePluginMessageToUser(target, elunesArrow);
		
	}
	
	private void m_Disarm(String user, EsObject obj) {
		
		String target = obj.getStringArray(TARGET_PLAYERS)[0];
		dropCard(obj, TARGET_CARD);
		dropCard(obj);
		looseEquipment(target, obj.getIntegerArray(TARGET_CARD));
		if (obj.getBoolean(STRENGTHED, false)) {
			spLost(user, 1, new EsObject());
			getCard(user, obj.getIntegerArray(TARGET_CARD));
		}
	}
	
	private void getCard(String player, int[] cards) {
		EsObject obj = new EsObject();
		obj.setInteger(code_action, ACTION_GET_SPECIFIC_CARD);
		obj.setIntegerArray(TARGET_CARD, cards);
		sendGamePluginMessageToUser(player, obj);
	}
	
	private void looseEquipment(String player, int[] cards) {
		EsObject obj = new EsObject();
		obj.setInteger(code_action, ACTION_LOOSE_EQUIPMENT);
		obj.setIntegerArray(TARGET_CARD, cards);
		sendGamePluginMessageToUser(player, obj);
	}
	
	private void m_Dispel(String user, EsObject obj) {
		actionCache = actionCacheNone;
		additionalEffect = actionCacheNone;
		int[] cards = obj.getIntegerArray(USED_CARDS);
		if (cards != null) {
			for (int card : cards) {
				dropStack.add(card);
			}
		}
		
	}
	
	private void m_Mislead(String user, EsObject obj) {
		dropCard(obj);
		String[] ps = obj.getStringArray(TARGET_PLAYERS);
		String source = ps[0];
//		String target = ps[1];
		// what source player happen
		spLost(source, 1, obj);
		dispatchHandCards(source, 1);
		// what target player happen
		obj.setInteger(code_action, ACTION_SP_UP);
		obj.setInteger(SP_CHANGED, 1);
		
	}
	
	private void s_ViperRaid(String user, EsObject messageIn) {
		// TODO ?
		
	}
	
	private void s_GodsStrength(String user, EsObject obj) {
		additionalEffect = obj.getInteger(code_action);
		int[] cards = obj.getIntegerArray(USED_CARDS);
		if (cards != null) {
			for (int card : cards) {
				dropStack.add(card);
			}
		}
		
		dispatchHandCards(user, 1);
		
	}
	
	private void s_LagunaBlade(String user, EsObject obj) {
		spLost(user, 3, obj);
		attack(user, obj);
		
	}
	
	private void heal(String user, EsObject obj) {
		obj.setInteger(code_action, ACTION_HP_RESTORE);
		obj.setInteger(HP_CHANGED, 1);
		obj.setInteger(code_client_action_required, ac_require_restored_hp);
		sendGamePluginMessageToUser(obj.getStringArray(TARGET_PLAYERS)[0], obj);
		
		EsObject p = new EsObject();
		p.setInteger(code_client_action_required, ac_require_play);
		sendGamePluginMessageToUser(currentPlayer, p);
		
	}
	
	private void attack(String user, EsObject obj) {
		
		int[] cards = obj.getIntegerArray(USED_CARDS);
		if (cards != null) {
			for (int card : cards) {
				dropStack.add(card);
			}
		}
		actionCache = cards[0];
		attackerCache = user;
		
		EsObject requireAction = new EsObject();
		requireAction.setInteger(code_action, ACTION_TARGETTED);
		requireAction.setInteger(code_client_action_required, ac_require_attacked);
		requireAction.setIntegerArray(USED_CARDS, cards);
		requireAction.setString(PLAYER_NAME, user);
		for (String target : obj.getStringArray(TARGET_PLAYERS)) {
			sendGamePluginMessageToUser(target, requireAction);
		}
	}
	
	private void hitted(String user, EsObject obj) {
		int[] cards = obj.getIntegerArray(USED_CARDS, new int[] {});
		if (cards != null && cards.length > 0) {
			for (int card : cards) {
				dropStack.add(card);
			}
		}
		int additionalHit = additionalEffect == CardModel.function_id_s_GodsStrength ? 1 : 0;
		int function = CardModel.getFunctionById(actionCache);
		
		switch (function) {
			case CardModel.function_id_normal_attack: {
				EsObject hurt = new EsObject();
				int hurtAmount = (1 + additionalHit);
				hurt.setInteger(code_action, ACTION_HP_DAMAGED);
				hurt.setInteger(code_client_action_required, ac_require_hitted);
				hurt.setInteger(HP_CHANGED, -hurtAmount);
				hurt.setInteger(SP_CHANGED, hurtAmount);
				hurt.setInteger(USED_CARDS, actionCache);
				sendGamePluginMessageToUser(user, hurt);
				EsObject attacker = new EsObject();
				attacker.setInteger(code_client_action_required, ac_require_made_damage);
				sendGamePluginMessageToUser(attackerCache, attacker);
				break;
			}
			case CardModel.function_id_flame_attack: {
				EsObject hurt = new EsObject();
				int hurtAmount = (1 + additionalHit);
				hurt.setInteger(code_action, ACTION_HP_DAMAGED);
				hurt.setInteger(code_client_action_required, ac_require_hitted);
				hurt.setInteger(HP_CHANGED, -hurtAmount);
				hurt.setInteger(SP_CHANGED, hurtAmount + 1);
				hurt.setInteger(USED_CARDS, actionCache);
				sendGamePluginMessageToUser(user, hurt);
				EsObject attacker = new EsObject();
				attacker.setInteger(code_client_action_required, ac_require_made_damage);
				sendGamePluginMessageToUser(attackerCache, attacker);
				break;
			}
			case CardModel.function_id_chaos_attack: {
				EsObject hurt = new EsObject();
				int hurtAmount = (1 + additionalHit);
				hurt.setInteger(code_action, ACTION_HP_DAMAGED);
				hurt.setInteger(code_client_action_required, ac_require_hitted);
				hurt.setInteger(HP_CHANGED, -hurtAmount);
				hurt.setInteger(SP_CHANGED, hurtAmount);
				hurt.setInteger(USED_CARDS, actionCache);
				sendGamePluginMessageToUser(user, hurt);
				EsObject angry = new EsObject();
				angry.setInteger(code_action, ACTION_SP_UP);
				angry.setInteger(code_client_action_required, ac_require_made_damage);
				angry.setInteger(SP_CHANGED, 1);
				sendGamePluginMessageToUser(attackerCache, angry);
				break;
			}
			case CardModel.function_id_s_LagunaBlade: {
				EsObject hurt = new EsObject();
				int[] evations = obj.getIntegerArray(USED_CARDS, new int[] {});
				dropCard(hurt);
				obj.setInteger(code_action, ACTION_SP_UP);
				obj.setInteger(SP_CHANGED, 3 - evations.length);
				damage(user, -(3 - evations.length), hurt);
				break;
			}
			case CardModel.function_id_m_ElunesArrow: {
				EsObject hurt = new EsObject();
				int hurtAmount = 1;
				hurt.setInteger(code_action, ACTION_HP_DAMAGED);
				hurt.setInteger(code_client_action_required, ac_require_hitted);
				hurt.setInteger(HP_CHANGED, -hurtAmount);
				hurt.setInteger(SP_CHANGED, hurtAmount);
				hurt.setInteger(USED_CARDS, actionCache);
				sendGamePluginMessageToUser(user, hurt);
				EsObject attacker = new EsObject();
				attacker.setInteger(code_client_action_required, ac_require_made_damage);
				sendGamePluginMessageToUser(attackerCache, attacker);
				break;
			}
			
		}
		
		actionCache = actionCacheNone;
		attackerCache = "";
	}
	
	private void spLost(String user, int howMuch, EsObject obj) {
		obj.setInteger(code_action, ACTION_SP_LOST);
		obj.setInteger(SP_CHANGED, howMuch);
		sendGamePluginMessageToUser(user, obj);
	}
	
	private void damage(String user, int howMuch, EsObject obj) {
		obj.setInteger(code_action, ACTION_HP_DAMAGED);
		obj.setInteger(HP_CHANGED, howMuch);
		sendGamePluginMessageToUser(user, obj);
	}
	
	private void evasion(String user, EsObject obj) {
		int[] cards = obj.getIntegerArray(USED_CARDS);
		if (cards != null) {
			for (int card : cards) {
				dropStack.add(card);
			}
		}
		obj.setInteger(code_action, ACTION_REACTED);
		obj.setStringArray(TARGET_PLAYERS, new String[] { user });
		obj.setInteger(code_client_action_required, ac_require_play);
		actionCache = additionalEffect = actionCacheNone;
		sendGamePluginMessageToUser(attackerCache, obj);
		attackerCache = "";
	}
	
	private void dropCard(EsObject obj, String key) {
		int[] cards = obj.getIntegerArray(key);
		if (cards != null) {
			for (int card : cards) {
				dropStack.add(card);
			}
		}
	}
	
	private void dropCard(EsObject obj) {
		dropCard(obj, USED_CARDS);
	}
	
	private void dropCards(String user, EsObject messageIn) {
		int[] cards = messageIn.getIntegerArray(USED_CARDS);
		for (int card : cards) {
			dropStack.add(card);
		}
		actionCache = actionCacheNone;
		EsObject obj = new EsObject();
		obj.setInteger(code_client_action_required, ac_require_play);
		sendGamePluginMessageToUser(currentPlayer, obj);
	}
	
	// private void dropCard(String user, EsObject obj){
	// todo user also need drop his/her cards
	
	private void initSizes() {
		playerChoseCharactors = new int[players.size()];
		force = new Integer[players.size()];
		playerStates = new String[players.size()];
		playerStakes = new int[players.size()];
		realPlayers = new Player[players.size()];
		// deprecating above logics, change to use desk model
		desk = new DeskModel(players.size());
	}
	
	private void gotStakeCard(String user, EsObject messageIn) {
		playerStates[players.indexOf(user)] = player_state_staked;
		playerStakes[players.indexOf(user)] = messageIn
				.getIntegerArray(USED_CARDS)[0];
		dropStack.add(playerStakes[players.indexOf(user)]);
		for (String playerState : playerStates) {
			if (!playerState.equals(player_state_staked)) {
				return;
			}
		}
		
		int biggestNumber = CardModel.valueOf("_" + playerStakes[0])
				.getPokerValue();
		String startPlayer = players.get(0);
		for (int i = 1; i < players.size(); i++) {
			int stake = CardModel.valueOf("_" + playerStakes[i])
					.getPokerValue();
			if (stake > biggestNumber) {
				biggestNumber = stake;
				startPlayer = players.get(i);
			}
		}
		
		EsObject obj = new EsObject();
		obj.setInteger(code_action, ACTION_STAKE);
		obj.setIntegerArray(ALL_STAKE_CARDS, playerStakes);
		for (String p : players) {
			sendGamePluginMessageToUser(p, obj);
		}
		
		gameTurn(startPlayer);
		
	}
	
	// private List<String> reorderPlayer(String startPlayer) {
	// int startCount = players.indexOf(startPlayer);
	// List<String> newPlayerList = new ArrayList<String>();
	// for (int i = 0; i < players.size(); i++) {
	// if ((i + startCount) < players.size()) {
	// newPlayerList.add(players.get(i + startCount));
	// } else {
	// newPlayerList.add(players.get((i + startCount) - players.size()));
	// }
	// }
	// return newPlayerList;
	// }
	//
	private void gameTurn(String player) {
		currentPlayer = player;
		updateRequiredAction(player, ac_require_determing);
		updateRequiredAction(player, ac_require_draw);
	}
	
	private void updateRequiredAction(String player, int actionRequired) {
		d.debug("sending state : " + actionRequired);
		EsObject obj = new EsObject();
		obj.setInteger(code_client_action_required, actionRequired);
		sendGamePluginMessageToUser(player, obj);
		d.debug("sent state with obj: " + obj + " to player " + player);
		
	}
	
	/**************** logic in game loop end ***************************/
	/**************** logic before game start start ***************************/
	
	private void initCardStack() {
		cardStack = new LinkedList<CardModel>(Arrays.asList(CardModel.values()));
		Collections.shuffle(cardStack);
		getApi().getLogger().debug("card stack is ready to use,");
		dropStack = new LinkedList<Integer>();
	}
	
	private void dispatchHandCards(String player) {
		dispatchHandCards(player, 2);
	}
	
	private void dispatchHandCards(String player, int howMany) {
		dispatchHandCards(player, howMany, ACTION_SEND_CARDS);
	}
	
	private void dispatchHandCards(String player, int howmany, int action) {
		EsObject obj = new EsObject();
		int[] cards = new int[howmany];
		for (int i = 0; i < howmany; i++) {
			cards[i] = cardStack.get(i).getCardId();
			d.debug(logprefix + "cardStack size : " + cardStack.size());
			cardStack.remove(0);
			
		}
		obj.setInteger(code_action, action);
		obj.setIntegerArray(DISPATCH_CARDS, cards);
		realPlayers[players.indexOf(player)].addHandCards(cards);
		
		sendGamePluginMessageToUser(player, obj);
	}
	
	private synchronized void choseCharacter(String user, EsObject messageIn) {
		getApi().getLogger().debug(
				logprefix + "set to user : " + user
						+ " of index in players list : "
						+ players.indexOf(user));
		playerChoseCharactors[players.indexOf(user)] = messageIn
				.getInteger(SELECTED_HERO_ID);
		
		playerStates[players.indexOf(user)] = player_state_character_confirmed;
		
		// after all players has chose their characters, then dispatch states
		
		for (int i = 0; i < players.size(); i++) {
			String playerState = playerStates[i];
			if (playerState == null ||
					!playerState.equals(player_state_character_confirmed)) {
				return;
			}
		}
		sendAllHeros();
		initRealPlayers();
		initCardStack();
		dispatchForce();
		for (String player : players) {
			dispatchHandCards(player, 9, ACTION_DISPATCH_HANDCARD);
		}
		
		// then wait for stake
		for (int i = 0; i < players.size(); i++) {
			playerStates[i] = player_state_waiting_for_stake;
		}
		
	}
	
	private void initRealPlayers() {
		for (int i = 0; i < playerChoseCharactors.length; i++) {
			realPlayers[i] = Player.getPlayerById(playerChoseCharactors[i],
					players.get(i));
		}
	}
	
	@SuppressWarnings("unused")
	private int nextPlayer() {
		actionCache = actionCacheNone;
		additionalEffect = actionCacheNone;
		int nextPlayerIndex = gameState[playerFlagInGameState] + 1;
		if (nextPlayerIndex > players.size()) {
			nextPlayerIndex = 0;
		}
		gameState[playerFlagInGameState] = nextPlayerIndex;
		return nextPlayerIndex;
	}
	
	private void sendAllHeros() {
		EsObject obj = new EsObject();
		obj.setInteger(code_action, ACTION_ALL_HEROS);
		obj.setIntegerArray(ALL_HEROS, playerChoseCharactors);
		for (String player : players) {
			sendGamePluginMessageToUser(player, obj);
		}
		
		// sendGamePluginMessageToRoom(obj);
	}
	
	private void dispatchForce() {
		EsObject obj = new EsObject();
		List<Integer> forceList = Arrays.asList(new Integer[] { force_a,
				force_b });
		
		switch (players.size()) {
			case 1: {
				forceList = Arrays.asList(new Integer[] { force_a });
				break;
			}
			case 2: {
				forceList = Arrays.asList(new Integer[] { force_a, force_b });
				break;
			}
			case 3: {
				forceList = Arrays
						.asList(new Integer[] { force_a, force_b, force_a });
				break;
			}
			case 4: {
				forceList = Arrays
						.asList(new Integer[] { force_a, force_b, force_a,
								force_b });
				break;
			}
			case 5: {
				forceList = Arrays.asList(new Integer[] { force_a, force_b,
						force_a, force_b,
						force_a });
				break;
			}
		}
		
		Collections.shuffle(forceList);
		force = forceList.toArray(new Integer[players.size()]);
		getApi().getLogger().debug(
				"dispatching force: " + Arrays.toString(force));
		
		obj.setInteger(code_action, ACTION_DISPATCH_FORCE);
		obj.setInteger(STACK_CARD_COUNT, cardStack.size());
		for (int i = 0; i < force.length; i++) {
			int f = force[i];
			obj.setInteger(FORCE, f);
			sendGamePluginMessageToUser(players.get(i), obj);
		}
	}
	
	private void reorderUsers() {
		for (UserValue user : getApi().getUsersInRoom(getApi().getZoneId(),
				getApi().getRoomId())) {
			players.add(user.getUserName());
		}
	}
	
	private void chooseCharacters(EsObject obj) {
		
		getApi().getLogger().debug("players = " + players.size());
		for (int i = 0; i < players.size(); i++) {
			int[] charsToChoose = new int[3];
			String player = players.get(i);
			for (int choosingCount = 0; choosingCount < charsToChoose.length; choosingCount++) {
				int shouldAddCharacterCount = i * charsToChoose.length
						+ choosingCount;
				getApi().getLogger()
						.debug(
								"charsToChoose = "
										+ Arrays.toString(charsToChoose) + "\n"
										+ "choosingCount = " + choosingCount
										+ "\n"
										+ "shouldAddCharacterCount = "
										+ shouldAddCharacterCount);
				charsToChoose[choosingCount] = allCharactersForChoose.get(
						shouldAddCharacterCount).getId();
			}
			obj.setInteger(code_action, ACTION_CHOOSE_CHARACTER);
			obj.setIntegerArray(CHARACTORS_TO_CHOOSE, charsToChoose);
			sendGamePluginMessageToUser(player, obj);
			getApi().getLogger().debug(
					"Characters " + Arrays.toString(charsToChoose)
							+ " are sending to player " + player);
		}
		
	}
	
	// private void sendGamePluginMessageToRoom(EsObject obj) {
	// if (cardStack != null) {
	// obj.setInteger(STACK_CARD_COUNT, cardStack.size());
	// }
	// getApi().sendPluginMessageToRoom(getApi().getZoneId(),
	// getApi().getRoomId(), obj);
	// }
	
	private void sendGamePluginMessageToUser(String user, EsObject obj) {
		d.debug(logprefix + "sending plugin message to user " + user + " with obj: \r\n" + obj);
		if (cardStack != null) {
			obj.setInteger(STACK_CARD_COUNT, cardStack.size());
		}
		getApi().sendPluginMessageToUser(user, obj);
	}
	
	private static final String logprefix = "======== game =>> ";
	
	private class D {
		public void debug(String message) {
			getApi().getLogger().debug(message);
		}
	}
	
	private D d = new D();
	
}
