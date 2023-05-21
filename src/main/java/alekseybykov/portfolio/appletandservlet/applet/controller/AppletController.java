package alekseybykov.portfolio.appletandservlet.applet.controller;

import alekseybykov.portfolio.appletandservlet.applet.components.button.StarterObserver;
import alekseybykov.portfolio.appletandservlet.applet.components.button.StarterView;
import alekseybykov.portfolio.appletandservlet.applet.components.logger.LoggerModel;
import alekseybykov.portfolio.appletandservlet.applet.model.AppDataModel;
import alekseybykov.portfolio.appletandservlet.network.connection.ServletClient;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 * Контроллер аплета, взаимодействующий с сервлетом {@see EchoServlet}.
 *
 * @author Aleksey Bykov
 * @since 28.04.2023
 */
public class AppletController implements StarterObserver {

	private final AppDataModel appDataModel;
	private final LoggerModel loggerModel;

	private final ServletClient servletClient;
	private final StarterView starter;

	public AppletController(StarterView starter,
	                        AppDataModel appDataModel,
	                        ServletClient servletClient,
	                        LoggerModel loggerModel) {

		this.servletClient = servletClient;
		this.starter = starter;
		this.appDataModel = appDataModel;
		this.loggerModel = loggerModel;

		addObserver();
	}

	/**
	 * Данный контроллер {@link AppletController} регистрируется как слушатель
	 * вью {@link StarterView}.
	 */
	private void addObserver() {
		starter.addObserver(this);
	}

	/**
	 * Метод формирует и отправляет запрос к сервлету {@see EchoServlet}.
	 *
	 * Поскольку в {@see Main#main} весь код аплета отправлен в EDT,
	 * необходимо вывести из него такие тяжеловесные операции, как формирование
	 * запроса к сервлету и ожидание ответа, иначе все последующие события,
	 * отправляемые из UI Swing в EDT будут накапливаться в очереди и приводить
	 * к непредсказуемым ошибкам (зависания интерфейса и пр).
	 */
	@Override
	public void start() {
		// Фоновый относительно EDT поток.
		SwingWorker<Boolean, Void> backgroundThread = new SwingWorker<Boolean, Void>() {
			String servletResponse = StringUtils.EMPTY;

			@SneakyThrows
			@Override
			protected Boolean doInBackground() {
				servletResponse = servletClient.sendRequest(createRequestData());
				return true;
			}

			/**
			 * Метод ожидает завершения фонового потока и обновляет модель {@link LoggerModel},
			 * которая в свою очередь оповещает вью {@see LoggerView}. Вью записывает в TextArea
			 * (лог работы аплета) строку {@code servletResponse}, полученную как ответ сервлета.
			 *
			 * Вью (и любые элементы UI Swing) не должно обновляться ни из какого потока, кроме EDT.
			 */
			@SneakyThrows
			@Override
			protected void done() {
				if (get() == Boolean.TRUE) {
					loggerModel.setLogContent(servletResponse);
				}
			}
		};

		backgroundThread.execute();
	}

	/**
	 * Метод формирует данные POST запроса, который далее будет отправлен на сервлет.
	 * Данные формируются из общей модели аплета {@link this#appDataModel}, в которую
	 * записываются различными вью при изменениях в их моделях (например, изменился выбранный элемент
	 * в комбобоксе, текст в TextField и пр.) Т.е. введенные в UI параметры.
	 *
	 * @return - данные POST запроса.
	 */
	private RequestData createRequestData() {
		RequestData requestData = RequestData.create();
		requestData.setRequestParam(RequestParams.FORMAT_PARAM.getName(), appDataModel.getFormat().getName());
		requestData.setRequestParam(RequestParams.COUNT_PARAM.getName(), appDataModel.getCounter());

		return requestData;
	}
}
