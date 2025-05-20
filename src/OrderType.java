public enum OrderType {
    ASC("ASC"), DESC("DESC"), NONE("NONE");

    OrderType(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
