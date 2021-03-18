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
    private ObjectInputStream objectInputStreamScheduler;


    public Scheduler(Client client) {
        observers = new ArrayList<>();
        stop = false;
        this.client = client;
        objectInputStreamScheduler = client.getObjectInputStreamScheduler();
        this.start();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        objectInputStreamScheduler = client.getObjectInputStreamScheduler();
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

    public void run() {
        while (!stop) {
            try {
                String task = (String) objectInputStreamScheduler.readObject();
                try {
                    sleep(2);
                } catch (InterruptedException e) {
                    continue;
                }
                notifyObservers(task);
            } catch (IOException | ClassNotFoundException e) {
                continue;
            }
        }
    }
}
