package alekseybykov.portfolio.appletandservlet.applet.components.format;

import alekseybykov.portfolio.appletandservlet.applet.model.AppDataModel;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Вью компонента выбора формата (комбобокс). Компонент состоит из данного вью
 * и модели {@link FormatChooserModel}.
 *
 * Любые изменения модели {@link FormatChooserModel} приводят к широковещательной рассылке событий,
 * слушателем которых является данное вью, но могут быть любые другие вью ("push model" MVC).
 * Измененная модель оповещает вью, которое меняется соответствующим образом. В данном случае,
 * изменения в {@code comboBox} неявно вызывают метод модели {@link FormatChooserModel#setSelectedItem},
 * т.е. неявно в отличие от {@see CounterView} меняют модель, которая через {@see fireFormatChanged}
 * оповещает это же вью и в методе {@link this#formatChanged(Format)} меняет общую модель данных
 * аплета {@link AppDataModel} (но не саму себя еще раз). В общую модель записывается выбранный в комбобоксе
 * формат. Общая модель данных аплета {@link AppDataModel} не прослушивается и служит для дальнейшего
 * формирования запроса к сервлету.
 *
 * @author Aleksey Bykov
 * @since 27.04.2023
 */
public class FormatChooserView extends JPanel implements FormatObserver {

	private final FormatChooserModel formatChooserModel;
	private final AppDataModel appDataModel;

	private JComboBox<Format> comboBox;

	public FormatChooserView(FormatChooserModel formatChooserModel, AppDataModel appDataModel) {
		super();

		this.formatChooserModel = formatChooserModel;
		this.appDataModel = appDataModel;

		initView();
		addObserver();
	}

	/**
	 * Данное вью {@link FormatChooserView} регистрируется как слушатель
	 * модели {@link FormatChooserModel}.
	 */
	private void addObserver() {
		formatChooserModel.addObserver(this);
	}

	/**
	 * Метод, вызываемый прослушиваемой моделью {@link FormatChooserModel}
	 * на всех зарегистрированных слушателях. Изменение данной модели компонента т.о. приводит
	 * к изменению модели данных аплета {@link AppDataModel}.
	 *
	 * @param format - выбранный в комбобоксе элемент.
	 */
	@Override
	public void formatChanged(Format format) {
		appDataModel.setFormat(format);
	}

	private void initView() {
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = makeConstraints();

		JLabel label = new JLabel("Формат:");
		add(label, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 7, 0, 0);

		makeComboBox();
		add(comboBox, constraints);
	}

	private void makeComboBox() {
		comboBox = new JComboBox<>();
		comboBox.setModel(formatChooserModel);
		comboBox.setSelectedIndex(0);
		comboBox.setMaximumRowCount(5);
	}

	private GridBagConstraints makeConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.fill = GridBagConstraints.BOTH;

		constraints.weightx = NumberUtils.DOUBLE_ONE;
		constraints.weighty = NumberUtils.DOUBLE_ONE;

		constraints.gridx = 0;
		constraints.gridy = 0;

		return constraints;
	}
}
