package roman.skrypnyk;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String BD_USERNAME = "postgres";
    private static final String BD_PASSWORD = "postgres";
    private static final String BD_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) throws Exception {

        try {

            Connection connection = DriverManager.getConnection(BD_URL, BD_USERNAME, BD_PASSWORD);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1.Показать список всех задач");
                System.out.println("2.Выполнить задачу");
                System.out.println("3.Создать задачу");
                System.out.println("4.Удалить задачу");
                System.out.println("5.Выйти");
                int command = scanner.nextInt();

                if (command == 1) {
                    Statement statement = connection.createStatement();
                    String SQL_SELECT_TASK = "SELECT * FROM tasks ORDER BY id ASC";
                    ResultSet resultSet = statement.executeQuery(SQL_SELECT_TASK);
                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt("id") + ". "
                                + resultSet.getString("taskName") + " "
                                + resultSet.getString("state"));
                        System.out.println("___________________");
                    }
                } else if (command == 2) {
                    String SQL_UPDATE_PREPARE = "UPDATE tasks SET state = 'DONE' WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PREPARE);
                    System.out.println("Введите номер задачи:");
                    int taskId = scanner.nextInt();
                    preparedStatement.setInt(1, taskId);
                    preparedStatement.executeUpdate();
                } else if (command == 3) {
                    String SQL_SET_PREPARE = "INSERT INTO tasks (taskname, state) VALUES (?, 'IN_PROCESS')";
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_PREPARE);
                    System.out.println("Введите задачу:");
                    scanner.nextLine();
                    String taskId = scanner.nextLine();
                    preparedStatement.setString(1, taskId);
                    preparedStatement.executeUpdate();
                } else if (command == 4) {
                    String SQL_DELETE = "DELETE FROM tasks WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
                    System.out.println("Введите номер задачи для удаления:");
                    scanner.nextLine();
                    int taskId = scanner.nextInt();
                    preparedStatement.setInt(1, taskId);
                    preparedStatement.executeUpdate();
                } else if (command == 5) {
                    System.exit(0);
                } else {
                    System.err.println("Команды нет!");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
