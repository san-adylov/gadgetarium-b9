package peaksoft.house.gadgetariumb9.enums;

public enum Status {
    PENDING("В ожидании"),
    IN_PROCESSING("В обработке"),
    READY_FOR_DELIVERY("Готов к выдаче"),
    RECEIVED("Получен"),
    CANCEL("Отменить"),
    COURIER_ON_THE_WAY("Курьер в пути"),
    DELIVERED("Доставлен");

    Status(String value) {
    }
}
