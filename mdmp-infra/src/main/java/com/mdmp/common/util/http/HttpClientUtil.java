package com.mdmp.common.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.mdmp.common.MDMPConstants;
import com.mdmp.common.util.Context;
import com.mdmp.common.util.JsonUtils;

public class HttpClientUtil {

	static Context context = new Context();
	static String appName = context.get("app.name");

	private HttpClientUtil() {
	}

	public static HttpClient getDefaultHttpClient() {
		HttpClientFactory factory = HttpClientFactory.getInstance();
		HttpClientConfig config = HttpClientConfig.getDefaultConfig();
		if (timeout > 0) {
			config.setConnectionTimeout(timeout);
			config.setSoTimeout(timeout);
		}
		return factory.createHttpClient(config);
	}

	public static String getStringFromStream(InputStream input) {
		String body = null;
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream(1024);
			byte[] bb = new byte[1024];
			int len = 0;
			while ((len = input.read(bb)) > 0) {
				bao.write(bb, 0, len);
			}
			body = new String(bao.toByteArray());
		} catch (Exception e) {
			body = "";
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return body;
	}

	public static void closeResponse(HttpResponse response) throws IOException {
		if (null != response && null != response.getEntity()) {
			response.getEntity().consumeContent();
		}
	}

	private static RequestResponse request(HttpRequestBase method,
			Map<String, String> header, String content) throws Exception {
		populateParameterAndContent(method, header, content);
		RequestResponse rr = new RequestResponse();
		HttpClient client = HttpClientUtil.getDefaultHttpClient();
		HttpResponse response = client.execute(method);

		StatusLine statusLine = response.getStatusLine();
		int status = statusLine.getStatusCode();
		rr.setStatCode(status);
		HttpEntity en = response.getEntity();
		String responseStr = HttpClientUtil
				.getStringFromStream(en.getContent());
		rr.setContent(responseStr);
		return rr;
	}

	public static InputStream requestStream(String url,
			Map<String, String> header, String content, boolean expectSucc)
			throws Exception {
		HttpPost method = new HttpPost(url);
		populateParameterAndContent(method, header, content);

		HttpClient client = HttpClientUtil.getDefaultHttpClient();
		HttpResponse response = client.execute(method);
		HttpEntity en = response.getEntity();

		return en.getContent();
	}

	private static void populateParameterAndContent(HttpRequestBase method,
			Map<String, String> header, String content) throws Exception {
		if (header != null && header.size() > 0) {
			String stayAtHeader = context.get("token.in.header", "true");
			boolean shouldConcat = false;
			if (stayAtHeader.equals("true")) {
				method.addHeader(MDMPConstants.USER_TOKEN,
						header.get(MDMPConstants.USER_TOKEN));
			} else {
				method.setURI(new URI(method.getURI() + "?"
						+ MDMPConstants.USER_TOKEN + "="
						+ header.get(MDMPConstants.USER_TOKEN)));
				shouldConcat = true;
			}

			if (header.containsKey(MDMPConstants.FILTER)
					&& header.get(MDMPConstants.FILTER) != null) {
				if (shouldConcat) {
					method.setURI(new URI(method.getURI() + "&"
							+ MDMPConstants.FILTER + "="
							+ header.get(MDMPConstants.FILTER)));
				} else {
					method.setURI(new URI(method.getURI() + "?"
							+ MDMPConstants.FILTER + "="
							+ header.get(MDMPConstants.FILTER)));
				}

				header.remove(MDMPConstants.FILTER);
			}

			if (header.containsKey(MDMPConstants.SQL)
					&& header.get(MDMPConstants.SQL) != null) {
				HttpEntityEnclosingRequestBase entityRequestBase = (HttpEntityEnclosingRequestBase) method;
				AbstractHttpEntity reqEntity = new StringEntity(
						header.get(MDMPConstants.SQL));
				entityRequestBase.setEntity(reqEntity);

				header.remove(MDMPConstants.SQL);
			}

			if (header.size() > 0) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();

				for (Map.Entry<String, String> entry : header.entrySet()) {
					if (!entry.getKey().equals(MDMPConstants.USER_TOKEN)
							&& !entry.getKey().equals(MDMPConstants.SQL)) {
						list.add(new BasicNameValuePair(entry.getKey(), entry
								.getValue()));
					}
				}

				if (list.size() > 0) {
					HttpPost post = (HttpPost) method;
					post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
				}
			}
		}

		if (content != null && !content.trim().isEmpty()) {
			HttpEntityEnclosingRequestBase entityRequestBase = (HttpEntityEnclosingRequestBase) method;
			AbstractHttpEntity reqEntity = new StringEntity(content);
			entityRequestBase.setEntity(reqEntity);
		}

		// method.addHeader(APIConstants.APP_NAME, appName);
		// method.addHeader(APIConstants.APP_TOKEN,
		// AppTokenUtil.makeToken(appName));
	}

	public static RequestResponse requestPost(String url,
			Map<String, String> header, String content) throws Exception {
		return request(new HttpPost(url), header, content);
	}

	public static RequestResponse requestPost(String url,
			Map<String, String> header) throws Exception {
		return request(new HttpPost(url), header, null);
	}

	public static RequestResponse requestPut(String url,
			Map<String, String> header, String content) throws Exception {
		return request(new HttpPut(url), header, content);
	}

	public static RequestResponse requestGet(String url,
			Map<String, String> header) throws Exception {
		return request(new HttpGet(url), header, null);
	}

	public static RequestResponse requestDelete(String url,
			Map<String, String> header) throws Exception {
		return request(new HttpDelete(url), header, null);
	}

	private static int timeout = -1;
	private static int DEFAULT_TIMEOUT = 3000;

	public static void setTimeout(int to) {
		timeout = to;
	}

	public static <T> T request(String uri, String method, Object original,
			Map<String, String> header, Class<T> clazz) throws Exception {
		RequestResponse result = null;

		if (original instanceof String) {
			result = getResult(uri, method, (String) original, header);
		} else {
			result = getResult(uri, method, JsonUtils.convertFrom(original),
					header);
		}

		if (clazz == null || result == null
				|| result.getContent().trim().isEmpty()) {
			return null;
		}

		return JsonUtils.convertFrom(result.getContent(), clazz);
	}

	public static <T> List<T> requestList(String uri, String method,
			String content, Map<String, String> header, Class<T> clazz)
			throws Exception {
		RequestResponse result = getResult(uri, method, content, header);
		return JsonUtils.convertToList(result.getContent(), clazz);
	}

	public static RequestResponse getResult(String uri, String method,
			String content, Map<String, String> header)
			throws Exception {
		RequestResponse result = null;
		if (method.equalsIgnoreCase("POST")) {
			result = HttpClientUtil.requestPost(uri, header, content);
		} else if (method.equalsIgnoreCase("GET")) {
			result = HttpClientUtil.requestGet(uri, header);
		} else if (method.equalsIgnoreCase("PUT")) {
			result = HttpClientUtil.requestPut(uri, header, content);
		} else {
			result = HttpClientUtil.requestDelete(uri, header);
		}

		return result;
	}
}
