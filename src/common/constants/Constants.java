package common.constants;

/**
 * 
 * ビジネスロジックで使用する定数クラス
 * 
 *
 */

public class Constants {
	
//	public static final String AmazonURL                = "https://www.amazon.co.jp";
//	
//	public static final String AmazonURL_SEARCH        = "/s?k=";
	
	public static final String PRODUCT_GROUP_TAG       = "[ \\t\\n\\x0B\\f\\r]*<div data-asin=\".+\" data-index=\".+\" .*>";
	
	public static final String PRODUCT_NAME_TAG        = "<span class=\\\"a-size-base-plus.*?</span>";
	
	public static final String PRODUCT_STAR_TAG        = "<span class=\"a-icon-alt\">5つ星のうち.*?</span>";
	
	public static final String PRODUCT_REVIEWURL_TAG  = "<a class=\"a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal\".*?>";
	
	public static final String PRODUCT_REVIEW_GROUP_TAG      = ".*?<div data-hook=\\\"review-collapsed\\\".*?>.*?";
	
	public static final String PRODUCT_REVIEW_TAG          ="<span>.*?</span>";
	
	public static final String PROPERTIES_PASS          ="C:\\work\\properties\\sample.properties";

}