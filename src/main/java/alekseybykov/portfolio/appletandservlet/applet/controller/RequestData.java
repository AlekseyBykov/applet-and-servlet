package alekseybykov.portfolio.appletandservlet.applet.controller;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Aleksey Bykov
 * @since 20.04.2023
 */
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestData {

	private final Properties props = new Properties();

	public static RequestData create() {
		return new RequestData();
	}

	public void setRequestParam(String name, String value) {
		props.put(name, value);
	}

	public String getRequestParam(String name) {
		return props.getProperty(name);
	}

	public String toUrlEncodedString() {
		return new HttpRequestDataEncoder().encode(this);
	}

	public RequestData fromUrlEncodedString(String urlEncodedString) {
		return new HttpRequestDataDecoder().decode(urlEncodedString);
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	private static class HttpRequestDataEncoder {
		@SneakyThrows
		public String encode(RequestData requestData) {
			Enumeration names = requestData.props.propertyNames();
			StringBuffer buf = new StringBuffer();
			while (names.hasMoreElements()) {
				val name = (String) names.nextElement();
				val value = requestData.props.getProperty(name);
				buf.append(URLEncoder.encode(name, StandardCharsets.UTF_8.name())
				           + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8.name()));

				if (names.hasMoreElements()) {
					buf.append("&");
				}
			}
			return buf.toString();
		}
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	private static class HttpRequestDataDecoder {
		@SneakyThrows
		public RequestData decode(String urlEncodedString) {
			RequestData requestData = RequestData.create();
			val pairs = urlEncodedString.split("&");
			for (val pair : pairs) {
				val idx = pair.indexOf("=");
				requestData.setRequestParam(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8.name()),
						URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8.name()));
			}

			return requestData;
		}
	}
}
