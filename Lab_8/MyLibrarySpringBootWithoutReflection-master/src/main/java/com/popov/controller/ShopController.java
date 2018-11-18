package com.popov.controller;

import com.popov.DTO.ShopDTO;
import com.popov.domain.Shop;
import com.popov.exceptions.NoSuchShopException;
import com.popov.exceptions.NoSuchSuppliersException;
import com.popov.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ShopController {
    @Autowired
    ShopService shopService;

//    @GetMapping(value = "/api/shop/person/{person_id}")
//    public ResponseEntity<List<ShopDTO>> getShopsByPersonID(@PathVariable Long person_id) throws NoSuchPersonException, NoSuchPersonException, NoSuchSuppliersException {
//        Set<Shop> shopList = shopService.getShopsByPersonId(person_id);
//        Link link = linkTo(methodOn(ShopController.class).getAllShops()).withSelfRel();
//
//        List<ShopDTO> shopsDTO = new ArrayList<>();
//        for (Shop entity : shopList) {
//            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
//            ShopDTO dto = new ShopDTO(entity, selfLink);
//            shopsDTO.add(dto);
//        }
//
//        return new ResponseEntity<>(shopsDTO, HttpStatus.OK);
//    }

    @GetMapping(value = "/api/shop/{shop_id}")
    public ResponseEntity<ShopDTO> getShop(@PathVariable Long shop_id) throws NoSuchShopException {
        Shop shop = shopService.getShop(shop_id);
        Link link = linkTo(methodOn(ShopController.class).getShop(shop_id)).withSelfRel();

        ShopDTO shopDTO = new ShopDTO(shop, link);

        return new ResponseEntity<>(shopDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/shop")
    public ResponseEntity<List<ShopDTO>> getAllShops() throws NoSuchShopException {
        List<Shop> shopList = shopService.getAllShops();
        Link link = linkTo(methodOn(ShopController.class).getAllShops()).withSelfRel();

        List<ShopDTO> shopsDTO = new ArrayList<>();
        for (Shop entity : shopList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            ShopDTO dto = new ShopDTO(entity, selfLink);
            shopsDTO.add(dto);
        }

        return new ResponseEntity<>(shopsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/shop")
    public ResponseEntity<ShopDTO> addShop(@RequestBody Shop newShop) throws NoSuchShopException {
        shopService.createShop(newShop);
        Link link = linkTo(methodOn(ShopController.class).getShop(newShop.getId())).withSelfRel();

        ShopDTO shopDTO = new ShopDTO(newShop, link);

        return new ResponseEntity<>(shopDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/shop/{shop_id}")
    public ResponseEntity<ShopDTO> updateShop(@RequestBody Shop uShop, @PathVariable Long shop_id) throws NoSuchShopException, NoSuchSuppliersException {
        shopService.updateShop(uShop, shop_id);
        Shop shop = shopService.getShop(shop_id);
        Link link = linkTo(methodOn(ShopController.class).getShop(shop_id)).withSelfRel();

        ShopDTO shopDTO = new ShopDTO(shop, link);

        return new ResponseEntity<>(shopDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/shop/{shop_id}")
    public  ResponseEntity deleteShop(@PathVariable Long shop_id) throws NoSuchSuppliersException {
        shopService.deleteShop(shop_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

