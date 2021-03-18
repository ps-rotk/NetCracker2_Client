import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class View extends Thread {
    private Client client;
    private Scheduler scheduler;
    private Observer observer;
    private boolean go = false;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public View() throws IOException, ClassNotFoundException, InterruptedException {
        client = new Client();
        scheduler = new Scheduler(client);
        observer = new Observer(this);
        begin();
    }

    public boolean isGo() {
        return go;
    }

    public void setGo(boolean go) {
        this.go = go;
    }

    public void printMenu() {
        System.out.println("Меню");
        System.out.println("1) Посмотреть список задач");
        System.out.println("2) Добавить новую задачу");
        System.out.println("3) Изменить данные задачи");
        System.out.println("4) Найти задачи по дате");
        System.out.println("5) Найти задачи по дате и типу");
        System.out.println("6) Удалить задачу");
        System.out.println("7) Удалить выполненые задачи");
        System.out.println("8) Выход");
    }

    public void begin() throws IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        int number;
        deleteOldTask();
        while (!go) {
            printMenu();
            System.out.println("Введите пункт меню");
            String numberIn = in.nextLine();
            try {
                number = Integer.parseInt(numberIn);
                switch (number) {
                    case 1:
                        printListTask();
                        break;
                    case 2:
                        addTask();
                        break;
                    case 3:
                        updateTask();
                        break;
                    case 4:
                        getTaskByDate();
                        break;
                    case 5:
                        getTaskByDateType();
                        break;
                    case 6:
                        deleteTaskById();
                        break;
                    case 7:
                        deleteOldTask();
                        break;
                    case 8:
                        go = true;
                        scheduler.interrupt();
                        exit();
                        break;
                    default:
                        System.out.println();
                        System.out.println("Что-то пошло не так. Введите число, равное пункту меню.");
                }
            } catch (SocketException e) {
                reconnect();
            }
            catch (NumberFormatException e){
                System.out.println("Что-то пошло не так. Введите число, равное пункту меню.");
            }

        }
        System.out.println("Работа программы окончена");
    }

    //1
    public void printListTask() throws IOException, ClassNotFoundException {

        System.out.println(client.getStringListTask());

    }

    //2
    public void addTask() throws IOException, ClassNotFoundException {

        String str = "2";
        Scanner in = new Scanner(System.in);
        String date = checkDate();
        if (date == null) {
            return;
        }
        String time = checkTime();
        if (time == null) {
            return;
        }
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        System.out.println("Введите заметку");
        String text = in.nextLine();
        str += "\n" + date + "\n" + time + "\n" + type + "\n" + text;
        System.out.println(client.addTask(str));
        System.out.println();

    }

    //3
    public void updateTask() throws IOException, ClassNotFoundException {

        String str = "3\n";
        System.out.println("Введите ID задачи, который хотите изменить");
        Scanner inId = new Scanner(System.in);
        int ID = inId.nextInt();
        str += ID + "\n";
        Scanner in = new Scanner(System.in);
        String date = checkDate();
        if (date == null) {
            return;
        }
        String time = checkTime();
        if (time == null) {
            return;
        }
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        System.out.println("Введите заметку");
        String text = in.nextLine();
        str += date + "\n" + time + "\n" + type + "\n" + text;
        System.out.println(client.updateTask(str));
        System.out.println();

    }

    //4
    public void getTaskByDate() throws IOException, ClassNotFoundException {

        String str = "4\n";
        String date = checkDate();
        if (date == null) {
            return;
        }
        str += date;
        System.out.println(client.getTaskByDate(str));

    }

    //5
    public void getTaskByDateType() throws IOException, ClassNotFoundException {

        String str = "5\n";
        Scanner in = new Scanner(System.in);
        String date = checkDate();
        if (date == null) {
            return;
        }
        str += date;
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        str += "\n" + type;
        System.out.println(client.getTaskByDateType(str));

    }

    //6
    //доделать удаление при не существующем id
    public void deleteTaskById() throws IOException, ClassNotFoundException {

        try {
            String str = "6\n";
            System.out.println("Введите id задачи, которую хотите удалить");
            Scanner in = new Scanner(System.in);
            int id = in.nextInt();
            str += id;
            System.out.println(client.deleteTaskById(str));
        } catch (InputMismatchException o) {
            System.out.println("Введено неправльное значение id.");
        }

    }

    //7
    public void deleteOldTask() throws IOException, ClassNotFoundException {

        System.out.println("Проверка задач...");
        String deleteList = client.checkOldTask("7");
        if (deleteList != null && !deleteList.equals("")) {
            System.out.println("Следующие задачи устарели или были выполнены:");
            System.out.println(deleteList);
            System.out.println("Желаете удалить из списка эти задачи? Y для удаления:");
            Scanner dl = new Scanner(System.in);
            String del = dl.nextLine();
            if (del.equals("Y")) {
                System.out.println("Вы выбрали удаление задач. \nУдаляю...");
                client.deleteOldTask();
            } else {
                System.out.println("Вы выбрали не удалять");
            }
        } else {
            System.out.println("Устареших задач не обнаружено");
        }

    }

    //8
    public void exit() throws IOException {
        client.exit();
        scheduler.setStop(true);
    }

    private String checkDate() {
        boolean stop = false;
        while (!stop) {
            System.out.println("Введите дату в формате dd.MM.yyyy");
            Scanner in = new Scanner(System.in);
            String dateIn = in.nextLine();
            if (dateIn.equals("Y")) {
                stop = true;
                continue;
            }
            try {
                String[] date1 = dateIn.split("\\.");
                int[] date = new int[3];
                for (int i = 0; i < date1.length; i++) {
                    date[i] = Integer.parseInt(date1[i]);
                }

                LocalDate localDate = LocalDate.of(date[2], date[1], date[0]);
                if (localDate.isAfter(LocalDate.now()) || localDate.isEqual(LocalDate.now())) {
                    stop = true;
                    return date[0] + "." + date[1] + "." + date[2];
                } else {
                    System.out.println("Не удалось выполнить операцию. Неправильная дата. \nПовторите ввод или введите Y для выхода");
                }
            } catch (Exception e) {
                System.out.println("Не удалось выполнить операцию. Неправильная дата. \nПовторите ввод или введите Y для выхода");

            }
        }
        return null;
    }

    private String checkTime() {
        boolean stop = false;
        while (!stop) {
            System.out.println("Введите время в формате HH:mm");
            Scanner in = new Scanner(System.in);
            String timeIn = in.nextLine();
            if (timeIn.equals("Y")) {
                stop = true;
                continue;
            }
            try {
                String[] time1 = timeIn.split(":");
                int[] time = new int[2];
                for (int i = 0; i < time1.length; i++) {
                    time[i] = Integer.parseInt(time1[i]);
                }
                LocalTime localTime = LocalTime.of(time[0], time[1]);
                stop = true;
                return time[0] + ":" + time[1];
            } catch (Exception e) {
                System.out.println("Не удалось выполнить операцию.  \nПовторите ввод или введите Y для выхода");
            }
        }
        return null;
    }

    public void isNotification(String task) {
        System.out.println(task);
    }

    public void reconnect() throws IOException, ClassNotFoundException {
        System.out.println("Соединение разорвано");
        System.out.println("Переподключиться к серверу снова? (Введите Y для переподключения)");
        Scanner in = new Scanner(System.in);
        String y = in.nextLine();
        if (y.equals("Y")) {
            client.closeSockets();
            client.start();
        } else {
            go = true;
            scheduler.interrupt();
            exit();
        }
    }
}
