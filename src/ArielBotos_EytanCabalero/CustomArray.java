package ArielBotos_EytanCabalero;
import java.io.Serializable;
import java.util.ArrayList;


public class CustomArray<T>  implements Serializable {
    private final ArrayList<T> items = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public boolean add(T item) {
        if (!items.contains(item)) {
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

    public int size() {
        return items.size();
    }

    public T[] toArray(T[] a) {
        return items.toArray(a);
    }
}
