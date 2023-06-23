<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="service.beans.Product" %>
<%@ page import="service.beans.ProductReview" %>
<%@ page import="common.constants.Constants" %>
<%@ page import="java.util.ArrayList" %>
<% ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("productList"); %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/ProductSearch/css/common.css">
<meta charset="UTF-8">
<title>検索結果画面</title>
</head>
<body>
<!--<p>あなたが検索したワードは<%= request.getAttribute("searchWord") %>です</p>-->
<h1 class="back_green">検索結果</h1>
<% for (Product p : productList) { %>
	<p>商品名：<%= p.getName() %></p>
	<p>レビュー値：<%= p.getTotalScore() %></p>
    <% if (p.getTotalScore() ==  Constants.NO_REVIEW) { 
    	out.println("<br>");
    	continue;  
     } %>
    <% if (null !=  p.getReviewList()) { %>
	    <% for (ProductReview pr : p.getReviewList()) { %>
	    	<p>口コミ：<%= pr.getReview() %></p>
		<% } %>
			<br>
	<% } else { %>
		<p>口コミ：無し</p>
		<br>
	<% } %>	
<%  } %>
</body>
</html>