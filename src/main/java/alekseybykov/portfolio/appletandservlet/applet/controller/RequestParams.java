package alekseybykov.portfolio.appletandservlet.applet.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Aleksey Bykov
 * @since 21.04.2023
 */
@RequiredArgsConstructor
public enum RequestParams {

	FORMAT_PARAM("format"),
	COUNT_PARAM("count");

	@Getter
	private final String name;
}
