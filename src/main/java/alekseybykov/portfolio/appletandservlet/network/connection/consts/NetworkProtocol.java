package alekseybykov.portfolio.appletandservlet.network.connection.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Aleksey Bykov
 * @since 26.04.2023
 */
@AllArgsConstructor
public enum NetworkProtocol {

	SECURED("https"),
	NOT_SECURED("http");

	@Getter
	private final String name;
}
