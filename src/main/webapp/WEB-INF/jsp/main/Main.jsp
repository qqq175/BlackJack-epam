<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${curLocale}" />
<fmt:setBundle basename="conf.i18n.jsp" />

<!DOCTYPE html lang="${curLocale}">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="utf-8">
<title>The Quasino!</title>
<script type="text/javascript"
	src='/blackjack/script/locale/messages-${not empty sessionScope.curLocale ? sessionScope.curLocale.language : "en"}.js'
	charset="utf-8"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="/blackjack/script/jquery.leanModal.min.js" charset="utf-8"></script>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" />
<link href="/blackjack/css/style.css" rel="stylesheet" />
</head>
<body
	${empty sessionScope.loginError ? "" : " onload=\"$('#modal_login').trigger('click')\""}>
	<div id="shadow"></div>
	<!-- IMPORT HEADER -->
	<jsp:include page="/WEB-INF/jsp/element/GuestHeader.jsp" />
	<div class="container">
		<!-- IMPORT LEFT MENU -->
		<jsp:include page="/WEB-INF/jsp/element/GuestSideMenu.jsp" />

         <fmt:message key="page.main.welcome" var="welcome" />
		<jsp:include
			page='${not empty mainform ? mainform : welcome}' />


		<!-- IMPORT ASIDE HERE -->
		<jsp:include page="/WEB-INF/jsp/element/Aside.jsp" />
	</div>

	<!-- IMPORT FOOTER -->
	<jsp:include page="/WEB-INF/jsp/element/Footer.jsp" />

	<!-- LOGIN FORM -->
	<jsp:include page="/WEB-INF/jsp/main/Login.jsp" />
	<script type="text/javascript" src="/blackjack/script/page-style.js"
		charset="utf-8"></script>
	<script type="text/javascript">
		let popupMessage = '${sessionScope.popupMessage}';
		if (popupMessage.length > 0) {
			alert(popupMessage);
		}
		<c:remove var="popupMessage" scope="session" />
	</script>
	<script src="http://vk.com/js/api/openapi.js"></script>
 	<script type="text/javascript" src="/blackjack/script/visitorinfo.js"
		charset="utf-8"></script>
</body>
</html>