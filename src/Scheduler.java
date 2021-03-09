import Interface.IObservable;
import Interface.IObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Scheduler extends Thread implements IObservable {
    private List<IObserver> observers;
    private boolean stop;
    private Client client;
    ObjectInputStream objectInputStreamScheduler;

    public Scheduler(Client client) {
        observers = new ArrayList<>();
        stop = false;
        this.client = client;
        objectInputStreamScheduler = this.client.getObjectInputStreamScheduler();
        this.start();
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String task) throws IOException {
        for (IObserver observer : observers) {
            observer.update(task);
        }
    }

    public void run(){
        while (!stop){
            try{
                String task = (String) objectInputStreamScheduler.readObject();
                notifyObservers(task);
            } catch (Exception e) {
                return;
            }
        }
    }
}
