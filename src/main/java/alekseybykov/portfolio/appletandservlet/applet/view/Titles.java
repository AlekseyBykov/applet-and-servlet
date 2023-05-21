package alekseybykov.portfolio.appletandservlet.applet.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Aleksey Bykov
 * @since 18.04.2023
 */
@RequiredArgsConstructor
public enum Titles {

	APPLICATION_NAME("Java Applet (JWS)"),
	APPLICATION_VERSION("v1.0");

	@Getter
	private final String title;
}
