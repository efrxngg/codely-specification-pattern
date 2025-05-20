public enum FilterOperator {
    EQUAL("="),
    NOT_EQUAL("!="),
    GT(">"),
    LT("<"),
    CONTAINS("CONTAINS"),
    NOT_CONTAINS("NO_CONTAINS");

    private final String value;

    FilterOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
