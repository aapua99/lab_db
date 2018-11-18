package com.popov.service;


import com.popov.Repository.ShopRepository;
import com.popov.domain.Shop;
import com.popov.exceptions.NoSuchShopException;
import com.popov.exceptions.NoSuchSuppliersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShopService {
    @Autowired
    ShopRepository shopRepository;


    public Shop getShop(Long shop_id) throws NoSuchShopException {
        Shop shop = shopRepository.findById(shop_id).get();//2.0.0.M7
        if (shop == null) throw new NoSuchShopException();
        return shop;
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Transactional
    public void createShop(Shop shop) {
        shopRepository.save(shop);
    }

    @Transactional
    public void updateShop(Shop uShop, Long shop_id) throws  NoSuchSuppliersException {
        Shop shop = shopRepository.findById(shop_id).get();//2.0.0.M7
        if (shop == null) throw new NoSuchSuppliersException();
        //update
        shop.setName(uShop.getName());
        shop.setAddress(uShop.getAddress());
        shop.setManager(uShop.getManager());

    }

    @Transactional
    public void deleteShop(Long shop_id) throws NoSuchSuppliersException {
        Shop shop = shopRepository.findById(shop_id).get();//2.0.0.M7

        if (shop == null) throw new NoSuchSuppliersException();
        shopRepository.delete(shop);
    }
}
