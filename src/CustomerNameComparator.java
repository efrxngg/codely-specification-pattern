import java.util.Comparator;

public class CustomerNameComparator implements CustomerComparatorFactory {
    @Override
    public Comparator<Customer> create(Order order) {
        return order.getType() == OrderType.ASC
                ? Comparator.comparing(Customer::getName)
                : Comparator.comparing(Customer::getName).reversed();

    }
}
