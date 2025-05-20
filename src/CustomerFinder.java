import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CustomerFinder {

    private final Map<FilterOperator, PredicateWrapper<Customer, Filter>> OPERATOR_MAP = Map.of(
            FilterOperator.EQUAL, this::matchEquals,
            FilterOperator.CONTAINS, this::matchContains
    );

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

    private int applyOrders(Customer customer1, Customer customer2, Orders orders) {
        Stream<Comparator<Customer>> comparators = orders.getOrders().stream()
                .map(o -> {
                    Comparator<Customer> comparator;
                    switch (o.getBy()) {
                        case "id": {
                            comparator = (c1, c2) ->
                                    switch (o.getType()) {
                                        case ASC -> c1.getId().compareTo(c2.getId());
                                        case DESC -> c2.getId().compareTo(c1.getId());
                                        default -> 0;
                                    };
                            break;
                        }
                        case "name": {
                            comparator = (c1, c2) ->
                                    switch (o.getType()) {
                                        case ASC -> c1.getName().compareTo(c2.getName());
                                        case DESC -> c2.getName().compareTo(c1.getName());
                                        default -> 0;
                                    };
                            break;
                        }

                        default: {
                            comparator = (c1, c2) -> 0;
                            break;
                        }
                    }

                    return comparator;
                });

        // Creamos un comparador inicial que siempre devuelve 0 (igualdad)
        // Esto es necesario para que luego podamos encadenar otros comparadores
        Comparator<Customer> combinedComparator = (c1, c2) -> 0;
        // Reducimos el stream de comparadores a un único comparador combinado
        // Cada comparador en el stream se añade al 'combinedComparator' usando thenComparing
        // Esto asegura que los comparadores se apliquen en el orden en que aparecen en la lista de órdenes
        combinedComparator = comparators.reduce(
                combinedComparator, // Identidad: el comparador inicial que no hace nada
                Comparator::thenComparing // Acumulador: combina el comparador actual con el siguiente
        );

        return combinedComparator.compare(customer1, customer2);
    }

}