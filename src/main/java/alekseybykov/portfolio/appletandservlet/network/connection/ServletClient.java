package alekseybykov.portfolio.appletandservlet.network.connection;

import alekseybykov.portfolio.appletandservlet.applet.controller.RequestData;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.net.URLConnection;

/**
 * @author Aleksey Bykov
 * @since 18.04.2023
 */
public class ServletClient {

	private final ServletConnection servletConnection;

	public ServletClient(ServletConnection servletConnection) {
		this.servletConnection = servletConnection;
	}

	// Локальный запрос будет отправлен на сервлет по адресу http://localhost:8080/AppletAndServlet/echo
	@SneakyThrows
	public String sendRequest(RequestData requestData) {
		URLConnection connection = servletConnection.getConfiguredServletConnection();
		try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
			out.write(requestData.toUrlEncodedString());
			out.flush();

			return getResponse(connection);
		}
	}

	@SneakyThrows
	private String getResponse(URLConnection connection) {
		try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream())) {
			int character;
			StringBuffer sb = new StringBuffer();
			while ((character = in.read()) != NumberUtils.INTEGER_MINUS_ONE) {
				sb.append((char) character);
			}

			return sb.toString();
		}
	}
}
