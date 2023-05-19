package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Service.HttpClient;
import Service.ProductService;
import Service.beans.Product;
import Service.beans.ProductReview;
import common.constants.Constants;
import common.constants.HttpClientConstants;
import common.utils.PropertiesUtil;
import common.utils.Util;

/**
 * 
 */

/**
 * @author iwata
 *
 */
public class Execute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// 入力された検索ワードをエンコードして保持
		String searchWord = args[0];

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
		
		for (Product p : productList) {
			System.out.println("商品名 " + p.getName());
			System.out.println("レビュー値 " + p.getTotalScore());
			if (p.getTotalScore() ==  Constants.NO_REVIEW) {
				System.out.println("");
				continue;
			}
			for (ProductReview pr : p.getReviewList()) {
				System.out.println("口コミ " + pr.getReview());
			}
			System.out.println("");
		}

	}

}
