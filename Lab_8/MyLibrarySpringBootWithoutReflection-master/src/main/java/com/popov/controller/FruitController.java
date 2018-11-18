package com.popov.controller;


import com.popov.DTO.FruitDTO;
import com.popov.domain.Fruit;

import com.popov.domain.Shop;
import com.popov.exceptions.AlreadyExistsFruitInSuppliersException;
import com.popov.exceptions.FruitHasNotSupplierskException;
import com.popov.exceptions.NoSuchFruitException;
import com.popov.exceptions.NoSuchSuppliersException;
import com.popov.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class FruitController {
    @Autowired
    FruitService fruitService;

    @GetMapping(value = "/api/fruit/suppliers/{suppliers_id}")
    public ResponseEntity<List<FruitDTO>> getFruitsBySuppliersID(@PathVariable Long suppliers_id) throws NoSuchFruitException, NoSuchSuppliersException {
        Set<Fruit> fruitList = fruitService.getSuppliersByFruitID(suppliers_id);
        Link link = linkTo(methodOn(FruitController.class).getAllFruits()).withSelfRel();

        List<FruitDTO> fruitsDTO = new ArrayList<>();
        for (Fruit entity : fruitList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            FruitDTO dto = new FruitDTO(entity, selfLink);
            fruitsDTO.add(dto);
        }

        return new ResponseEntity<>(fruitsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/fruit/{fruit_id}")
    public ResponseEntity<FruitDTO> getFruit(@PathVariable Long fruit_id) throws NoSuchFruitException, NoSuchSuppliersException {
        Fruit fruit = fruitService.getFruit(fruit_id);
        Link link = linkTo(methodOn(FruitController.class).getFruit(fruit_id)).withSelfRel();

        FruitDTO fruitDTO = new FruitDTO(fruit, link);

        return new ResponseEntity<>(fruitDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/fruit")
    public ResponseEntity<List<FruitDTO>> getAllFruits() throws NoSuchFruitException, NoSuchSuppliersException {
        List<Fruit> fruitList = fruitService.getAllFruits();
        Link link = linkTo(methodOn(FruitController.class).getAllFruits()).withSelfRel();

        List<FruitDTO> fruitsDTO = new ArrayList<>();
        for (Fruit entity : fruitList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            FruitDTO dto = new FruitDTO(entity, selfLink);
            fruitsDTO.add(dto);
        }

        return new ResponseEntity<>(fruitsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/fruit")
    public ResponseEntity<FruitDTO> addFruit(@RequestBody Fruit newFruit) throws NoSuchSuppliersException, NoSuchFruitException {
        fruitService.createFruit(newFruit);
        Link link = linkTo(methodOn(FruitController.class).getFruit(newFruit.getId())).withSelfRel();

        FruitDTO fruitDTO = new FruitDTO(newFruit, link);

        return new ResponseEntity<>(fruitDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/fruit/{fruit_id}")
    public ResponseEntity<FruitDTO> updateFruit(@RequestBody Fruit uFruit, @PathVariable Long fruit_id) throws NoSuchFruitException, NoSuchSuppliersException {
        fruitService.updateFruit(uFruit, fruit_id);
        Fruit fruit = fruitService.getFruit(fruit_id);
        Link link = linkTo(methodOn(FruitController.class).getFruit(fruit_id)).withSelfRel();

        FruitDTO fruitDTO = new FruitDTO(fruit, link);

        return new ResponseEntity<>(fruitDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/fruit/{fruit_id}")
    public  ResponseEntity deleteFruit(@PathVariable Long fruit_id) throws NoSuchSuppliersException {
        fruitService.deleteFruit(fruit_id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping(value = "/api/fruit/{fruit_id}/suppliers/{suppliers_id}")
    public  ResponseEntity<FruitDTO> addBookForFruit(@PathVariable Long fruit_id, @PathVariable Long suppliers_id)
            throws NoSuchFruitException,  NoSuchSuppliersException, AlreadyExistsFruitInSuppliersException {
        fruitService.addSuppliersForFruit(fruit_id,suppliers_id);
        Fruit fruit = fruitService.getFruit(fruit_id);
        Link link = linkTo(methodOn(FruitController.class).getFruit(fruit_id)).withSelfRel();

        FruitDTO fruitDTO = new FruitDTO(fruit, link);

        return new ResponseEntity<>(fruitDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api?fruit/addShop/{fruit_id}/shop/{newShop}")
    public ResponseEntity<String> addShop(@PathVariable Long newShop, @PathVariable Long fruit_id){
        fruitService.addShopForFruit(newShop, fruit_id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/fruit/{fruit_id}/suppliers/{suppliers_id}")
    public  ResponseEntity<FruitDTO> removeBookForFruit(@PathVariable Long fruit_id, @PathVariable Long suppliers_id)
            throws NoSuchFruitException, NoSuchSuppliersException, FruitHasNotSupplierskException {
        fruitService.removeBookForPerson(fruit_id,suppliers_id);
        Fruit fruit = fruitService.getFruit(fruit_id);
        Link link = linkTo(methodOn(FruitController.class).getFruit(fruit_id)).withSelfRel();

        FruitDTO fruitDTO = new FruitDTO(fruit, link);

        return new ResponseEntity<>(fruitDTO, HttpStatus.OK);
    }
}
