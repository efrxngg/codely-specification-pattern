import java.util.List;

public class CriteriaTest {

    private static final List<Customer> CUSTOMER_MOCK_API_RESULT = List.of(
            new Customer("1", "Efren Galarza"),
            new Customer("2", "Daniel Galarza"),
            new Customer("3", "Andres Galarza")
    );

    public static void main(String... args) {
//        var filter1 = new Filter(new FilterField("id"), FilterOperator.EQUAL, new FieldValue("1"));
        var filter2 = new Filter(new FilterField("name"), FilterOperator.CONTAINS, new FieldValue("Galarza"));
        var filters = new Filters(List.of(filter2));

        var order = new Order("name", OrderType.ASC);
        var orders = new Orders(List.of(order));

        var criteria = new Criteria(filters, orders, null, null);
        var customerFinder = new CustomerFinder();

        customerFinder.apply(CUSTOMER_MOCK_API_RESULT, criteria)
                .forEach(System.out::println);

        System.out.println("END");
    }

}
