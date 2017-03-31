package com.blackfat.common.utils.collection.type.primitive;

import com.blackfat.common.utils.collection.MapUtil;

import java.io.Serializable;
import java.util.*;

/**
 * JDK并没有提供ConcurrenHashSet，考虑到JDK的HashSet也是基于HashMap实现的，因此ConcurrenHashSet也由ConcurrenHashMap完成
 *
 * @author blackfat
 * @create 2017-03-10 下午2:25
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>, Serializable {

    private static final long serialVersionUID = -8672117787651310382L;

    private final Map<E, Boolean> m;

    private transient Set<E> s; // Its keySet


    public ConcurrentHashSet(){
        m = MapUtil.newConcurrentHashMap();
        s = m.keySet();
    }

    @Override
    public Iterator<E> iterator() {
        return s.iterator();
    }

    @Override
    public boolean isEmpty() {
        return m.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return m.containsKey(o);
    }

    @Override
    public Object[] toArray() {
        return s.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return s.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return m.put(e, Boolean.TRUE) == null;
    }

    @Override
    public boolean remove(Object o) {
        return m.remove(o) != null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return s.retainAll(c);
    }

    @Override
    public void clear() {
        m.clear();
    }

    @Override
    public String toString() {
        return s.toString();
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || s.equals(o);
    }

    @Override
    public int size() {
        return m.size();
    }


}
