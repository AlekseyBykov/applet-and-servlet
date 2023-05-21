package alekseybykov.portfolio.appletandservlet.applet.components.counter;

/**
 * Интерфейс слушателя модели {@link CounterModel}.
 *
 * @author Aleksey Bykov
 * @since 17.05.2023
 */
public interface CounterObserver {

	void counterChanged(String counter);
}
