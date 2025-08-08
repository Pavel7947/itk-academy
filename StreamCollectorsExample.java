import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

record Order(String product, double cost) {
}

public class StreamCollectorsExample {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );
        Map<String, Double> ordersMap = orders.stream().collect(Collectors.groupingBy(Order::product, Collectors.summingDouble(Order::cost)));
        System.out.println(ordersMap.entrySet().stream()
                .sorted((entry, entry2) -> Double.compare(entry2.getValue(), entry.getValue())).limit(3)
                .map(entry -> new Order(entry.getKey(), entry.getValue())).toList());
    }
}
