package alekseybykov.portfolio.appletandservlet.applet.components.logger;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
* Модель компонента логера аплета. Компонент логера состоит из данной модели и вью {@link LoggerView}.
*
* Любые изменения модели логера приводят к широковещательной рассылке событий, слушателем которых
* является вью {@link LoggerView}, но могут быть и любые другие вью ("push model" MVC).
* Т.о., измененная модель оповещает вью, которое меняется соответствующим образом. В данном случае,
* изменения модели происходят в {@see AppletController#start}, что приводит к оповещению
* вью {@link LoggerView} через {@link this#fireLogChanged}. Вью устанавливает строку ответа,
* полученного от сервлета, в {@code JTextArea}.
*
* @author Aleksey Bykov
* @since 18.05.2023
*/
public class LoggerModel {

	@Getter
	private String logContent;

	/* Слушатели данной модели, на которые идет широковещательная рассылка
      при ее изменениях. */
	private final List<LoggerObserver> observers = new ArrayList<>();

	/**
	* Сеттер, вызов которого приводит к вызову метода {@link this#fireLogChanged},
	* оповещающему подписчиков модели о произошедших с ней изменениях.
	*
	* @param logContent - новое значение параметра модели (строка в логе).
	*/
	public void setLogContent(String logContent) {
		this.logContent = logContent;
		fireLogChanged(logContent);
	}

	/**
	* Метод регистрирует слушатель данной модели для последующего оповещения.
	*
	* @param observer - слушатель, реализующий интерфейс {@link LoggerObserver}.
	*/
	public void addObserver(LoggerObserver observer) {
		observers.add(observer);
	}

	/**
	* При изменении модели данный метод выполняет широковещательную рассылку всем
	* слушателям, реализующим интерфейс {@link LoggerObserver}.
	*
	* @param logContent - новое значение параметра модели (строка в логе).
	*/
	private void fireLogChanged(String logContent) {
		for (LoggerObserver observer : observers) {
			observer.logDataChanged(logContent);
		}
	}
}
