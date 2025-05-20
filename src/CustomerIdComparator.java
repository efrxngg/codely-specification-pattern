import java.util.Comparator;

public class CustomerIdComparator implements CustomerComparatorFactory {
    @Override
    public Comparator<Customer> create(Order order) {
        return order.getType() == OrderType.ASC
                ? Comparator.comparing(Customer::getId)
                : Comparator.comparing(Customer::getId).reversed();
    }
}
