/**
 * Main.java
 * В данном файле реализован основной класс программы.
 */

package attestation;

import org.w3c.dom.ls.LSOutput;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int id = 1;
    static List<User> user_array_list = new ArrayList<>();
    static List<Products> product_array_list = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    /* ******************************************************************************
     * @brief  Основное тело программы.
     */
    public static void main(String[] args) {
        default_accounts();
        default_products_list();
        shop_console();
    }

    /* ******************************************************************************
     * @brief  Заполнение списка продуктов.
     * @param  Нет.
     * @retval Нет.
     */
    public static void default_products_list() {
        product_array_list.add(new Products(TypeProduct.FOOD, 2));
        product_array_list.add(new Products(TypeProduct.COSMETICS, 2));
        product_array_list.add(new Products(TypeProduct.CLOTHES, 2));
        product_array_list.add(new Products(TypeProduct.SHOES, 2));
    }

    /* ******************************************************************************
     * @brief  Заполнение тестовых аккаунтов.
     * @param  Нет.
     * @retval Нет.
     */
    public static void default_accounts() {
        String none = "none";

        String login_admin = "a";
        String email_admin = "admin@mail.ru";
        String password_admin = "a";
        User admin_user  = new User(id, login_admin, email_admin, password_admin,
                none, none, none, none, Role.ADMIN_USER);
        id++;

        String login_user = "q";
        String email_user = "user@mail.ru";
        String password_user = "q";
        User ordinary_user  = new User(id, login_user, email_user, password_user,
                none, none, none, none, Role.ORDINARY_USER);
        id++;

        user_array_list.add(admin_user);
        user_array_list.add(ordinary_user);
    }

    /* ******************************************************************************
     * @brief  Реализация консольного магазина.
     * @param  Нет.
     * @retval Нет.
     */
    public static void shop_console() {
        System.out.println("1 - Зарегистрироваться в системе\n" +
                           "2 - Войти в систему");
        try {
            switch (input.nextInt()) {
                case 1:
                    registration();
                    break;
                case 2:
                    authorisation();
                    break;
                default:
                    System.out.println("Вы выбрали неверное действие. " +
                            "Пожалуйста, попробуйте еще раз.");
                    shop_console();
                    break;
            }
        }
        catch (Exception exception) {
            System.out.println("Неверная команда, попробуйте еще раз.");
            input.nextLine();
            shop_console();
        }
    }

    /* ******************************************************************************
     * @brief  Регистрация.
     * @param  Нет.
     * @retval Нет.
     */
    public static void registration() {
        System.out.println("Регистрация");

        System.out.println("Введите логин: ");
        input.nextLine();
        String login = input.nextLine();
        while (!login.matches("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$")) {
            System.out.println("Логин должен быть введен с ограничением 2-20 " +
                    "символов, которыми могут быть латинские буквы и цифры, " +
                    "первый символ обязательно буква.");
            System.out.println("Попробуйте еще раз.");
            login = input.nextLine();
        }

        System.out.println("Введите email: ");
        String email = input.nextLine();
        while (!email.matches("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$")) {
            System.out.println("Введен некорректный email. Попробуйте еще раз.");
            email = input.nextLine();
        }

        System.out.println("Введите пароль: ");
        String password = input.nextLine();
        while (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$")) {
            System.out.println("Пароль должен содержать строчные и прописные " +
                    "латинские буквы, цифры. Попробуйте еще раз.");
            password = input.nextLine();
        }

        System.out.println("Введите фамилию: ");
        String last_name = input.nextLine();
        while (!last_name.matches("^[а-яА-ЯёЁa-zA-Z]+$")) {
            System.out.println("Фамилия может содержать только буквы. Попробуйте еще раз.");
            last_name = input.nextLine();
        }

        System.out.println("Введите имя: ");
        String first_name = input.nextLine();
        while (!first_name.matches("^[а-яА-ЯёЁa-zA-Z]+$")) {
            System.out.println("Имя может содержать только буквы. Попробуйте еще раз.");
            first_name = input.nextLine();
        }

        System.out.println("Введите отчество: ");
        String patronymic = input.nextLine();
        while (!patronymic.matches("^[а-яА-ЯёЁa-zA-Z]+$")) {
            System.out.println("Отчество может содержать только буквы. Попробуйте еще раз.");
            patronymic = input.nextLine();
        }

        System.out.println("Введите телефон: ");
        String telephone = input.nextLine();
        while (!telephone.matches("^((\\+7|7|8)+([0-9]){10})$")) {
            System.out.println("Введен некорректный номер телефона. Попробуйте еще раз.");
            telephone = input.nextLine();
        }

        boolean ok_registration = false;
        while (ok_registration == false) {
            switch (checkReplay(login, email)) {
                case 0:
                    ok_registration = true;
                    break;
                case 1:
                    System.out.println("Введите логин: ");
                    login = input.nextLine();
                    break;
                case 2:
                    System.out.println("Введите email: ");
                    email = input.nextLine();
                    break;
                default: break;
            }
        }

        User new_user = new User(id, login, email, password,
            last_name, first_name, patronymic, telephone, Role.ORDINARY_USER);
        id++;
        user_array_list.add(new_user);
        System.out.println("Вы успешно зарегистрировались");
        System.out.println();

        shop_console();
    }

    /* ******************************************************************************
     * @brief  Проверка уникальности логина и адреса email.
     * @param  login - логин;
     *         email - адрес email.
     * @retval 0 - логин и пароль уникальны;
     *         1 - такой логин уже существует;
     *         2 - такой email уже существует.
     */
    public static int checkReplay(String login, String email) {
        for (User user_item : user_array_list) {
            if (user_item.getLogin().equals(login)) {
                System.out.println("Такой логин уже существует, попробуйте еще раз");
                if (user_item.getEmail().equals(email)) {
                    System.out.println("Такой email уже существует, попробуйте еще раз");
                    return 2;
                }
                return 1;
            }
        }
        return 0;
    }

    /* ******************************************************************************
     * @brief  Авторизация.
     * @param  Нет.
     * @retval Нет.
     */
    public static void authorisation() {
        System.out.println("Авторизация");

        System.out.println("Введите логин или email: ");
        input.nextLine();
        String login_email_user = input.nextLine();

        System.out.println("Введите пароль: ");
        String password_user = input.nextLine();

        for (User user : user_array_list) {
            if ( user.getLogin().equals(login_email_user) ||
                    user.getEmail().equals(login_email_user) )
            {
                if (user.getPassword().equals(password_user)) {
                    System.out.println("Вы успешно аутентифицировались");
                    System.out.println();
                    if (user.getRole().equals(Role.ORDINARY_USER))
                        user_actions();
                    else if (user.getRole().equals(Role.ADMIN_USER)) {
                        admin_actions();
                    }
                    break;
                } else {
                    System.out.println("Вы ввели неверный пароль");
                    System.out.println();
                    shop_console();
                    break;
                }
            }
        }
        System.out.println("Вы ввели неверный логин или email");
        System.out.println();
        shop_console();
    }

    /* ******************************************************************************
     * @brief  Выбор действий, доступных администратору.
     * @param  Нет.
     * @retval Нет.
     */
    public static void admin_actions() {
        System.out.println("""
                Выберите действие:
                0 - просмотр доступных товаров для покупки
                1 - добавление товара
                2 - удаление товара
                3 - просмотр информации о пользователях
                4 - смена роли пользователя
                5 - выход из аккаунта""");
        try {
            int action_num = input.nextInt();
            AdminActions action = AdminActions.values()[action_num];
            switch (action) {
                case VIEW_PRODUCTS:
                    view_products();
                    break;
                case ADD_PRODUCT:
                    add_product();
                    break;
                case REMOVE_PRODUCT:
                    remove_product();
                    break;
                case VIEW_USERS:
                    view_users();
                    break;
                case CHANGE_ROLE_USER:
                    change_role_user();
                    break;
                case EXIT:
                    System.out.println("Вы вышли из аккаунта.");
                    System.out.println();
                    shop_console();
                    break;
                default:
                    System.out.println("Неверная команда, попробуйте еще раз.");
                    System.out.println();
                    admin_actions();
                    break;
            }
            admin_actions();
        }
        catch (Exception exception) {
            System.out.println("Неверная команда, попробуйте еще раз.");
            input.nextLine();
            admin_actions();
        }
    }

    /* ******************************************************************************
     * @brief  Выбор действий, доступных пользователю.
     * @param  Нет.
     * @retval Нет.
     */
    public static void user_actions() {
        System.out.println("""
                Выберите действие:
                0 - просмотр доступных товаров для покупки
                1 - выход из аккаунта""");
        try {
            int action_num = input.nextInt();
            UserActions action = UserActions.values()[action_num];

            switch (action) {
                case VIEW_PRODUCTS:
                    view_products();
                    break;
                case EXIT:
                    System.out.println("Вы вышли из аккаунта.");
                    System.out.println();
                    shop_console();
                    break;
                default:
                    System.out.println("Неверная команда, попробуйте еще раз.");
                    System.out.println();
                    user_actions();
                    break;
            }
            user_actions();
        }
        catch (Exception exception) {
            System.out.println("Неверная команда, попробуйте еще раз.");
            input.nextLine();
            user_actions();
        }
    }

    /* ******************************************************************************
     * @brief  Просмотр списка доступных товаров для покупки.
     * @param  Нет.
     * @retval Нет.
     */
    public static void view_products() {
        System.out.println("Список товаров, доступных для покупки: ");
        for (Products product_item : product_array_list) {
            System.out.println(product_item.getDescriptionProduct() +
                    " - " + product_item.getNumberProduct() + " шт.");
        }
        System.out.println();
    }

    /* ******************************************************************************
     * @brief  Добавление товара.
     * @param  Нет.
     * @retval Нет.
     */
    public static void add_product() {
        System.out.println("""
                Выберите какой товар добавить:
                0 - еда
                1 - косметика
                2 - одежда
                3 - обувь""");
        try {
            int type_input = input.nextInt();
            System.out.println("Введите сколько товаров добавить");
            int plus_product = input.nextInt();

            TypeProduct typeProduct = TypeProduct.values()[type_input];
            for (int i = 0; i < product_array_list.size(); i++) {
                if (product_array_list.get(i).getTypeProduct().equals(typeProduct)) {
                    int current_num = product_array_list.get(i).getNumberProduct();
                    System.out.println(current_num);
                    product_array_list.set(i, new Products(typeProduct,
                            current_num + plus_product));
                    System.out.println("Добавлено " + plus_product +
                            " шт. товара " + product_array_list.get(i).getDescriptionProduct());
                    System.out.println();
                    break;
                }
            }
        }
        catch (Exception exception) {
            System.out.println("Неверная команда, попробуйте еще раз.");
            input.nextLine();
            add_product();
        }
    }

    /* ******************************************************************************
     * @brief  Удаление товара.
     * @param  Нет.
     * @retval Нет.
     */
    public static void remove_product() {
        System.out.println("""
                Выберите какой товар удалить:
                0 - еда
                1 - косметика
                2 - одежда
                3 - обувь""");
        try {
            int type_input = input.nextInt();
            System.out.println("Введите сколько товаров удалить: ");
            int minus_product = input.nextInt();

            TypeProduct typeProduct = TypeProduct.values()[type_input];
            for (int i = 0; i < product_array_list.size(); i++) {
                if (product_array_list.get(i).getTypeProduct().equals(typeProduct)) {
                    int current_num = product_array_list.get(i).getNumberProduct();
                    System.out.println(current_num);
                    if (current_num - minus_product < 0) {
                        product_array_list.set(i, new Products(typeProduct, 0));
                        System.out.println("Удален весь товар " +
                                product_array_list.get(i).getDescriptionProduct());
                        System.out.println();
                        break;
                    }
                    product_array_list.set(i, new Products(typeProduct,
                            current_num - minus_product));
                    System.out.println("Удалено " + minus_product +
                            " шт. товара " + product_array_list.get(i).getDescriptionProduct());
                    System.out.println();
                    break;
                }
            }
        }
        catch (Exception exception) {
            System.out.println("Неверная команда, попробуйте еще раз.");
            input.nextLine();
            remove_product();
        }
    }

    /* ******************************************************************************
     * @brief  Просмотр информации о пользователях.
     * @param  Нет.
     * @retval Нет.
     */
    public static void view_users() {
        System.out.println("Информация о пользователях: ");
        for (User user_item : user_array_list) {
            System.out.println(user_item.toString());
        }
        System.out.println();
    }

    /* ******************************************************************************
     * @brief  Смена роли пользователя.
     * @param  Нет.
     * @retval Нет.
     */
    public static void change_role_user() {
        System.out.println("Введите ID пользователя: ");
        try {
            int id_user = input.nextInt();
            for (int i = 0; i < user_array_list.size(); i++) {
                if (user_array_list.get(i).getId() == id_user) {
                    System.out.println("Текущая роль: " + user_array_list.get(i).getRole());
                    if (user_array_list.get(i).getRole().equals(Role.ORDINARY_USER))
                        user_array_list.get(i).setRole(Role.ADMIN_USER);
                    else if (user_array_list.get(i).getRole().equals(Role.ADMIN_USER))
                        user_array_list.get(i).setRole(Role.ORDINARY_USER);
                    System.out.println("Новая роль: " + user_array_list.get(i).getRole());
                    System.out.println();
                    return;
                }
            }
            // при проходе цикла ID не нашлось
            System.out.println("Такого пользователя не существует.");
        }
        catch (Exception exception) {
            System.out.println("Введен неверный ID.");
            input.nextLine();
        }
    }
}
