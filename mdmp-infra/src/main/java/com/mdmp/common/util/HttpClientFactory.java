package com.mdmp.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientFactory {

	private static final String PREEMPTIVE_AUTH = "preemptive-auth";

	private static final String ACCEPT_ENCODING = "Accept-Encoding";

	private static final long IDLE_TIME = 10 * 1000;

	private static final String GZIP = "gzip";

	private static Logger log = LoggerFactory
			.getLogger(HttpClientFactory.class);

	private static HttpClientFactory instance = new HttpClientFactory();

	public static HttpClientFactory getInstance() {
		return instance;
	}

	private HttpClientFactory() {
	}

	private HttpClientConfig defaultHttpClientConfig = HttpClientConfig
			.getDefaultConfig();

	/**
	 * 
	 * @return HttpClient uses the default config.
	 */
	public HttpClient createHttpClient() {
		return createHttpClient(defaultHttpClientConfig);
	}

	public HttpClient createHttpClient(HttpClientConfig config) {
		HttpParams params = getHttpParams(config);
		ClientConnectionManager cm = getThreadSafeConnectionManager(params,
				config);
		AbstractHttpClient client = new DefaultHttpClient(cm, params);
		if (config.isAuth()) {
			enableAuthentication(client, config);
		}
		if (config.isGZip()) {
			enableGZip(client, config);
		}
		setKeepAliveStrategy(client, config);

		log.info("Create HttpClient with the config: " + config);

		return client;
	}

	private static void setKeepAliveStrategy(AbstractHttpClient _client,
			HttpClientConfig config) {
		_client.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response,
					HttpContext context) {
				if (response == null) {
					throw new IllegalArgumentException(
							"HTTP response may not be null");
				}
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						try {
							return Long.parseLong(value) * 1000;
						} catch (NumberFormatException ignore) {
						}
					}
				}
				return IDLE_TIME;
			}

		});
	}

	/**
	 * Enable Authentication, Get Credentials from config's credProvider
	 */
	protected void enableAuthentication(AbstractHttpClient _client,
			HttpClientConfig config) {
		_client.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest request, HttpContext context)
					throws HttpException, IOException {
				AuthState authState = (AuthState) context
						.getAttribute(ClientContext.TARGET_AUTH_STATE);
				if (authState.getAuthScheme() == null) {
					// Get AuthScheme from the context
					AuthSchemeRegistry authSchemeRegistry = (AuthSchemeRegistry) context
							.getAttribute(ClientContext.AUTHSCHEME_REGISTRY);
					AuthScheme authScheme = authSchemeRegistry.getAuthScheme(
							PREEMPTIVE_AUTH, null);

					// Get CredentialsProvider
					CredentialsProvider credsProvider = (CredentialsProvider) context
							.getAttribute(ClientContext.CREDS_PROVIDER);
					if (authScheme != null) {
						Credentials creds = credsProvider
								.getCredentials(AuthScope.ANY);
						if (creds == null) {
							throw new HttpException(
									"No credentials for preemptive authentication");
						}
						authState.setAuthScheme(authScheme);
						authState.setCredentials(creds);
						// the following code is from HttpClient source
						// code(RequestTargetAuthentication.process) for fix the
						// bug(406666)
						// when concurrent thread is more then 200, HttpClient
						// will lose the Header named "Authorization".
						if (authState.getAuthScope() != null
								|| !authScheme.isConnectionBased()) {
							try {
								request.addHeader(authScheme.authenticate(
										creds, request));
							} catch (AuthenticationException ex) {
								if (log.isErrorEnabled()) {
									log.error("Authentication error: "
											+ ex.getMessage());
								}
							}
						}
					} else {
						log.debug("authScheme is null...........");
					}
				} else {
					log.debug("authState.getAuthScheme() is not null...........");
				}
			}
		}, 0);

		// set credential provider
		_client.setCredentialsProvider(config.getCredProvider());

		AuthSchemeRegistry authSchemeRegistry = new AuthSchemeRegistry();
		authSchemeRegistry.register(PREEMPTIVE_AUTH, new BasicSchemeFactory());

		_client.setAuthSchemes(authSchemeRegistry);

	}

	/**
	 * Enable GZip, Add gzip in http header, wrap response entity's content
	 */
	protected void enableGZip(AbstractHttpClient _client,
			HttpClientConfig config) {
		_client.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest request, HttpContext context)
					throws HttpException, IOException {
				if (!request.containsHeader(ACCEPT_ENCODING)) {
					request.addHeader(ACCEPT_ENCODING, GZIP);
				}
			}
		}, 1);
		_client.addResponseInterceptor(new HttpResponseInterceptor() {
			@Override
			public void process(HttpResponse response, HttpContext context)
					throws HttpException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						HeaderElement[] codecs = ceheader.getElements();
						for (HeaderElement element : codecs) {
							if (element.getName().equalsIgnoreCase(GZIP)) {
								response.setEntity(new GzipDecompressingEntity(
										entity));
								return;
							}
						}
					}
				}
			}
		});
	}

	private static class GzipDecompressingEntity extends HttpEntityWrapper {
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

	/**
	 * @param params
	 * @return
	 */
	protected ClientConnectionManager getThreadSafeConnectionManager(
			HttpParams params, HttpClientConfig config) {
		// Create tcp scheme
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), config.getHttpPort()));
		registry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), config.getHttpsPort()));

		// Use thread safe connection manager
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				registry);
		return cm;
	}

	protected HttpVersion getHttpVersion(int version) {
		switch (version) {
		case 0:
			return HttpVersion.HTTP_1_0;
		case 1:
			return HttpVersion.HTTP_1_1;
		case 9:
			return HttpVersion.HTTP_0_9;
		default:
			return HttpVersion.HTTP_1_1;
		}
	}

	/**
	 * @return HttpParams
	 */
	protected HttpParams getHttpParams(HttpClientConfig config) {
		HttpParams params = new BasicHttpParams();
		// Set http connection params
		HttpConnectionParams.setConnectionTimeout(params,
				config.getConnectionTimeout());
		HttpConnectionParams.setSocketBufferSize(params,
				config.getSocketBufferSize());
		HttpConnectionParams.setSoTimeout(params, config.getSoTimeout());
		HttpConnectionParams.setStaleCheckingEnabled(params,
				config.isStaleCheckingEnabled());
		HttpConnectionParams.setTcpNoDelay(params, config.isTcpNoDelay());

		// set http protocol params

		HttpProtocolParams.setVersion(params,
				getHttpVersion(config.getHttpVersion()));
		HttpProtocolParams
				.setContentCharset(params, config.getContentCharset());
		HttpProtocolParams.setUseExpectContinue(params,
				config.isUseExpectContinue());
		// params.setIntParameter(HttpProtocolParams.WAIT_FOR_CONTINUE, 1);
		HttpProtocolParams.setUserAgent(params, config.getUserAgent());

		// set connection manager params
		ConnManagerParams.setMaxConnectionsPerRoute(params,
				new ConnPerRouteBean(config.getMaxConnectionsPerRoute()));
		// FIXME possible case for Athena Hang issue - If the connection manager
		// (pool) does't have the connection,
		// the connection manager hang the waiting thread and await the timeout
		// Mill
		// managedConn = connRequest.getConnection(timeout,
		// TimeUnit.MILLISECONDS);
		// org.apache.http.impl.conn.tsccm.ConnPoolByRoute.getEntryBlocking(ConnPoolByRoute.java:331)
		ConnManagerParams.setTimeout(params,
				config.getConnectionManagerTimeout());
		ConnManagerParams.setMaxTotalConnections(params,
				config.getMaxTotalConnections());

		// set auth params
		HttpClientParams.setAuthenticating(params, config.isAuth());

		// set auto redirect
		HttpClientParams.setRedirecting(params, config.isAutoRedirect());
		return params;
	}
}
