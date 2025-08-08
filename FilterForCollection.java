import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterForCollection<T> {

    public List<T> filter(Collection<T> collection, Filter<T> filter) {
        List<T> resultList = new ArrayList<>();
        for (T t : collection) {
            T result = filter.apply(t);
            resultList.add(result);
        }
        return resultList;
    }
}

