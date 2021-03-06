package org.qqq175.blackjack.logic.blackjack;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.qqq175.blackjack.logic.blackjack.entity.Card;
import org.qqq175.blackjack.logic.blackjack.entity.Dealer;
import org.qqq175.blackjack.logic.blackjack.entity.Hand;
import org.qqq175.blackjack.logic.blackjack.entity.Player;
import org.qqq175.blackjack.logic.blackjack.entity.Score;
import org.qqq175.blackjack.persistence.dao.util.PhotoManager;
import org.qqq175.blackjack.persistence.entity.User;
import org.qqq175.blackjack.pool.UserPool;

/**
 * Represents game state as JSON object
 * @author qqq175
 */
@SuppressWarnings("unchecked")
public class GameJSONizer {

	/**
	 * Represents GAME state as JSON object from USER's "point of view" 
	 * @param game
	 * @param user
	 * @return
	 */
	public static JSONObject toJSON(BlackJackGame game, User user) {
		JSONObject result = new JSONObject();
		JSONArray players = new JSONArray();
		if (game != null) {
			Player activePlayer = game.getActivePlayer();
			boolean isUserActive = false;
			if (activePlayer != null) {
				isUserActive = user.getId().equals(activePlayer.getUserId());
			}
			for (Player player : game.getPlayersList()) {
				boolean isCurrentUser = player.getUserId().equals(user.getId());
				players.add(toJSON(player, isCurrentUser));
			}
			result.put("dealer", toJSON(game.getDealer()));
			{
				JSONObject timer = new JSONObject();
				timer.put("timeLimit", 120);
				result.put("timer", timer);
			}
			result.put("result", null);
			result.put("players", players);

			/* controls */
			{
				JSONObject controls = new JSONObject();
				{
					JSONObject balance = new JSONObject();
					balance.put("value", user.getAccountBalance());
					controls.put("balance", balance);
				}
				{
					JSONObject actions = new JSONObject();
					{
						JSONObject surrender = new JSONObject();
						boolean isActive = isUserActive && game.getGameStage().compareTo(GameStage.PLAY) <= 0 && GameLogic.canSurrender(activePlayer);
						surrender.put("isActive", isActive);
						actions.put("surrender", surrender);
					}
					{
						JSONObject split = new JSONObject();
						boolean isActive = isUserActive && game.getGameStage() == GameStage.PLAY && GameLogic.canSplit(activePlayer);
						split.put("isActive", isActive);
						actions.put("split", split);
					}
					{
						JSONObject doubleBet = new JSONObject();
						boolean isActive = isUserActive && game.getGameStage() == GameStage.PLAY && GameLogic.canDouble(activePlayer);
						doubleBet.put("isActive", isActive);
						actions.put("double", doubleBet);
					}
					{
						JSONObject hit = new JSONObject();
						boolean isActive = isUserActive && game.getGameStage() == GameStage.PLAY && GameLogic.canHit(activePlayer);
						hit.put("isActive", isActive);
						actions.put("hit", hit);
					}
					{
						JSONObject deal = new JSONObject();
						boolean isActive = isUserActive && game.getGameStage() == GameStage.DEAL && GameLogic.canDeal(activePlayer);
						deal.put("isActive", isActive);
						actions.put("deal", deal);
					}
					{
						JSONObject stay = new JSONObject();
						boolean isActive = isUserActive && game.getGameStage().compareTo(GameStage.PLAY) <= 0 && GameLogic.canStay(activePlayer);
						stay.put("isActive", isActive);
						actions.put("stay", stay);
					}
					{
						JSONObject insurance = new JSONObject();
						boolean isActive = isUserActive && game.getGameStage() == GameStage.PLAY
								&& GameLogic.canInsurance(game.getDealer(), activePlayer);
						insurance.put("isActive", isActive);
						actions.put("insurance", insurance);
					}
					controls.put("actions", actions);
				}
				{
					JSONObject bid = new JSONObject();
					boolean isActive = isUserActive && game.getGameStage() == GameStage.DEAL && GameLogic.canDeal(activePlayer);
					bid.put("isActive", isActive);
					controls.put("bid", bid);
				}
				result.put("controls", controls);
			}
		} else {
			result.put("result", "NULL");
			result.put("message", "game is null");
			System.out.println("game is " + game);
		}

		return result;
	}

	/**
	 * Represent dealer state as JSON
	 * @param dealer
	 * @return
	 */
	private static JSONObject toJSON(Dealer dealer) {
		JSONObject result = new JSONObject();
		result.put("hand", toJSON(dealer.getHand(), dealer.isShowAllCards(), false));
		return result;
	}

	/**
	 * represent players state as JSON object. 
	 * @param player
	 * @param isCurrentUser
	 * @return
	 */
	private static JSONObject toJSON(Player player, boolean isCurrentUser) {
		JSONObject result = new JSONObject();
		UserPool userPool = UserPool.getInstance();
		PhotoManager photoManager = new PhotoManager();
		User user = userPool.get(player.getUserId());
		result.put("id", player.getUserId().getValue());
		if (user != null) {
			result.put("id", user.getId().getValue());
			result.put("name", user.getDisplayName());
			result.put("img", photoManager.findPhotoRelativePath(player.getUserId()));
			result.put("hands", toJSON(player.getHandsListCopy()));
			result.put("isActive", player.isActive());
		} else {
			result.put("id", "");
			result.put("img", "");
			result.put("hands", "");
			result.put("isActive", false);
		}
		return result;
	}

	/**
	 * represent hands as JSON object. 
	 * @param hands
	 * @return
	 */
	private static JSONArray toJSON(List<Hand> hands) {
		JSONArray result = new JSONArray();
		for (Hand hand : hands) {
			result.add(toJSON(hand, true, true));
		}
		return result;
	}

	/**
	 * represent hand state as JSON object.
	 * @param hand
	 * @param showAll - shows score and all cards if true, else show only first card and cards backs 
	 * @param isPlayer - for player aslo add bet, insurance and active state
	 * @return
	 */
	private static JSONObject toJSON(Hand hand, boolean showAll, boolean isPlayer) {
		JSONObject result = new JSONObject();
		result.put("cards", toJSON(hand.getCardsListCopy(), showAll));
		if (showAll) {
			result.put("score", toJSON(hand.getScore()));
		}
		if (isPlayer) {
			result.put("bet", hand.getBid());
			result.put("insurance", hand.getInsurance());
			result.put("isActive", hand.isActive());
		}
		return result;
	}

	/**
	 * represent score as JSON object. 
	 * @param score
	 * @return
	 */
	private static String toJSON(Score score) {
		String result = "";
		if (score.isBlackJack()) {
			result = "blackjack";
		} else {
			result = Integer.toString(score.getValue());
		}

		return result;
	}

	/**
	 * represent cards list as JSON object. 
	 * @param cards
	 * @param showAll - true - show all cards, false - show only first card and back of other cards
	 * @return
	 */
	private static JSONArray toJSON(List<Card> cards, boolean showAll) {
		JSONArray result = new JSONArray();
		if (showAll) {
			for (Card card : cards) {
				result.add(card.getImgPath());
			}
		} else {
			if (!cards.isEmpty()) {
				result.add(cards.get(0).getImgPath());
				for (int i = 1; i < cards.size(); i++) {
					result.add(Card.CARD_BACK);
				}
			}
		}
		return result;
	}

}
