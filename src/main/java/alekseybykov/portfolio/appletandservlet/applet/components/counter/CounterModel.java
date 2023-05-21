package alekseybykov.portfolio.appletandservlet.applet.components.counter;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель компонента счетчика. Компонент состоит из данной модели и вью {@link CounterView}.
 *
 * Любые изменения модели счетчика приводят к широковещательной рассылке событий, слушателем которых
 * является вью {@link CounterView}, но могут быть и любые другие вью ("push model" MVC).
 * Т.о., измененная модель оповещает вью, которое меняется соответствующим образом. В данном случае,
 * изменения во вью изменяют данную модель счетчика. Модель оповещает это же вью через {@link CounterObserver},
 * что приводит к изменениям в общей модели данных аплета {@see AppDataModel}, в которую
 * записывается устанавливаемое значение счетчика. Общая модель данных аплета
 * {@see AppDataModel} не прослушивается и служит для дальнейшего формирования запроса к сервлету.
 *
 * @author Aleksey Bykov
 * @since 18.05.2023
 */
public class CounterModel {

	@Getter
	private String counter;

	/* Слушатели данной модели, на которые идет широковещательная рассылка
       при ее изменениях. */
	private final List<CounterObserver> observers = new ArrayList<>();

	/**
	 * Сеттер, вызов которого приводит к вызову метода {@link this#fireCounterChanged},
	 * оповещающему подписчиков модели о произошедших с ней изменениях.
	 *
	 * @param counter - новое значение параметра модели (значение счетчика).
	 */
	public void setCounter(String counter) {
		this.counter = counter;
		fireCounterChanged(counter);
	}

	/**
	 * Метод регистрирует слушатель данной модели для последующего оповещения.
	 *
	 * @param observer - слушатель, реализующий интерфейс {@link CounterObserver}.
	 */
	public void addObserver(CounterObserver observer) {
		observers.add(observer);
	}

	/**
	 * При изменении модели данный метод выполняет широковещательную рассылку всем
	 * слушателям, реализующим интерфейс {@link CounterObserver}.
	 *
	 * @param counter - новое значение параметра модели (значение счетчика).
	 */
	private void fireCounterChanged(String counter) {
		for (CounterObserver observer : observers) {
			observer.counterChanged(counter);
		}
	}
}
