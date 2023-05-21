package alekseybykov.portfolio.appletandservlet.applet.components.counter;

import alekseybykov.portfolio.appletandservlet.applet.model.AppDataModel;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;

/**
 * Вью компонента счетчика. Компонент счетчика состоит из данного вью и модели {@link CounterModel}.
 *
 * Любые изменения модели счетчика приводят к широковещательной рассылке событий, слушателем которых
 * является данное вью, но могут быть любые другие вью ("push model" MVC).
 * Т.о., измененная модель оповещает вью, которое меняется соответствующим образом. В данном случае,
 * изменения в {@code counterField} вызывают метод {@link this#updateCounterModel()}, который
 * меняет модель счетчика в {@link CounterModel#setCounter(String)}}. Модель оповещает
 * это же вью через {@link CounterObserver} и в методе {@link this#counterChanged}
 * меняет общую модель данных аплета {@link AppDataModel} (но не саму себя еще раз), в которую
 * записывается устанавливаемое значение счетчика. Общая модель данных аплета
 * {@link AppDataModel} не прослушивается и служит для дальнейшего формирования запроса к сервлету.
 *
 * @author Aleksey Bykov
 * @since 27.04.2023
 */
public class CounterView extends JPanel implements CounterObserver {

	private final CounterModel counterModel;
	private final AppDataModel appDataModel;

	private final JTextField counterField = new JTextField(10);

	public CounterView(CounterModel counterModel, AppDataModel appDataModel) {
		super();
		this.counterModel = counterModel;
		this.appDataModel = appDataModel;

		addObserver();
		addTextFieldListener();

		initView();
	}

	/**
	 * Данное вью {@link CounterView} регистрируется как слушатель
	 * модели {@link CounterModel}.
	 */
	private void addObserver() {
		counterModel.addObserver(this);
	}

	/**
	 * Метод, вызываемый прослушиваемой моделью {@link CounterModel}
	 * на всех зарегистрированных слушателях. Изменение данной модели компонента т.о. приводит
	 * к изменению модели данных аплета {@link AppDataModel}.
	 *
	 * @param counter - выбранный в комбобоксе элемент.
	 */
	@Override
	public void counterChanged(String counter) {
		appDataModel.setCounter(counter);
	}

	/**
	 * Метод добавляет слушатель события изменения текста в {@code countField}.
	 * Изменение текста приводит к изменению модели в {@link this#updateCounterModel}.
	 */
	private void addTextFieldListener() {
		Document document = counterField.getDocument();
		document.addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent event) {
				updateCounterModel();
			}

			@Override
			public void removeUpdate(DocumentEvent event) {
				updateCounterModel();
			}

			@Override
			public void insertUpdate(DocumentEvent event) {
				updateCounterModel();
			}
		});
	}

	/**
	 * Метод изменяет модель {@link CounterModel} при изменении
	 * текста в {@code counterField}. Модель в свою очередь оповещает
	 * данное вью о произошедшем изменении вызовом метода {@link this#counterChanged}
	 * (метод вызывается для всех подписчиков модели). Далее в этом методе
	 * {@link this#counterChanged} изменяется общая модель данных аплета.
	 */
	private void updateCounterModel() {
		counterModel.setCounter(counterField.getText());
	}

	private void initView() {
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = makeConstraints();

		JLabel label = new JLabel("Значение счетчика:");
		add(label, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 7, 0, 0);

		add(counterField, constraints);
	}

	private GridBagConstraints makeConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.fill = GridBagConstraints.BOTH;

		constraints.weightx = NumberUtils.DOUBLE_ONE;
		constraints.weighty = NumberUtils.DOUBLE_ONE;

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 10, 0, 0);

		return constraints;
	}
}
