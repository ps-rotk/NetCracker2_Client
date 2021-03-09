import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    Socket clientSocket = null;
    Socket clientSocketScheduler = null;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStreamScheduler;
    ObjectOutputStream objectOutputStreamScheduler;

    /*public class ProcessList implements Runnable {

        private final ArrayList<String> actions;

        public ProcessList(ArrayList<String> aList) {
            this.actions = aList;
        }

        public void run() {
            String str, message;
            System.out.println("Мы в ране");
            while (true){
                //System.out.println("Размер актионс внутри вайла " + this.actions.size());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (actions.size() > 0){

                    str = actions.get(0);
                    chooseMethod(str);
                    message = (String) objectInputStream.readObject();
                    String[] arr = message.split("_");
                    while(arr[0].equals("Alert")){
                        System.out.println(arr[1]); // вызвать метод вывода этой строки во view
                        message = (String) objectInputStream.readObject();
                        arr = message.split("_");
                    }
                    System.out.println(arr[1]);
                        System.out.flush();
                    actions.remove(0);
                }}
                catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Беда");
                }
            }
        }
    }*/

    public Client() throws IOException, ClassNotFoundException {
        // actions = new ArrayList<>();
        start();
    }

    public ObjectInputStream getObjectInputStreamScheduler() {
        return objectInputStreamScheduler;
    }

    public ObjectOutputStream getObjectOutputStreamScheduler() {
        return objectOutputStreamScheduler;
    }

    public void start() throws IOException, ClassNotFoundException {
        try {
            clientSocket = new Socket("localhost", 1024);
            clientSocketScheduler = new Socket("localhost", 1025);
            objectOutputStreamScheduler = new ObjectOutputStream(clientSocketScheduler.getOutputStream());
            objectInputStreamScheduler = new ObjectInputStream(clientSocketScheduler.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Мы на связи.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Вылетели на подключении");
        }
    }

    public String getStringListTask() throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("1");
        objectOutputStream.flush();
        return (String) objectInputStream.readObject();
    }

    public String addTask(String task) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(task);
        objectOutputStream.flush();
        return (String) objectInputStream.readObject();
    }

    public String updateTask(String task) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(task);
        objectOutputStream.flush();
        return (String) objectInputStream.readObject();
    }

    public String getTaskByDate(String str) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(str);
        objectOutputStream.flush();
        return (String) objectInputStream.readObject();
    }

    public String getTaskByDateType(String str) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(str);
        objectOutputStream.flush();
        return (String) objectInputStream.readObject();
    }

    public String deleteTaskById(String id) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(id);
        objectOutputStream.flush();
        return (String) objectInputStream.readObject();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String checkOldTask(String string) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(string);
        objectOutputStream.flush();
        return (String) objectInputStream.readObject();
    }

    public void deleteOldTask() throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("delete");
        objectOutputStream.flush();
    }

    public void exit() throws IOException {
        objectOutputStream.writeObject("8");
        objectOutputStream.flush();
    }
}
