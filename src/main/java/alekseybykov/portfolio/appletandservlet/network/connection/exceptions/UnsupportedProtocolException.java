package alekseybykov.portfolio.appletandservlet.network.connection.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author Aleksey Bykov
 * @since 27.04.2023
 */
@NoArgsConstructor
public class UnsupportedProtocolException extends RuntimeException {

	public UnsupportedProtocolException(String message) {
		super(message);
	}
}
