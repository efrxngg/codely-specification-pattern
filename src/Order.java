public class Order {
    private String by;
    private OrderType type;

    public Order(String by, OrderType type) {
        this.by = by;
        this.type = type;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }
}
