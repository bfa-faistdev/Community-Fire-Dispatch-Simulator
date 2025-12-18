/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package at.faistdev.fwlstsim.dataaccess.cache;

import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public abstract class SingleListCache<E> {

    protected final ArrayList<E> entities;

    protected SingleListCache() {
        this.entities = new ArrayList<>();
    }

    public synchronized void add(E entity) {
        entities.add(entity);
    }

    public synchronized E get(int idx) {
        return entities.get(idx);
    }

    public synchronized int size() {
        return entities.size();
    }

    public synchronized ArrayList<E> getAll() {
        return entities;
    }
}
