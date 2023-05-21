package alekseybykov.portfolio.appletandservlet.network.connection;

import alekseybykov.portfolio.appletandservlet.network.connection.consts.Timeouts;
import alekseybykov.portfolio.appletandservlet.network.jnlp.CodebaseLocator;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Aleksey Bykov
 * @since 18.04.2023
 */
public class ServletConnection {

	private final CodebaseLocator codebaseLocator;

	public ServletConnection(CodebaseLocator codebaseLocator) {
		this.codebaseLocator = codebaseLocator;
	}

	public URLConnection getConfiguredServletConnection() throws IOException {
		URL servletUrl = new URL(codebaseLocator.locate(), "/EchoServlet/echo");

		NetworkConnection networkConnection = NetworkConnection.createConnection(
				servletUrl, Timeouts.CONNECT_TIMEOUT.getValue(), Timeouts.READ_TIMEOUT.getValue());

		return networkConnection.connect();
	}
}
