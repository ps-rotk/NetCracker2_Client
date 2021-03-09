
import Interface.IObserver;
import java.io.IOException;

public class Observer implements IObserver {
    private View view;


    public Observer(View view){
        this.view = view;
        view.getScheduler().addObserver(this);
    }
    @Override
    public void update(String task) throws IOException {
        view.isNotification(task);
    }

}
