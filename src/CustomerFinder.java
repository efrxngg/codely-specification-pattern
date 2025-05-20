import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CustomerFinder {

    private final Map<FilterOperator, PredicateWrapper<Customer, Filter>> OPERATOR_MAP = Map.of(
            FilterOperator.EQUAL, this::matchEquals,
            FilterOperator.CONTAINS, this::matchContains
    );

    private final Map<String, CustomerComparatorFactory> comparatorFieldFactories = Map.of(
            "id", new CustomerIdComparator(),
            "name", new CustomerNameComparator()
    );

    /**
     * Applies the given criteria to filter the list of customers based on specified conditions.
     */
    public List<Customer> apply(List<Customer> customers, Criteria criteria) {
        return customers.stream()
                .filter(c -> matchsOperator(c, criteria.getFilters()))
                .sorted((c1, c2) -> applyOrders(c1, c2, criteria.getOrders()))
                .toList();
    }

    private Boolean matchContains(Customer customer, Filter filter) {
        String filterValue = filter.getValue();
        return switch (filter.getName()) {
            case "id" -> customer.getId().contains(filterValue);
            case "name" -> customer.getName().contains(filterValue);
            default -> false;
        };
    }

    /**
     * Evaluates whether a given customer satisfies all the specified filters based on their operators.
     * Similar to and concatenation
     */
    private Boolean matchsOperator(Customer customer, List<Filter> filters) {
        return filters.stream()
                .allMatch(f -> OPERATOR_MAP.get(f.getOperator()).test(customer, f));
    }

    private Boolean matchEquals(Customer customer, Filter filter) {
        String filterValue = filter.getValue();
        return switch (filter.getName()) {
            case "id" -> customer.getId().equals(filterValue);
            case "name" -> customer.getName().equals(filterValue);
            default -> false;
        };
    }

    private int applyOrders(Customer customer1, Customer customer2, Orders orders) {
        Comparator<Customer> combinedComparator = orders.getOrders().stream()
                .map(this::getComparator)
                .reduce((c1, c2) -> 0, Comparator::thenComparing);

        return combinedComparator.compare(customer1, customer2);
    }

    private Comparator<Customer> getComparator(Order o) {
        return comparatorFieldFactories
                .getOrDefault(o.getBy(), (order) -> (c1, c2) -> 0)
                .create(o);
    }

}