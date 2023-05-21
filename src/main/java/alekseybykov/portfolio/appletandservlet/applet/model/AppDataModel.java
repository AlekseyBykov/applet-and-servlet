package alekseybykov.portfolio.appletandservlet.applet.model;

import alekseybykov.portfolio.appletandservlet.applet.components.format.Format;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Aleksey Bykov
 * @since 18.05.2023
 */
@Getter
@Setter
public class AppDataModel {

	private Format format;
	private String counter;
}
