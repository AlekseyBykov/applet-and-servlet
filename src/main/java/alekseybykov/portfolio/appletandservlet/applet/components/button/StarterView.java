package alekseybykov.portfolio.appletandservlet.applet.components.button;

import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Вью кнопки старта. Модель здесь особого смысла не имеет, поэтому не реализуется.
 *
 * Слушателем (Observer) данного вью является {@see AppletController}.
 * Для {@code startBtn} устанавливается Swing-слушатель нажатия (клика по кнопке) в
 * {@link this#addListener}, который в свою очередь при событии клика вызывает метод
 * {@link this#startButtonPressed}. Данный метод оповещает всех подписчиков
 * {@link this#observers} о том, что нажата кнопка. Подписчик {@see AppletController}
 * начинает взаимодействие с сервлетом - формирует и отправляет POST запрос.
 *
 * @author Aleksey Bykov
 * @since 27.04.2023
 */
public class StarterView extends JPanel {

	private JButton startBtn;

	/* Слушатели данного вью, на которые идет широковещательная рассылка
	  при изменениях во вью. */
	private final List<StarterObserver> observers = new ArrayList<>();

	public StarterView() {
		super();

		initView();
		addListener();
	}

	/**
	 * Метод регистрирует слушатель данного {@link StarterView} вью.
	 * Слушателем является {@see AppletController}.
	 *
	 * @param observer - слушатель, реализующий интерфейс {@link StarterObserver}.
	 */
	public void addObserver(StarterObserver observer) {
		observers.add(observer);
	}

	/**
	 * Метод добавляет Swing-слушатель события нажатия на кнопку {@link this#startBtn}.
	 *
	 * При нажатии на кнопку оповещается контроллер {@see AppletController},
	 * который начинает взаимодействие с сервлетом.
	 */
	private void addListener() {
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButtonPressed();
			}
		});
	}

	/**
	 * Метод выполняет оповещение (широковещательную рассылку) всех
	 * слушателей, реализующих интерфейс {@link StarterObserver}
	 * о событии нажатия на кнопку {@link this#startBtn}.
	 */
	private void startButtonPressed() {
		for (StarterObserver observer : observers) {
			observer.start();
		}
	}

	private void initView() {
		startBtn = new JButton("Отправить запрос.");
		setLayout(new GridBagLayout());
		add(startBtn, makeConstraints());
	}

	private GridBagConstraints makeConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.fill = GridBagConstraints.BOTH;

		constraints.weightx = NumberUtils.DOUBLE_ONE;
		constraints.weighty = NumberUtils.DOUBLE_ONE;

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 25, 0, 0);

		return constraints;
	}
}
