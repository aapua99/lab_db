package com.popov.DTO;

import com.popov.controller.SuppliersController;
import com.popov.domain.Fruit;
import com.popov.domain.Shop;
import com.popov.domain.Suppliers;
import com.popov.exceptions.NoSuchFruitException;
import com.popov.exceptions.NoSuchSuppliersException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class FruitDTO extends ResourceSupport {
    Fruit fruit;
    public FruitDTO(Fruit fruit, Link selfLink) throws NoSuchSuppliersException, NoSuchFruitException {
        this.fruit=fruit;
        add(selfLink);
        add(linkTo(methodOn(SuppliersController.class).getSuppliersByFruitID(fruit.getId())).withRel("suppliers"));
    }

    public Long getFruitId() {
        return fruit.getId();
    }

    public String getFruitName() {
        return fruit.getName();
    }

    public Shop getShop() {
        return fruit.getShop();
    }

    public Set<Suppliers> getSuppliers(){
        return fruit.getSuppliers();
    }


}
