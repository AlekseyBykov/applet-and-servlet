package alekseybykov.portfolio.appletandservlet.applet.components.toolbar;

import alekseybykov.portfolio.appletandservlet.applet.components.button.StarterView;
import alekseybykov.portfolio.appletandservlet.applet.components.counter.CounterView;
import alekseybykov.portfolio.appletandservlet.applet.components.format.FormatChooserView;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Компонент-контейнер, тулбар в верхней части аплета.
 * Содержит вью выбора формата (комбобокс), счетчик (текстовое поле)
 * и кнопку старта генерации.
 *
 * @author Aleksey Bykov
 * @since 27.04.2023
 */
public class Toolbar extends JPanel {

	private final FormatChooserView formatChooser;
	private final CounterView counter;
	private final StarterView starter;

	public Toolbar(FormatChooserView formatChooser,
	               CounterView counter,
	               StarterView starter) {
		super();

		this.formatChooser = formatChooser;
		this.counter = counter;
		this.starter = starter;

		initView();
	}

	private void initView() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createTitledBorder(StringUtils.EMPTY));

		add(formatChooser);
		add(counter);
		add(starter);
	}
}
