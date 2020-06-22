package org.cis.optur.lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;

public abstract class Repository<T> implements Iterable<T>{

    private ArrayList<T> instances = new ArrayList<T>();

    public Iterator<T> iterator() {
        Iterator<T> iterator = instances.iterator();
        return iterator;
    }

    public T save(T t){
        this.instances.add(t);
        return t;
    }

    public ArrayList<T> getAll(){
        return instances;
    }

    public int size(){
        return instances.size();
    }

    public void printAll() {
        System.out.println("============");
        instances.forEach(t -> {
            System.out.println(t.toString());
        });
    }

    public T getAtIndex(int index){
        return instances.get(index);
    }

    public Object[] toArray(){
        Object[] array = new Object[instances.size()];
        for (int i = 0; i<instances.size(); i++){
            array[i] = instances.get(i);
        }
        return array;
    }
}
