package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

/**
 Создание таблицы User(ов)

     Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )

 Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)

 Очистка таблицы User(ов)

 Удаление таблицы**/

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        for (int i = 0; i < 4; i++) {
            String name = String.valueOf(new StringBuilder("Iva").append((char)(i + 97)));
            userService.saveUser(name, String.valueOf(new StringBuilder("Budeik").append((char)(i + 97))), (byte) (38  + i));
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        }
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
