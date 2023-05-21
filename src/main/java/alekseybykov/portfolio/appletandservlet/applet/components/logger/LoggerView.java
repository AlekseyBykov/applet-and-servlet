package alekseybykov.portfolio.appletandservlet.applet.components.logger;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Вью компонента логера аплета. Компонент логера состоит из данного вью и модели {@link LoggerModel}.
 *
 * Любые изменения модели логера приводят к широковещательной рассылке событий, слушателем которых
 * является данное вью, но могут быть и любые другие вью ("push model" MVC).
 * Т.о., измененная модель оповещает вью, которое меняется соответствующим образом. В данном случае,
 * изменения модели происходят в {@see AppletController#start}, что приводит к оповещению
 * вью через {@link this#logDataChanged}. Вью устанавливает строку ответа, полученного
 * от сервлета, в {@code JTextArea}.
 *
 * @author Aleksey Bykov
 * @since 28.04.2023
 */
public class LoggerView extends JPanel implements LoggerObserver {

	private final LoggerModel loggerModel;

	private JTextArea textArea;

	public LoggerView(LoggerModel loggerModel) {
		super();
		this.loggerModel = loggerModel;

		addObserver();
		initView();
	}

	/**
	 * Данное вью {@link LoggerView} регистрируется как слушатель
	 * модели {@link LoggerModel}.
	 */
	private void addObserver() {
		loggerModel.addObserver(this);
	}

	/**
	 * Метод, вызываемый прослушиваемой моделью {@link LoggerModel}
	 * на всех зарегистрированных слушателях. Содержимое {@link this#textArea}
	 * обновляется строкой {@param logContent}.
	 *
	 * @param logContent - строка в логе.
	 */
	@Override
	public void logDataChanged(String logContent) {
		textArea.setText(StringUtils.EMPTY);
		textArea.setText(logContent);
	}

	private void initView() {
		setLayout(new BorderLayout(5, 5));

		textArea = new JTextArea();
		textArea.setFont(new Font("Serif", Font.ITALIC, 13));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(new Color(204, 238, 241));

		add(wrapTextArea(), BorderLayout.CENTER);
	}

	private JScrollPane wrapTextArea() {
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		return scrollPane;
	}
}
