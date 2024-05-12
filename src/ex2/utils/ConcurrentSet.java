package ex2.utils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSet<T> {
    private final Set<T> set;
    private final Lock lock;

    public ConcurrentSet() {
        this.set = new HashSet<>();
        this.lock = new ReentrantLock();
    }

    public boolean add(final T element) {
        this.lock.lock();
        try {
            return this.set.add(element);
        } finally {
            this.lock.unlock();
        }
    }

    public boolean contains(final T element) {
        this.lock.lock();
        try {
            return this.set.contains(element);
        } finally {
            this.lock.unlock();
        }
    }

    public int size() {
        this.lock.lock();
        try {
            return this.set.size();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean addAll(final Collection<? extends T> elements) {
        this.lock.lock();
        try {
            return this.set.addAll(elements);
        } finally {
            this.lock.unlock();
        }
    }

}
