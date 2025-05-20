import java.util.List;

public class Criteria {
    private Filters filters;
    private Orders orders;
    private Integer offset;
    private Integer limit;

    public Criteria(Filters filters, Orders orders, Integer offset, Integer limit) {
        this.filters = filters;
        this.orders = orders;
        this.offset = offset;
        this.limit = limit;
    }

    public List<Filter> getFilters() {
        return filters.getFilters();
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
