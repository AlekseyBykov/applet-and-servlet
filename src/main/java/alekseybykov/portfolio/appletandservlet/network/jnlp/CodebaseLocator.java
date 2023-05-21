package alekseybykov.portfolio.appletandservlet.network.jnlp;

import lombok.SneakyThrows;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import java.net.URL;

/**
 * Класс определяет codebase URL, где расположен файл jnlp.
 *
 * @author Aleksey Bykov
 * @since 20.04.2023
 */
public class CodebaseLocator {

	private final static String BASIC_SERVICE_NAME = "javax.jnlp.BasicService";

	/**
	 * Метод возвращает codebase URL, где расположен jnlp файл.
	 *
	 * В самом jnlp файле плейсхолдер "$$codebase" заменяется сервлетом
	 * {@see JnlpDownloadServlet} на codebase URL. Относительно данного
	 * адреса формируется запрос на сервлет.
	 *
	 * @return - codebase URL, где расположен jnlp файл (например, http://localhost:8080).
	 */
	@SneakyThrows
	public URL locate() {
		BasicService bs = (BasicService) ServiceManager.lookup(BASIC_SERVICE_NAME);
		return bs.getCodeBase();
	}
}
