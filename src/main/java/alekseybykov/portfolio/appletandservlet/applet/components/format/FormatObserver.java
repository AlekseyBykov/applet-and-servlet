package alekseybykov.portfolio.appletandservlet.applet.components.format;

/**
 * Интерфейс слушателя модели {@link FormatChooserModel}.
 *
 * @author Aleksey Bykov
 * @since 17.05.2023
 */
public interface FormatObserver {

	void formatChanged(Format format);
}
