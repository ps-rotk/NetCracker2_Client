package Interface;

import java.io.IOException;

public interface IObservable {
    void addObserver(IObserver o);

    void removeObserver(IObserver o);

    void notifyObservers(String task) throws IOException;
}