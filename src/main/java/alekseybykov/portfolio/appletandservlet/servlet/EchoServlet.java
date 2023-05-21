package alekseybykov.portfolio.appletandservlet.servlet;

import lombok.SneakyThrows;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Сервлет (backend), выполняющий некоторую работу по запросу от аплета.
 * Развертывается в сервлет-контейнере Apache Tomcat-8(.5.87).
 *
 * @author Aleksey Bykov
 * @since 18.04.2023
 */
public class EchoServlet extends HttpServlet {

	@SneakyThrows
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		disableCaching(response);
		/* Сервлет может выполнять некоторую работу в backend. Например, формировать некоторый отчет.
		   Здесь же просто возвращаем строку вызывающему аплету.*/
		val status = "OK";
		try (PrintWriter writer = response.getWriter()) {
			writer.println(status.toString());
			writer.flush();
		}
	}

	private void disableCaching(HttpServletResponse response) {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control",
				"private, no-store, no-cache, must-revalidate");
	}
}
