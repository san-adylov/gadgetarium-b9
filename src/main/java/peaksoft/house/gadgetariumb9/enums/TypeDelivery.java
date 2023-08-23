package peaksoft.house.gadgetariumb9.enums;

public enum TypeDelivery {
    PICKUP("Самовызов"),
    DELIVERY("Доставка");

    private final String value;

    TypeDelivery(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
