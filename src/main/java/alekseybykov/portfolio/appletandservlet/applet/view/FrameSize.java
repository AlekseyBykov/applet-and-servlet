package alekseybykov.portfolio.appletandservlet.applet.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Aleksey Bykov
 * @since 17.04.2023
 */
@RequiredArgsConstructor
public enum FrameSize {

	HEIGHT(500),
	WIDTH(900);

	@Getter
	private final int value;
}
