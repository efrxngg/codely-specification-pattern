public class Filter {
    private FilterField name;
    private FilterOperator operator;
    private FieldValue value;

    public Filter(FilterField name, FilterOperator operator, FieldValue value) {
        this.name = name;
        this.operator = operator;
        this.value = value;
    }

    public String getName() {
        return name.getName();
    }

    public void setName(FilterField name) {
        this.name = name;
    }

    public String getValue() {
        return value.getValue();
    }

    public void setValue(FieldValue value) {
        this.value = value;
    }

    public FilterOperator getOperator() {
        return operator;
    }

    public void setOperator(FilterOperator operator) {
        this.operator = operator;
    }
}
