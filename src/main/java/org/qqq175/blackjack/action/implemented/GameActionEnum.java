package org.qqq175.blackjack.action.implemented;

import org.qqq175.blackjack.action.Action;
import org.qqq175.blackjack.action.ActionEnum;
import org.qqq175.blackjack.action.implemented.game.DoAction;
import org.qqq175.blackjack.action.implemented.game.GameAction;
import org.qqq175.blackjack.action.implemented.game.GetStateAction;
import org.qqq175.blackjack.action.implemented.game.LeaveGameAction;
import org.qqq175.blackjack.action.implemented.game.NewGameAction;
import org.qqq175.blackjack.action.implemented.game.ShowRulesAction;
import org.qqq175.blackjack.action.implemented.main.IndexAction;

/**
 * contains GAME actions
 * @author qqq175
 *
 */
public enum GameActionEnum implements ActionEnum {
	UNKNOWN {
		{
			this.action = new IndexAction();
		}
	},
	GAME {
		{
			this.action = new GameAction();
		}
	},
	NEWSOLO {
		{
			this.action = new NewGameAction(NewGameAction.Mode.SINGLEPLAYER);
		}
	},
	NEWMULTI {
		{
			this.action = new NewGameAction(NewGameAction.Mode.MULTIPLAYER);
		}
	},
	GETSTATE {
		{
			this.action = new GetStateAction();
		}
	},
	DEAL {
		{
			this.action = new DoAction(DoAction.ActionType.DEAL);
		}
	},
	DOUBLE {
		{
			this.action = new DoAction(DoAction.ActionType.DOUBLE);
		}
	},
	HIT {
		{
			this.action = new DoAction(DoAction.ActionType.HIT);
		}
	},
	INSURANCE {
		{
			this.action = new DoAction(DoAction.ActionType.INSURANCE);
		}
	},
	STAY {
		{
			this.action = new DoAction(DoAction.ActionType.STAY);
		}
	},
	SPLIT {
		{
			this.action = new DoAction(DoAction.ActionType.SPLIT);
		}
	},
	SURRENDER {
		{
			this.action = new DoAction(DoAction.ActionType.SURRENDER);
		}
	},
	LEAVE {
		{
			this.action = new LeaveGameAction();
		}
	},
	RULES {
		{
			this.action = new ShowRulesAction();
		}
	};
	Action action;

	@Override
	public Action getAction() {
		return action;
	}
}
