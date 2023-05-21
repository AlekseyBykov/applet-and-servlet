package alekseybykov.portfolio.appletandservlet.applet.components.logger;

/**
 * Интерфейс слушателя модели {@link LoggerModel}.
 *
 * @author Aleksey Bykov
 * @since 18.05.2023
 */
public interface LoggerObserver {

	void logDataChanged(String logContent);
}
