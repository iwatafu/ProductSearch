<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検索画面</title>
 <script src="/ProductSearch/js/common.js"></script>
</head>
<body>
	<form method="POST" action="search" name="searchform" onsubmit="return formcheck()">
		<!-- 検索対象の入力 -->
		検索欄:<input type="text" name="search"><br>

		<!-- 送信ボタン -->
		<input type="submit" value="送信"><br>
	</form>
</body>
</html>