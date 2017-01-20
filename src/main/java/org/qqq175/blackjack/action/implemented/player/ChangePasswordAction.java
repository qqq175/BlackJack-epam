package org.qqq175.blackjack.action.implemented.player;

import static org.qqq175.blackjack.action.ActionResult.ActionType.REDIRECT;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qqq175.blackjack.StringConstant;
import org.qqq175.blackjack.action.Action;
import org.qqq175.blackjack.action.ActionResult;
import org.qqq175.blackjack.logic.player.ModifyUserLogic;
import org.qqq175.blackjack.persistence.dao.util.JSPPathManager;
import org.qqq175.blackjack.persistence.dao.util.Settings;
import org.qqq175.blackjack.persistence.entity.User;

public class ChangePasswordAction implements Action {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> params = request.getParameterMap();
		ModifyUserLogic muLogic = new ModifyUserLogic();
		User user = (User) request.getSession().getAttribute(StringConstant.ATTRIBUTE_USER);

		ModifyUserLogic.Result logicResult = muLogic.changePassowrd(params, user);
		if (logicResult == ModifyUserLogic.Result.OK) {
			request.getSession().setAttribute(StringConstant.ATTRIBUTE_PASSWORD_MESSAGE, "message.pass.success");
		} else {
			request.getSession().setAttribute(StringConstant.ATTRIBUTE_PASSWORD_MESSAGE, "message.pass.error " + logicResult.getMessage());
		}

		muLogic.updateSessionUser(request.getSession());

		String context = Settings.getInstance().getContextPath();
		String path = context + JSPPathManager.getProperty("command.settings");
		return new ActionResult(REDIRECT, path);
	}

}
