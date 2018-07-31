package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Database;
import com.codecool.shop.model.Supplier;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {
    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoJDBC instance = null;
    Database db = Database.getInstance();

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoJDBC() {
    }

    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        //TODO
    }

    @Override
    public Supplier find(int id) {
        return null;
        //TODO
    }

    @Override
    public Supplier find(String name) throws ElementNotFoundException {
        return null;
        //TODO
    }

    @Override
    public void remove(int id) {
        //TODO
    }

    @Override
    public List<Supplier> getAll() {
        //TODO
        return null;
    }


}
