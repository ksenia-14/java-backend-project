/**
 * AdminActions.java
 * В данном файле реализовано перечисление действий,
 * доступных администратору магазина.
 */

package attestation;

public enum AdminActions {
    VIEW_PRODUCTS,    // просмотр доступных товаров для покупки
    ADD_PRODUCT,      // добавление товара
    REMOVE_PRODUCT,   // удаление товара
    VIEW_USERS,       // просмотр информации о пользователях
    CHANGE_ROLE_USER, // смена роли пользователя
    EXIT              // выход из аккаунта
}
