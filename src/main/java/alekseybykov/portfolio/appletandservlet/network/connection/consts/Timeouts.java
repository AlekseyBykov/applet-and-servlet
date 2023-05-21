package alekseybykov.portfolio.appletandservlet.network.connection.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Aleksey Bykov
 * @since 19.05.2023
 */
@AllArgsConstructor
public enum Timeouts {

	/**
	 * Таймаут на считывание ответа сервлета, ms.
	 * Если считывание ответа не началось за указанное время,
	 * выбрасывается исключение java.net.SocketTimeoutException.
	 */
	READ_TIMEOUT(60_000),

	/**
	 * Таймаут на соединение с сервлетом, ms.
	 * Если соединение не установлено за указанное время,
	 * выбрасывается исключение java.net.SocketTimeoutException.
	 */
	CONNECT_TIMEOUT(30_000);

	@Getter
	private int value;
}
