import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    private Socket clientSocket = null;
    ObjectInputStream ois;
    ObjectOutputStream ous;
    //ArrayList<String> actions;

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
                    message = (String) ois.readObject();
                    String[] arr = message.split("_");
                    while(arr[0].equals("Alert")){
                        System.out.println(arr[1]); // вызвать метод вывода этой строки во view
                        message = (String) ois.readObject();
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

    public void start() throws IOException, ClassNotFoundException {
        try{
            clientSocket = new Socket("localhost", 1024);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ois = new ObjectInputStream(clientSocket.getInputStream());
        ous = new ObjectOutputStream(clientSocket.getOutputStream());

        System.out.println("Мы на связи.");
      /* Thread thread = new Thread(new ProcessList(actions));
       thread.start();*/
    }
    
   /* public void chooseMethod(String str) throws IOException {
        char number =  str.charAt(0);
        switch (number){
            case '1':
                ous.writeObject(str);
                ous.flush();
                break;
            case '2':
                ous.writeObject(str);
                ous.flush();
                break;

        }
    }*/

    public String getStringListTask() throws IOException, ClassNotFoundException {
        String one = "1";
        /*actions.add(one);*/
        ous.writeObject(one);
        ous.flush();
        return (String) ois.readObject();
        //return "";
}

    public String addTask(String task) throws IOException, ClassNotFoundException {
        //actions.add(task);
        ous.writeObject(task);
        ous.flush();
        return (String) ois.readObject();
    }
    public String updateTask(String task) throws IOException, ClassNotFoundException {
        ous.writeObject(task);
        ous.flush();
        return (String) ois.readObject();
    }
    public String getTaskByDate(String str) throws IOException, ClassNotFoundException {
        ous.writeObject(str);
        ous.flush();
        return (String) ois.readObject();
    }
    public String getTaskByDateType(String str) throws IOException, ClassNotFoundException {
        ous.writeObject(str);
        ous.flush();
        return (String) ois.readObject();
    }
    public String deleteTaskById(String id) throws IOException, ClassNotFoundException {
        ous.writeObject(id);
        ous.flush();
        return (String) ois.readObject();
    }
    public String deleteOldTask() throws IOException, ClassNotFoundException {
        String seven = "7";
        ous.writeObject(seven);
        ous.flush();
        return (String) ois.readObject();
    }
    public void exit() throws IOException {
        ous.writeObject("8");
        ous.flush();
    }
}
