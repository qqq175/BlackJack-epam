package org.qqq175.blackjack.service.implemented.main;

import static org.qqq175.blackjack.service.ActionResult.ActionType.REDIRECT;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qqq175.blackjack.StringConstant;
import org.qqq175.blackjack.logic.main.LocaleLogic;
import org.qqq175.blackjack.persistence.dao.util.Settings;
import org.qqq175.blackjack.service.Action;
import org.qqq175.blackjack.service.ActionResult;

/**
 * Set locale and save cookies
 * @author qqq175
 *
 */
public class SetLocaleAction implements Action {

	private static final int COOKIE_LIFETIME = 7 * 24 * 60 * 60;

	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
		String localeString = request.getParameter(StringConstant.PARAMETER_LOCALE);
		LocaleLogic lLogic = new LocaleLogic();
		Locale locale = lLogic.getLocaleByString(localeString);

		request.getSession().setAttribute(StringConstant.ATTRIBUTE_LOCALE, locale);

		Cookie cookie = new Cookie(StringConstant.COOKIE_LOCALE, locale.getLanguage());
		cookie.setMaxAge(COOKIE_LIFETIME);
		response.addCookie(cookie);

		return new ActionResult(REDIRECT, Settings.getInstance().getContextPath());
	}

}
