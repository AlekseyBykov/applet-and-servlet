package alekseybykov.portfolio.appletandservlet;

import alekseybykov.portfolio.appletandservlet.applet.components.button.StarterView;
import alekseybykov.portfolio.appletandservlet.applet.components.counter.CounterModel;
import alekseybykov.portfolio.appletandservlet.applet.components.counter.CounterView;
import alekseybykov.portfolio.appletandservlet.applet.components.format.FormatChooserModel;
import alekseybykov.portfolio.appletandservlet.applet.components.format.FormatChooserView;
import alekseybykov.portfolio.appletandservlet.applet.components.logger.LoggerModel;
import alekseybykov.portfolio.appletandservlet.applet.components.logger.LoggerView;
import alekseybykov.portfolio.appletandservlet.applet.components.toolbar.Toolbar;
import alekseybykov.portfolio.appletandservlet.applet.controller.AppletController;
import alekseybykov.portfolio.appletandservlet.applet.model.AppDataModel;
import alekseybykov.portfolio.appletandservlet.applet.view.AppletFrame;
import alekseybykov.portfolio.appletandservlet.network.connection.ServletClient;
import alekseybykov.portfolio.appletandservlet.network.connection.ServletConnection;
import alekseybykov.portfolio.appletandservlet.network.jnlp.CodebaseLocator;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * @author Aleksey Bykov
 * @since 21.05.2023
 */
public class Main {

	/**
	 * Точка входа аплета (приложения JWS).
	 *
	 * Т.к. не используется никакой фреймворк DI, все зависимости создаются явно
	 * и внедряются через ctor injection.
	 *
	 * Здесь создаются два потока - main (здесь же и завершается) и EDT.
	 * Вызов {@link SwingUtilities#invokeLater} передает метод run() в поток EDT,
	 * т.о., весь код в методе run() будет выполнен в потоке-диспетчере событий,
	 * как этого и требует Swing.
	 *
	 * (Компоновка может быть проверена изолированно, вызовом метода main).
	 */
	public static void main(String[] args) {
		// Весь код метода run() будет выполнен в потоке EDT.
		SwingUtilities.invokeLater(new Runnable() {
			@SneakyThrows
			public void run() {
				UIManager.setLookAndFeel(new MetalLookAndFeel());

				AppDataModel appDataModel = new AppDataModel();

				FormatChooserView formatChooser = buildFormatChooser(appDataModel);
				CounterView counter = buildCounter(appDataModel);

				StarterView starter = new StarterView();

				Toolbar toolbar = new Toolbar(formatChooser, counter, starter);

				LoggerModel loggerModel = new LoggerModel();
				LoggerView logger = new LoggerView(loggerModel);

				ServletClient servletClient = buildServletClient();

				AppletController controller = new AppletController(starter, appDataModel, servletClient, loggerModel);

				AppletFrame applet = new AppletFrame(toolbar, logger);
				applet.setVisible(true);
			}
		});
	}

	private static ServletClient buildServletClient() {
		CodebaseLocator codebaseLocator = new CodebaseLocator();
		ServletConnection servletConnection = new ServletConnection(codebaseLocator);

		return new ServletClient(servletConnection);
	}

	private static CounterView buildCounter(AppDataModel appDataModel) {
		CounterModel counterModel = new CounterModel();
		return new CounterView(counterModel, appDataModel);
	}

	private static FormatChooserView buildFormatChooser(AppDataModel appDataModel) {
		FormatChooserModel formatChooserModel = new FormatChooserModel();
		return new FormatChooserView(formatChooserModel, appDataModel);
	}
}
