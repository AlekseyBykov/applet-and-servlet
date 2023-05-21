package alekseybykov.portfolio.appletandservlet.applet.components.format;

import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель компонента выбора формата (комбобокс). Компонент состоит из данной модели
 * и вью {@link FormatChooserView}.
 *
 * Любые изменения модели {@link FormatChooserModel} приводят к широковещательной рассылке событий,
 * слушателем которых является вью {@link FormatChooserView}, но могут быть и любые другие вью ("push model" MVC).
 * Измененная модель оповещает вью, которое меняется соответствующим образом. В данном случае,
 * изменения в {@code comboBox} неявно вызывают метод модели {@link this#setSelectedItem},
 * т.е. неявно в отличие от {@see CounterView} меняют модель, которая через {@link FormatObserver}
 * оповещает вью {@link FormatChooserView} и там меняет общую модель данных аплета {@see AppDataModel}.
 * В общую модель записывается выбранный в комбобоксе формат, далее используется при формировании запроса к сервлету.
 * Общая модель данных аплета не прослушивается.
 *
 * @author Aleksey Bykov
 * @since 27.04.2023
 */
public class FormatChooserModel extends AbstractListModel<Format> implements ComboBoxModel<Format> {

	private final Format[] formats = {
			Format.PDF, Format.RTF,
			Format.TXT, Format.EXCEL,
			Format.HTML
	};

	@Getter
	private Format selectedItem;

    /* Слушатели данной модели, на которые идет широковещательная рассылка
       при ее изменениях. */
	private final List<FormatObserver> observers = new ArrayList<>();

	/**
	 * Метод регистрирует слушатель данной модели для последующего оповещения.
	 *
	 * @param observer - слушатель, реализующий интерфейс {@link FormatObserver}.
	 */
	public void addObserver(FormatObserver observer) {
		observers.add(observer);
	}

	/**
	 * При изменении модели данный метод выполняет широковещательную рассылку всем
	 * слушателям, реализующим интерфейс {@link FormatObserver}.
	 *
	 * @param format - новое значение параметра модели (интересующий формат).
	 */
	private void fireFormatChanged(Format format) {
		for (FormatObserver observer : observers) {
			observer.formatChanged(format);
		}
	}

	/**
	 * Сеттер неявно вызывается, если в комбобоксе изменили текущий выбранный элемент.
	 * Приводит к вызову {@link this#fireFormatChanged}, оповещающему подписчиков
	 * модели о произошедших с ней изменениях.
	 *
	 * @param selectedItem - новый выбранный элемент в комбобоксе.
	 */
	@Override
	public void setSelectedItem(Object selectedItem) {
		this.selectedItem = (Format) selectedItem;
		fireFormatChanged(this.selectedItem);
	}

	@Override
	public Format getElementAt(int index) {
		return formats[index];
	}

	@Override
	public int getSize() {
		return formats.length;
	}
}
