package Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import common.constants.HttpClientConstants;

public class HttpClient {

	/** GETメソッド */
	private static final String METHOD_GET = "GET";

	/** POSTメソッド */
	private static final String METHOD_POST = "POST";

	private String charset;

	/**
	 * コンストラクタ
	 */
	public HttpClient() {
		this.charset = "UTF-8";
	}

	public HttpClient(String charset) {
		this.charset = charset;
	}

	/**
	 * HTTP通信(GET)
	 */
	public ArrayList<String> doGet(String urlStr, String urlParam, Map<String, String> headers) {
		return request(urlStr, urlParam, headers, null, METHOD_GET);
	}

	/**
	 * HTTP通信(POST)
	 */
	public ArrayList<String> doPost(String urlStr, String urlParam,
			Map<String, String> headers, Map<String, String> params) {
		return request(urlStr, urlParam, headers, params, METHOD_POST);
	}

	/**
	 * HTTP通信(POST用のparamについての実装はまだ)
	 * 
	 */
	private ArrayList<String> request(String urlStr, String urlParam,
			Map<String, String> headers, Map<String, String> params,
			String method) {

		HttpURLConnection connection = null;
		InputStream is = null;
		ArrayList<String> lineList = new ArrayList<String>();

		try {

			URL url = new URL(urlStr + urlParam);

			// コネクションをオープン
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty(HttpClientConstants.USER_AGENT_KEY, HttpClientConstants.USER_AGENT_VALUE);

			// レスポンスが来た場合は処理続行
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

				is = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, charset); // 指定したストリームを、指定した文字コードで構成されるテキストファイルとして読み込み
				BufferedReader br = new BufferedReader(isr); //テキストファイルを読み込むためのクラス

				String line = null;
				while ((line = br.readLine()) != null) {
					lineList.add(line);
				}

			} else {
				Exception e = new IOException();
				throw e;
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return lineList;
	}
	

	//	/**
	//	 * HTTP通信
	//	 * 
	//	 * @param urlStr  URL
	//	 * @param headers リクエストヘッダー
	//	 * @param params  リクエストパラメータ
	//	 * @param method  POST or GET
	//	 * @return レスポンス文字列
	//	 * @return
	//	 * @throws SystemException 
	//	 */
	//	private HttpClinetResultBean request(String urlStr, Map<String, String> headers, Map<String, String> params,
	//			String method) throws SystemException {
	//		HttpClinetResultBean bean = null;
	//		HttpURLConnection con = null;
	//		BufferedInputStream in = null;
	//		BufferedReader br = null;
	//
	//		try {
	//			// HttpClientResultBeanを生成する
	//			bean = new HttpClinetResultBean();
	//			bean.setUrl(urlStr);
	//			bean.setMethod(method);
	//			bean.setHeaders(headers);
	//			bean.setParams(params);
	//
	//			// CookieHandler
	//			CookieManager manager = new CookieManager();
	//			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
	//			CookieHandler.setDefault(manager);
	//
	//			// コネクションを作成する
	//			con = createRequestMethodConnection(urlStr, headers, params, method);
	//
	//			// HTTP通信
	//			in = new BufferedInputStream(con.getInputStream());
	//			// レスポンスを取得する
	//			br = new BufferedReader(new InputStreamReader(in, charset));
	//
	//			StringBuilder response = new StringBuilder();
	//			String line;
	//			while ((line = br.readLine()) != null) {
	//				response.append(line);
	//			}
	//
	//			// Cookie取得
	//			Map<String, String> map = new LinkedHashMap<>();
	//			List<HttpCookie> cookies = manager.getCookieStore().getCookies();
	//			if (cookies != null && !cookies.isEmpty()) {
	//				for (HttpCookie cookie : cookies) {
	//					map.put(cookie.getName(), cookie.getValue());
	//				}
	//			}
	//
	//			bean.setCookies(map);
	//			bean.setStatusCode(con.getResponseCode());
	//			bean.setResponse(response.toString());
	//
	//		} catch (IOException e) {
	//			try {
	//				// エラー時のHTTPステータスコードを設定する
	//				bean.setStatusCode(con.getResponseCode());
	//				bean.setResponse(con.getResponseMessage());
	//			} catch (IOException e1) {
	//				throw new SystemException(e);
	//			}
	//
	//			throw new SystemException(e);
	//		} finally {
	//			try {
	//				if (br != null) {
	//					br.close();
	//				}
	//
	//				if (con != null) {
	//					con.disconnect();
	//				}
	//
	//				logger.debug(bean);
	//
	//			} catch (IOException e) {
	//				throw new SystemException(e);
	//			}
	//		}
	//		return bean;
	//	}
	//
	//	/**
	//	 * コネクション作成
	//	 * 
	//	 * @param urlStr  URL
	//	 * @param headers リクエストヘッダー
	//	 * @param params  リクエストパラメータ
	//	 * @param method  POST or GET
	//	 * @return コネクション
	//	 * @throws IOException
	//	 * @throws SystemException 
	//	 */
	//	private HttpURLConnection createRequestMethodConnection(String urlStr, Map<String, String> headers,
	//			Map<String, String> params, String method) throws SystemException {
	//		HttpURLConnection con = null;
	//
	//		try {
	//			con = (HttpURLConnection) new URL(urlStr).openConnection();
	//			con.setRequestMethod(method);
	//			con.setConnectTimeout(18000);
	//			con.setReadTimeout(18000);
	//
	//			// リクエストヘッダー設定
	//			if (headers != null) {
	//				for (Entry<String, String> entry : headers.entrySet()) {
	//					con.setRequestProperty(entry.getKey(), entry.getValue());
	//				}
	//			}
	//
	//			// リクエストパラメータ設定
	//			if (METHOD_POST.equals(method) && params != null) {
	//				con.setDoOutput(true);
	//				OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
	//				osw.write(createRequestParameter(params));
	//				osw.close();
	//			}
	//
	//		} catch (IOException e) {
	//			throw new SystemException(e);
	//		}
	//		return con;
	//	}

}
