package service.beans;

import java.util.ArrayList;

public class Product {
	
	private String name;	                      // 名前
	private String totalScore;                   // 星の数(総合評価値)
	private String detailUrl;	                  // 商品詳細URL
	private ArrayList<ProductReview> reviewList; // 口コミリスト
	
	public Product() {} //デフォルトコンストラクタ

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public ArrayList<ProductReview> getReviewList() {
		return reviewList;
	}

	public void setReviewList(ArrayList<ProductReview> reviewList) {
		this.reviewList = reviewList;
	}

}
