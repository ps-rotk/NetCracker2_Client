import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class View {
    private Client client;

    public View() throws IOException, ClassNotFoundException, InterruptedException {
        client = new Client();
        start();
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

    public void start() throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        boolean stop = false;
        int number;
        while (!stop) {
            //Thread.sleep(100);
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
                        stop = true;
                        exit();
                        //controller.exit();
                        break;
                    default:
                        System.out.println("Неверное число, повторите ввод");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
                System.out.println("Что-то пошло не так. Введите число, равное пункту меню.");
            }
        }
    }

    //1
    public void printListTask() throws IOException, ClassNotFoundException {
        System.out.println(client.getStringListTask());
    }

    //2
    public void addTask() throws IOException, ClassNotFoundException {
    //0 - menu, 1 - id, 2 - date, 3 - time, 4 - type, 5 - text
        String str = "2";
        str += " 0";
        System.out.println("Введите дату в формате dd.MM.yyyy");
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
        System.out.println("Введите время в формате HH:mm");
        String time = in.nextLine();
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        System.out.println("Введите заметку");
        String text = in.nextLine();
        str += " " + date + " " + time + " " + type + " " + text;
        System.out.println(str);
        System.out.println(client.addTask(str));
        /*
        LocalDate localDate = checkDate();
        LocalTime localTime = checkTime();
        if (localDate == null || localTime == null) {
            return;
        }
        LocalDateTime date = LocalDateTime.of(localDate, localTime);
        System.out.println("Введите тип задачи");
        Scanner in = new Scanner(System.in);
        String type = in.nextLine();
        System.out.println("Введите заметку");
        String text = in.nextLine();
        Integer id = controller.setNewId();
        Task newTask = new Task(id, date, type, text);
        controller.addTask(newTask);*/
    }

    //3
    public void updateTask() throws IOException, ClassNotFoundException {
        String str = "3";
        System.out.println("Введите ID задачи, который хотите изменить");
        Scanner inId = new Scanner(System.in);
        int ID = inId.nextInt();
        str += " " + ID;
        System.out.println("Введите дату в формате dd.MM.yyyy");
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
        System.out.println("Введите время в формате HH:mm");
        String time = in.nextLine();
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        System.out.println("Введите заметку");
        String text = in.nextLine();
        str += " " + date + " " + time + " " + type + " " + text;
        System.out.println(str);
        System.out.println(client.updateTask(str));
        /*
        printListTask();
        System.out.println("Введите ID задачи");
        Scanner in = new Scanner(System.in);
        Integer id = in.nextInt();
        Task temp = controller.getTaskById(id);
        if (temp == null) {
            System.out.println("Введенный id не найден");
            return;
        }
        LocalDate localDate = checkDate();
        LocalTime localTime = checkTime();
        if (localDate == null || localTime == null) {
            return;
        }
        LocalDateTime date = LocalDateTime.of(localDate, localTime);
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        System.out.println("Введите заметку");
        String text = in.nextLine();
        controller.updateTask(new Task(id, date, type, text));
        System.out.println("Задача обновлена");*/
    }

    //4
    public void getTaskByDate() throws IOException, ClassNotFoundException {
        String str = "4" + " 0";
        System.out.println("Введите дату в формате dd.MM.yyyy");
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
        str += " " + date + " 00:00" + " 0" + " 0";
        System.out.println(client.getTaskByDate(str));
        /*
        LocalDate localDate = checkDate();
        if (localDate == null) {
            return;
        }
        LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        ArrayList<Task> listTask = controller.getTaskByDate(date);
        if (listTask.size() != 0) {
            System.out.println("Cписок задач: ");
            for (Task task : listTask) {
                System.out.println(task.toString());
            }
        } else System.out.println("По данным критериям ничего не найдено");*/
    }

    //5
    public void getTaskByDateType() throws IOException, ClassNotFoundException {
        String str = "5" + " 0";
        System.out.println("Введите дату в формате dd.MM.yyyy");
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
        str += " " + date + " 00:00";
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        str += " " + type + " 0";
        System.out.println(client.getTaskByDateType(str));
        /*
        LocalDate localDate = checkDate();
        if (localDate == null) {
            return;
        }
        LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        System.out.println("Введите тип задачи");
        Scanner in = new Scanner(System.in);
        String type = in.nextLine();
        ArrayList<Task> listTask = controller.getTaskByQuery(date, type);
        if (listTask.size() != 0) {
            System.out.println("Cписок задач: ");
            for (Task task : listTask) {
                System.out.println(task.toString());
            }
        } else System.out.println("По данным критериям ничего не найдено");*/
    }

    //6
    //доделать удаление при не существующем id
    public void deleteTaskById() throws IOException, ClassNotFoundException {
        try {
            String str = "6";
            System.out.println("Введите id задачи, которую хотите удалить");
            Scanner in = new Scanner(System.in);
            Integer id = in.nextInt();
            str += " " + id.toString() + " 11.11.2011 00:00" + " 0" + " 0";
            System.out.println(client.deleteTaskById(str));
        } catch (InputMismatchException o){
            System.out.println("Введено неправльное значение id.");
        }
        /*
        printListTask();
        System.out.println("Введите id задачи, которую хотите удалить");
        Scanner in = new Scanner(System.in);
        Integer id = in.nextInt();
        controller.deleteTaskById(id);
        System.out.println("Задача удалена");*/

    }

    //7
    public void deleteOldTask() throws IOException, ClassNotFoundException {
        System.out.println(client.deleteOldTask());
        /*
        System.out.println("Проверка задач...\n");
        ArrayList<Task> deleteList = controller.checkOldTask();
        if (deleteList != null && deleteList.size() != 0) {
            System.out.println("Следующие задачи устарели или были выполнены:");
            for (Task t : deleteList) {
                System.out.println(t.toString());
            }
            System.out.println("Желаете удалить из списка эти задачи? Y для удаления:");
            Scanner dl = new Scanner(System.in);
            String del = dl.nextLine();
            if (del.equals("Y")) {
                System.out.println("Вы выбрали удаление задач. \nУдаляю...");
                for (Task t : deleteList) {
                    controller.deleteTaskById(t.getId());
                }
                System.out.println("Удаление устаревших задач выполнено успешно");
            } else {
                System.out.println("Вы выбрали не удалять");
            }
        } else {
            System.out.println("Устареших задач не обнаружено");
        }*/
    }

    //8
    public void exit() throws IOException {
        client.exit();
    }

    private LocalDate checkDate() {/*
        boolean stop = false;
        while (!stop) {
            System.out.println("Введите дату в формате dd.MM.yyyy");
            Scanner in = new Scanner(System.in);
            String dateIn = in.nextLine();
            if (dateIn.equals("Y")) {
                stop = true;
            }
            String[] date1 = dateIn.split("\\.");
            int[] date = new int[3];
            for (int i = 0; i < date1.length; i++) {
                date[i] = Integer.parseInt(date1[i]);
            }
            LocalDate localDate = LocalDate.of(date[2], date[1], date[0]);
            if (localDate.isAfter(LocalDate.now()) || localDate.isEqual(LocalDate.now())) {
                stop = true;
                return localDate;
            } else {
                System.out.println("Не удалось выполнить операцию. Неправильная дата. \nПовторите ввод или введите Y для выхода");
            }
        }
       */ return null;
    }

    private LocalTime checkTime() {
        System.out.println("Введите время в формате HH:mm");
        Scanner in = new Scanner(System.in);
        String timeIn = in.nextLine();
        String[] time1 = timeIn.split(":");
        int[] time = new int[2];
        for (int i = 0; i < time1.length; i++) {
            time[i] = Integer.parseInt(time1[i]);
        }
        return LocalTime.of(time[0], time[1]);
    }
}
