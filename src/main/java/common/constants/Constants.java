package common.constants;

/**
 * 
 * 本プロジェクトで使用する定数クラス
 * 
 *
 */

public class Constants {
	
	public static final String UTF_8                           = "UTF-8";
	
	public static final String AMAZON_URL                     = "AmazonURL";
	
	public static final String AMAZON_URL_SEARCH             = "AmazonURL_SEARCH";
	
	public static final String PRODUCT_GROUP_TAG             = "[ \\t\\n\\x0B\\f\\r]*<div data-asin=\".+\" data-index=\".+\" .*>";
	
	public static final String PRODUCT_NAME_TAG              = "<span class=\\\"a-size-base-plus.*?</span>";
	
	public static final String PRODUCT_STAR_TAG              = "<span class=\"a-icon-alt\">5つ星のうち.*?</span>";
	
	public static final String PRODUCT_REVIEWURL_TAG        = "<a class=\"a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal\".*?>";
	
	public static final String PRODUCT_REVIEW_GROUP_TAG     = ".*?<div data-hook=\\\"review-collapsed\\\".*?>.*?";
	
	public static final String PRODUCT_REVIEW_TAG            = "<span>.*?</span>";
	
	public static final String PROPERTIES_PATH               = "C:\\work\\properties\\sample.properties";
	
	public static final String NO_REVIEW                      = "レビュー無し";
	
	public static final String BR_TAG                          ="<br />";

}