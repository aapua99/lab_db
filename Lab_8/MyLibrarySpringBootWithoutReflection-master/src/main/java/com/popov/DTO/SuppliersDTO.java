package com.popov.DTO;

import com.popov.controller.FruitController;
import com.popov.domain.Fruit;
import com.popov.domain.Suppliers;
import com.popov.exceptions.NoSuchFruitException;
import com.popov.exceptions.NoSuchSuppliersException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class SuppliersDTO extends ResourceSupport {
    Suppliers suppliers;
    public SuppliersDTO(Suppliers newSuppliers, Link link) throws NoSuchFruitException, NoSuchSuppliersException {
        this.suppliers=newSuppliers;
            add(link);
            add(linkTo(methodOn(FruitController.class).getFruitsBySuppliersID(suppliers.getId())).withRel("fruits"));
        }
    public Long getSuppliersId () {
        return suppliers.getId();
    }
    public String getSuppliersName () {
        return suppliers.getName();
    }
    public String getSuppliersCity () {
        return suppliers.getCity();
    }
    public Set<Fruit> getFruits(){
        return suppliers.getFruits();
    }
    }

