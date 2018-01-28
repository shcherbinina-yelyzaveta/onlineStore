package com.company.store.database;

import com.company.store.model.Cart;

import java.util.List;

public class CartDAO extends AbstractDAO<Integer, Cart> {
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart findEntityById(Integer id) {
        return null;
    }

    @Override
    public Integer findIdByEntity(Cart entity) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Integer create(Cart entity) {
        return null;
    }

    @Override
    public boolean update(Cart entity) {
        return false;
    }
}
