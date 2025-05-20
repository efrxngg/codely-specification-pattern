import java.util.Comparator;

public interface CustomerComparatorFactory {
        Comparator<Customer> create(Order order);
}
