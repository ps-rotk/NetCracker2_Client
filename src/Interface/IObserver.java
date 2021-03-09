package Interface;

import java.io.IOException;

public interface IObserver {
    void update(String task) throws IOException;
}
