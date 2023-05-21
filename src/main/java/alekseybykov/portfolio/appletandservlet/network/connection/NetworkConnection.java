package alekseybykov.portfolio.appletandservlet.network.connection;

import alekseybykov.portfolio.appletandservlet.network.connection.consts.NetworkProtocol;
import alekseybykov.portfolio.appletandservlet.network.connection.consts.RequestMethod;
import alekseybykov.portfolio.appletandservlet.network.connection.exceptions.UnsupportedProtocolException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;

/**
 * Класс устанавливает соединение с сетевым ресурсом.
 *
 * @author Aleksey Bykov
 * @since 27.04.2023
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkConnection {

	/**
	 * URL внешнего ресурса, с которым устанавливается соединение.
	 */
	private URL url;

	/**
	 * Таймаут на считывание ответа сервлета, ms.
	 * Если считывание ответа не началось за указанное время,
	 * выбрасывается исключение java.net.SocketTimeoutException.
	 */
	private int readTimeout;

	/**
	 * Таймаут на соединение с сервлетом, ms.
	 * Если соединение не установлено за указанное время,
	 * выбрасывается исключение java.net.SocketTimeoutException.
	 */
	private int connectTimeout;

	public static NetworkConnection createConnection(URL url, int readTimeout, int connectTimeout) {
		return new NetworkConnection(url, readTimeout, connectTimeout);
	}

	/**
	 * Метод открывает соединение с ресурсом, указанным в {@link NetworkConnection#url}.
	 * В зависимости от протокола, создается защищенное или незащищенное соединение.
	 *
	 * @return - объект соединения с ресурсом.
	 */
	@SneakyThrows
	public URLConnection connect() {
		val protocol = url.getProtocol();
		if (StringUtils.equalsIgnoreCase(NetworkProtocol.SECURED.getName(), protocol)) {
			return getSecuredConnection();
		} else if (StringUtils.equalsIgnoreCase(NetworkProtocol.NOT_SECURED.getName(), protocol)) {
			return getNotSecuredConnection();
		}

		throw new UnsupportedProtocolException("Неподдерживаемый протокол: " + protocol);
	}

	/**
	 * Метод устанавливает незащищенное соединение с ресурсом,
	 * указанным в {@link NetworkConnection#url}.
	 *
	 * @return - объект незащищенного соединения.
	 */
	@SneakyThrows
	private URLConnection getNotSecuredConnection() {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		connection.setRequestMethod(RequestMethod.POST.toString());
		connection.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());

		connection.setConnectTimeout(connectTimeout);
		connection.setReadTimeout(readTimeout);

		return connection;
	}

	/**
	 * Метод устанавливает защищенное соединение с ресурсом,
	 * указанным в {@link NetworkConnection#url}.
	 *
	 * @return - объект защищенного соединения.
	 */
	@SneakyThrows
	private HttpsURLConnection getSecuredConnection() {
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setHostnameVerifier(createDefaultHostNameVerifier());
		connection.setSSLSocketFactory(createEmptySSLContext().getSocketFactory());

		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		connection.setRequestMethod(RequestMethod.POST.toString());
		connection.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());

		connection.setConnectTimeout(connectTimeout);
		connection.setReadTimeout(readTimeout);

		return connection;
	}

	/**
	 * Метод формирует {@link TrustManager}, разрешающий любые сертификаты сервера.
	 *
	 * Не будем настраивать SSL на сервере, подлинность сертификата не проверяем.
	 * В handshake далее разрешаются любые сертификаты.
	 * Т.о., просто исключается ошибка: PKIX path building failed: ...unable to find valid certification path to requested target".
	 *
	 * @return - TrustManager, разрешающий любые сертификаты сервера.
	 */
	private TrustManager[] doAnyCertificateHandshake() {
		return new TrustManager[]{new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		}};
	}

	/**
	 * Метод создает верификатор имени хоста по-умолчанию,
	 * поскольку в {@link NetworkConnection#doAnyCertificateHandshake()}
	 * не выполняется проверка SSL сертификата сервера.
	 *
	 * @return - верификатор хоста по умолчанию.
	 */
	private HostnameVerifier createDefaultHostNameVerifier() {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostName, SSLSession sslSession) {
				return StringUtils.equals(hostName, sslSession.getPeerHost());
			}
		};
	}

	/**
	 * Метод создает пустой SSL контекст,
	 * поскольку в {@link NetworkConnection#doAnyCertificateHandshake()}
	 * не выполняется проверка SSL сертификата.
	 *
	 * @return - пустой SSL контекст.
	 */
	@SneakyThrows
	private SSLContext createEmptySSLContext() {
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(null, doAnyCertificateHandshake(), null);

		return ctx;
	}
}
