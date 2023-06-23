package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.constants.Constants;
import common.constants.HttpClientConstants;
import common.utils.PropertiesUtil;
import common.utils.Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.HttpClient;
import service.ProductService;
import service.beans.Product;
import service.beans.ProductReview;

public class ExecuteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.getRequestDispatcher("/Search.jsp").forward(request, response);
	}
	
	//doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchWord = "";

		searchWord = request.getParameter("search");
		
		// headers用Mapの宣言
		Map<String, String> headers = new HashMap<String, String>();
		// Amazon用通信先User-Agentを設定
		headers.put(HttpClientConstants.USER_AGENT_KEY, HttpClientConstants.USER_AGENT_VALUE);
		// Amazon用通信先URLを作成
        String urlStr = "";
        urlStr = PropertiesUtil.getProperties(Constants.AMAZON_URL) + PropertiesUtil.getProperties(Constants.AMAZON_URL_SEARCH);
        
		String serchWordEncoding = Util.urlEncode(searchWord, Constants.UTF_8);

		// http通信を行い、通信先ページのhttpのリストを取得
		HttpClient httpClient = new HttpClient();
		ArrayList<String> lineList = httpClient.doGet(urlStr, serchWordEncoding, headers);
		
		ProductService productService = new ProductService();
		// 商品情報（商品名・レビューの値・商品詳細URL）の取得
		ArrayList<Product> productList = productService.getProductList(lineList);
		// 商品詳細URLを利用し、各商品の口コミ(最大5件)を取得して商品リストにセット。
		productList = productService.getProductReviewList(productList);
		
//		for (Product p : productList) {
//			System.out.println("商品名 " + p.getName());
//			System.out.println("レビュー値 " + p.getTotalScore());
//			if (p.getTotalScore() ==  Constants.NO_REVIEW) {
//				System.out.println("");
//				continue;
//			}
//			
//			if (null !=  p.getReviewList()) {
//				for (ProductReview pr : p.getReviewList()) {
//					System.out.println("口コミ " + pr.getReview());
//				}
//				System.out.println("");
//			} else {
//				System.out.println("口コミ無し" );
//				System.out.println("");
//			}
//		}

		//request.setAttribute("searchWord", searchWord);
		request.setAttribute("productList", productList);

		request.getRequestDispatcher("/WEB-INF/SearchResult.jsp").forward(request, response);

	}
}
