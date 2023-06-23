package Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Service.beans.Product;
import Service.beans.ProductReview;
import common.constants.Constants;
import common.constants.HttpClientConstants;
import common.utils.PropertiesUtil;

/**
 * 
 * 検索されたワードに対して商品名・レビュー値・口コミ（最大5件）を返却するクラス
 * 
 *
 */

public class ProductService {

	public ArrayList<Product> getProductList(ArrayList<String> lineList) {

		ArrayList<Product> productList = new ArrayList<Product>();
		StringBuilder sbName = new StringBuilder();
		StringBuilder sbTotalScore = new StringBuilder();
		StringBuilder sbReview = new StringBuilder();

		boolean exceptionflag = false;
		Pattern pName = Pattern.compile(Constants.PRODUCT_NAME_TAG);
		Pattern pTotalScore = Pattern.compile(Constants.PRODUCT_STAR_TAG);
		Pattern pReview = Pattern.compile(Constants.PRODUCT_REVIEWURL_TAG);
		Matcher mName = null;
		Matcher mTotalScore = null;
		Matcher mReview = null;

		for (String str : lineList) {

			// まずは商品群のタグを見つけにいく
			if (str.matches(Constants.PRODUCT_GROUP_TAG)) {
				
//				System.out.println("商品群タグを含む1行：" + str);
//				System.out.println("例外フラグ：" + exceptionflag);

				// 商品名のタグが含まれてなければ複数行になっている例外パターンなので、
				// 文字列連結開始&例外フラグをtrueに&次の行に進む
				if (str.indexOf(Constants.PRODUCT_NAME_TAG) == -1 && !exceptionflag) {
					sbName.append(str);
					sbTotalScore.append(str);
					sbReview.append(str);
					exceptionflag = true;
					continue;
				} else {

					// 例外フラグがtrueの時に、その時点の例外用文字列をリストに追加
					if (exceptionflag) {

						Product product = new Product();

						mName = pName.matcher(sbName.toString());
						mTotalScore = pTotalScore.matcher(sbTotalScore.toString());
						mReview = pReview.matcher(sbReview.toString());
						if (mName.find()) {
							product.setName(mName.group()
									.replaceAll("<span class=\"a-size-base-plus a-color-base a-text-normal\">|</span>",
											""));
						}
						if (mTotalScore.find()) {
							product.setTotalScore(mTotalScore.group()
									.replaceAll("<span class=\\\"a-icon-alt\\\">5つ星のうち|</span>", ""));
						}
						if (mReview.find()) {
							product.setDetailUrl(mReview.group()
									.replaceAll(
											"<a class=\"a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal\".*?href=\"|\">|amp;",
											""));
						}
						sbName = new StringBuilder();
						sbTotalScore = new StringBuilder();
						sbReview = new StringBuilder();
						exceptionflag = false;
						
						// レビュー値がnullの場合「レビュー無し」をset
					    if (product.getTotalScore() == null) {
					    	product.setTotalScore(Constants.NO_REVIEW);
					    }

						productList.add(product);
					}
					
					// 商品名のタグが含まれてなければ複数行になっている例外パターンなので、
					// 文字列連結開始&例外フラグをtrueに&次の行に進む
					if (str.indexOf(Constants.PRODUCT_NAME_TAG) == -1 && !exceptionflag) {
						sbName.append(str);
						sbTotalScore.append(str);
						sbReview.append(str);
						exceptionflag = true;
						continue;
					} else {

						Product product = new Product();
	
						// 商品名のタグが含まれていればそのまま商品名取得
						mName = pName.matcher(str);
						if (mName.find()) {
							product.setName(mName.group()
									.replaceAll("<span class=\"a-size-base-plus a-color-base a-text-normal\">|</span>",
											""));
						}
	
						// レビュー値のタグが含まれていればそのままレビュー値取得
						mTotalScore = pTotalScore.matcher(str);
						if (mTotalScore.find()) {
							product.setTotalScore(mTotalScore.group()
									.replaceAll("<span class=\\\"a-icon-alt\\\">5つ星のうち|</span>", ""));
						}
	
						// 商品口コミのURLへのタグが含まれていればそのまま取得
						mReview = pReview.matcher(str);
						if (mReview.find()) {
							product.setDetailUrl(mReview.group()
									.replaceAll(
											"<a class=\"a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal\".*?href=\"|\">|amp;",
											""));
						}
						
						// レビュー値がnullの場合「レビュー無し」をset
					    if (product.getTotalScore() == null) {
					    	product.setTotalScore(Constants.NO_REVIEW);
					    }
	
						productList.add(product);
					
					}

				}
				// 次の商品群タグが来るまでsbに追加することで他の行と同じ1行を作成
			} else if (exceptionflag) {
				sbName.append(str);
				sbTotalScore.append(str);
				sbReview.append(str);
			}
		}

		return productList;

	}
	
	// 各商品のレビューを取得
	public ArrayList<Product> getProductReviewList(ArrayList<Product> productList){
		
		// headers用Mapの宣言
		Map<String, String> headers = new HashMap<String, String>();
		// Amazon用通信先User-Agentを設定
		headers.put(HttpClientConstants.USER_AGENT_KEY, HttpClientConstants.USER_AGENT_VALUE);
		
		for (Product product : productList) {
			
			// http通信を行い、通信先ページのhttpのリストを取得
			HttpClient httpClient = new HttpClient();
			
			// Amazon商品詳細用URLの共通部分
	        String urlStr = "";
	        urlStr = PropertiesUtil.getProperties(Constants.AMAZON_URL);
			
			// Amazon商品詳細用URLの可変部分（各商品で変わる）
			String urlParam = product.getDetailUrl();
			
			// レビュー値が「レビュー無し」の場合、以下の工程スキップ（口コミを取りに行く必要が無い）
			if (product.getTotalScore() == Constants.NO_REVIEW) {
				continue;
			}
			
			ArrayList<String> lineDetailList = new ArrayList<String>();
			lineDetailList = httpClient.doGet(urlStr, urlParam, headers);
			
			boolean reviewFlag = false;
			Pattern pReview = Pattern.compile(Constants.PRODUCT_REVIEW_TAG);
			Matcher mReview = null;
			Integer reviewCount = 0;
			
			ArrayList<ProductReview> reviewList = new ArrayList<ProductReview>();
			
			// 商品詳細ページのHTMLを1行ずつ見ていく
			for (String line : lineDetailList) {
				
				// 口コミのタグを見つけにいく
				if (line.matches(Constants.PRODUCT_REVIEW_GROUP_TAG)) {
					reviewFlag = true;
					continue;
				}
				
				// レビューフラグがtrueの時に、次に出現するspanタグの行を取得し、中身をリストに格納
				if (reviewFlag) {
					
					ProductReview productReview = new ProductReview();
					
					mReview = pReview.matcher(line);
					if (mReview.find()) {
						// <br />が存在していれば削除
						if (mReview.group().contains(Constants.BR_TAG)) {
							productReview.setReview(mReview.group().replaceAll("<span>|</span>", "").replaceAll(Constants.BR_TAG, ""));
						} else {
							productReview.setReview(mReview.group().replaceAll("<span>|</span>", ""));
						}
						reviewList.add(productReview);
						reviewFlag = false;
						reviewCount = reviewCount + 1;
						// レビューは最大5件までの取得とする
						if (reviewCount == 5) {
							product.setReviewList(reviewList);
							break;
						}
					}
				}
				
			}
			
		}
		
		return productList;
		
	}

}
