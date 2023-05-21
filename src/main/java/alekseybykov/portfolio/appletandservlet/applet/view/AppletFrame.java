package alekseybykov.portfolio.appletandservlet.applet.view;

import alekseybykov.portfolio.appletandservlet.applet.components.logger.LoggerView;
import alekseybykov.portfolio.appletandservlet.applet.components.toolbar.Toolbar;

import javax.swing.*;
import java.awt.*;

/**
 * Аплет (frontend, клиент), формирующий запрос на сервлет.
 *
 * В запросе указывается интересующий формат, счетчик и пр. Строго говоря, это не аплет, а приложение Java Web Start (JWS),
 * поскольку аплет встраивается в веб-страницу, здесь же - по ссылке скачивается jnlp файл и выполняется в sandbox'e клиентского
 * обозревателя (либо в AppletViewer'e).
 *
 * Клиенту отображается стартовая страница index.html, где уазана ссылка на jnlp файл (<a href="AppletAndServlet.jnlp">Launch JWS Application</a>)
 * Данный jnlp файл (AppletAndServlet.jnlp) скачивается по клику на ссылке. В Applet.jnlp в свою очередь указана ссылка на jar файл
 * (<jar href="AppletAndServlet.jar" main="true"/>), который и будет выполнен в окружении JRE клиентской машины.
 *
 * Все файлы, которые скачиваются клиентским обозревателем, располагаются в корне веб-архива AppletAndServlet.war.
 *
 * Поскольку jar файлу в рантайме нужны ряд библиотек для выполнения тех или иных задач,
 * в jnlp указывается следующий блок:
 * <jar href="lib/commons-io-2.11.0.jar" main="false" download="eager"/>
 * <jar href="lib/commons-collections-3.2.2.jar" main="false" download="eager"/>
 * <jar href="lib/commons-lang3-3.12.0.jar" main="false" download="eager"/>
 * - Параметр download="eager" указывает, что библиотека должна быть скачана вместе с главным jar файлом, до непосредственного обращения к ней.
 * - Параметр <update check="always"/> обеспечивает скачивание данных библиотек каждый раз при запуске jar файла (т.к. иначе они кэшируются в браузере).
 *
 * Все данные библиотеки копируются в каталог lib при сборке с помощью плагина "maven-dependency-plugin" (см. pom.xml).
 *
 * @author Aleksey Bykov
 * @since 18.04.2023
 */
public class AppletFrame extends JFrame {

	private final Toolbar toolbar;
	private final LoggerView logger;

	public AppletFrame(Toolbar toolbar, LoggerView logger) {
		super(String.format("%s %s",
				Titles.APPLICATION_NAME.getTitle(),
				Titles.APPLICATION_VERSION.getTitle())
		);

		this.toolbar = toolbar;
		this.logger = logger;

		initView();
	}

	private void initView() {
		setLayout(new BorderLayout(5, 5));

		add(toolbar, BorderLayout.NORTH);
		add(logger, BorderLayout.CENTER);

		setSize(FrameSize.WIDTH.getValue(), FrameSize.HEIGHT.getValue());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
