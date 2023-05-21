package alekseybykov.portfolio.appletandservlet.applet.components.format;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Aleksey Bykov
 * @since 21.04.2023
 */
@RequiredArgsConstructor
public enum Format {

	PDF("PDF", ".pdf"),
	RTF("RTF", ".rtf"),
	HTML("HTML", ".html"),
	EXCEL("EXCEL", ".xls"),

	TXT("TXT", ".txt");

	@Getter
	private final String name;
	@Getter
	private final String extension;

	public static Format findFormatByName(String formatName) {
		for (Format format : values()) {
			if (StringUtils.equalsIgnoreCase(format.getName(), formatName)) {
				return format;
			}
		}

		throw new IllegalArgumentException("Формат не поддерживается.");
	}

	@Override
	public String toString() {
		return name;
	}
}
