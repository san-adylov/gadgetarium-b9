package peaksoft.house.gadgetariumb9.enums;
public enum Status {
    EXPECTATION("Ожидание"),
    PROCESSING("В обработке"),
    COURIER_ON_THE_WAY("Курьер в пути"),
    DELIVERED("Доставлен"),
    CANCELED("Отменен"),
    READY_FOR_DELIVERY("Готово для доставки"),
    RECEIVED("Получено");
    Status(String value) {
    }
}
