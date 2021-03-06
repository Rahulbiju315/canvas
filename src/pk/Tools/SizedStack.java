package pk.Tools;

import java.util.*;
/**
 * SizedStack for undo redo methods.
 */
public class SizedStack<T> extends Stack<T> {
    private final int maxSize;
    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public Object push(Object object) {
        while (this.size() > maxSize) {
            this.remove(0);
        }
        return super.push((T) object);
    }
}
