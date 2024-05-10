package ex2.utils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSet<T> {
    private Set<T> set;
    private Lock lock;

    public ConcurrentSet() {
        this.set = new HashSet<>();
        this.lock = new ReentrantLock();
    }

    public boolean add(T element) {
        lock.lock();
        try {
            return set.add(element);
        } finally {
            lock.unlock();
        }
    }

    public boolean contains(T element) {
        lock.lock();
        try {
            return set.contains(element);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return set.size();
        } finally {
            lock.unlock();
        }
    }

    public boolean addAll(Collection<? extends T> elements) {
        lock.lock();
        try {
            return set.addAll(elements);
        } finally {
            lock.unlock();
        }
    }

}
