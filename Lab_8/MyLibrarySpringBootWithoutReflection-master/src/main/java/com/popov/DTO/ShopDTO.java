package com.popov.DTO;

import com.popov.domain.Fruit;
import com.popov.domain.Shop;
import com.popov.exceptions.NoSuchSuppliersException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class ShopDTO extends ResourceSupport {
    Shop shop;
    public ShopDTO(Shop shop, Link selfLink)  {
        this.shop=shop;
        add(selfLink);
    }

    public String getAdress() {
        return shop.getAddress();
    }

    public String getName() {
        return shop.getName();
    }

    public String  getManager() {
        return shop.getManager();
    }

    public List<Fruit> getFruitsList(){
        return shop.getFruitList();
    }
}
