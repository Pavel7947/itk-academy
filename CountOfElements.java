import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CountOfElements<T> {

    public Map<T, Integer> count(Collection<T> collection) {
        Map<T, Integer> result = new HashMap<>();
        for (T t : collection) {
            int value = result.getOrDefault(t, 0);
            result.put(t, ++value);
        }
        return result;
    }

}
