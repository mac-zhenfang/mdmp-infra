package com.mdmp.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	private final static int SOCKET_BUFFER_SIZE = 1048576;

	public static final String SHARDID_NULL = "empty";

	private static final String DESCRIPTION = "description";

	static final String BINARY_OCTET_STREAM = "binary/octet-stream";

	/**
	 * Close the inputstream when the content has been fully consumed .
	 */
	static class FileStreamEntity extends HttpEntityWrapper {
		public FileStreamEntity(final HttpEntity wrapped) {
			super(wrapped);
		}

		public void writeTo(OutputStream outstream) throws IOException {
			if (outstream == null) {
				throw new IllegalArgumentException(
						"Outputstream mustn't be null!");
			}
			InputStream instream = wrappedEntity.getContent();
			byte[] buffer = new byte[SOCKET_BUFFER_SIZE];
			int l;
			if (wrappedEntity.getContentLength() < 0) {
				while ((l = instream.read(buffer)) != -1) {
					outstream.write(buffer, 0, l);
				}
			} else {
				long remaining = wrappedEntity.getContentLength();
				while (remaining > 0) {
					l = instream.read(buffer, 0,
							(int) Math.min(SOCKET_BUFFER_SIZE, remaining));
					if (l == -1) {
						break;
					}
					outstream.write(buffer, 0, l);
					remaining -= l;
				}
			}
			wrappedEntity.consumeContent();
		}

	}

	static class GzipDecompressingEntity extends HttpEntityWrapper {
		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException,
				IllegalStateException {
			InputStream wrappedin = wrappedEntity.getContent();
			return new GZIPInputStream(wrappedin);
		}

		@Override
		public long getContentLength() {
			return -1;
		}

	}

	public static final int HTTP_METHOD_POST = 0;
	public static final int HTTP_METHOD_PUT = 1;
	public static final int HTTP_METHOD_GET = 2;
	public static final int HTTP_METHOD_DELETE = 3;

	private static HttpRequestBase getRequestBase(String url, int httpMethodType) {
		switch (httpMethodType) {
		case HTTP_METHOD_DELETE:
			return new HttpDelete(url);
		case HTTP_METHOD_GET:
			return new HttpGet(url);
		case HTTP_METHOD_PUT:
			return new HttpPut(url);
		default:
			return new HttpPost(url);
		}
	}

	public static InputStream send(HttpClient client, String uri, Object data,
			Map<String, String> headers, Map<String, String> params,
			int httpMethodType, String httpContentType) throws IOException {
		HttpRequestBase method = getRequestBase(uri, httpMethodType);
		if (null != headers && !headers.isEmpty()) {
			for (String headKey : headers.keySet()) {
				method.addHeader(headKey, headers.get(headKey));
			}
			log.debug("Send: {}#{}", new Object[] { uri, headers.toString() });
		} else {
			log.debug("Send: {}", new Object[] { uri });
		}
		if (null != params) {
			Iterator<Entry<String, String>> entries = params.entrySet()
					.iterator();
			while (entries.hasNext()) {
				Entry<String, String> entry = entries.next();
				method.setHeader(entry.getKey(), entry.getValue());
			}
		}
		if (null != data) {
			HttpEntityEnclosingRequestBase entityRequestBase = (HttpEntityEnclosingRequestBase) method;
			AbstractHttpEntity reqEntity = null;
			if (data instanceof InputStream) {
				InputStream in = (InputStream) data;
				reqEntity = new InputStreamEntity(in, in.available());
				reqEntity.setContentType(HTTP.OCTET_STREAM_TYPE);
				reqEntity.setChunked(false);
				// See {@link AbstractHttpClient}'s 763-769, Ensure that the
				// content has been fully consumed.
				// reqEntity = new FileStreamEntity(reqEntity);
			} else if (data instanceof byte[]) {
				reqEntity = new ByteArrayEntity((byte[]) data);
			} else if (data instanceof Map) {
				Map<String, String> map = (Map<String, String>) data;
				ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
				for (String key : map.keySet()) {
					postParams.add(new BasicNameValuePair(key, map.get(key)));
				}
				reqEntity = new UrlEncodedFormEntity(postParams);
			} else if (data instanceof File) {
				reqEntity = new FileEntity((File) data, null);
			} else if (data instanceof String) {
				reqEntity = new StringEntity((String) data);
			} else if (data instanceof Serializable) {
				reqEntity = new SerializableEntity((Serializable) data, false);
			} else if (data instanceof UrlEncodedFormEntity) {
				reqEntity = (UrlEncodedFormEntity) data;
			} else {
				throw new IOException("The data can't be recognized!");
			}
			if (reqEntity != null) {
				if (httpContentType != null) {
					reqEntity.setContentType(httpContentType);
				}
				entityRequestBase.setEntity(reqEntity);
			}
		}
		return sendRequest(client, method);
	}

	public static InputStream send(HttpClient client, String uri, Object data,
			Map<String, String> params, int httpMethodType,
			String httpContentType) throws IOException {
		return send(client, uri, data, null, params, httpMethodType,
				httpContentType);
	}

	/**
	 * 
	 * Send the HttpRequest, get the response's content
	 * 
	 * @param response
	 * @throws IOException
	 * @throws IOException
	 */
	private static InputStream sendRequest(HttpClient client,
			HttpRequestBase request) throws IOException {
		HttpResponse response = null;
		try {
			response = client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			if (status >= 300) {
				switch (status) {
				case HttpStatus.SC_UNAUTHORIZED:
					throw new IOException(
							"Security check failure, Please check your UserID&Password!");
				case HttpStatus.SC_MOVED_PERMANENTLY:
				case HttpStatus.SC_MOVED_TEMPORARILY:
					Header locationHeader = response.getFirstHeader("Location");
					if (null != locationHeader) {
						String newUri = locationHeader.getValue();
						try {
							request.setURI(new URI(newUri));
						} catch (URISyntaxException e) {
							throw new IOException(String.format(
									"Redirect(%d)'s uri(%s) is illegal!",
									status, newUri), e);
						}
						if (request instanceof HttpEntityEnclosingRequestBase) {
							if (!((HttpEntityEnclosingRequestBase) request)
									.getEntity().isRepeatable()) {
								throw new IOException(
										"Can't re-send the request.");
							}
						}
						closeResponse(response);
						return sendRequest(client, request);
					} else {
						throw new HttpResponseException(
								statusLine.getStatusCode(),
								statusLine.getReasonPhrase());
					}
				default:
					HttpEntity resEntity = response.getEntity();
					InputStream stream = resEntity.getContent();
					//String resault = Tools.inputStreamToString(stream);
					String resault = null;
					throw new HttpResponseException(statusLine.getStatusCode(),
							resault);
				}
			}

			HttpEntity resEntity = response.getEntity();
			InputStream stream = resEntity.getContent();
			return stream;
		} catch (IOException e1) {
			closeResponse(response);
			throw e1;
		}

	}

	private static void closeResponse(HttpResponse response) throws IOException {
		if (null != response && null != response.getEntity()) {
			response.getEntity().consumeContent();
		}
	}
}
