package com.popov.controller;

import com.popov.DTO.SuppliersDTO;
import com.popov.domain.Suppliers;
import com.popov.exceptions.NoSuchFruitException;
import com.popov.exceptions.NoSuchSuppliersException;
import com.popov.service.SuppliersService;
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
public class SuppliersController {
    @Autowired
    SuppliersService suppliersService;

    @GetMapping(value = "/api/suppliers/fruit/{fruit_id}")
    public ResponseEntity<List<SuppliersDTO>> getSuppliersByFruitID(@PathVariable Long fruit_id) throws NoSuchSuppliersException, NoSuchFruitException {
        Set<Suppliers> suppliersList = suppliersService.getSupplierssByFruitId(fruit_id);
        Link link = linkTo(methodOn(SuppliersController.class).getAllSupplierss()).withSelfRel();

        List<SuppliersDTO> supplierssDTO = new ArrayList<>();
        for (Suppliers entity : suppliersList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            SuppliersDTO dto = new SuppliersDTO(entity, selfLink);
            supplierssDTO.add(dto);
        }

        return new ResponseEntity<>(supplierssDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/suppliers/{suppliers_id}")
    public ResponseEntity<SuppliersDTO> getSuppliers(@PathVariable Long suppliers_id) throws NoSuchFruitException, NoSuchSuppliersException {
        Suppliers suppliers = suppliersService.getSuppliers(suppliers_id);
        Link link = linkTo(methodOn(SuppliersController.class).getSuppliers(suppliers_id)).withSelfRel();

        SuppliersDTO suppliersDTO = new SuppliersDTO(suppliers, link);

        return new ResponseEntity<>(suppliersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/suppliers")
    public ResponseEntity<List<SuppliersDTO>> getAllSupplierss() throws NoSuchFruitException, NoSuchSuppliersException {
        List<Suppliers> suppliersList = suppliersService.getAllSupplierss();
        Link link = linkTo(methodOn(SuppliersController.class).getAllSupplierss()).withSelfRel();

        List<SuppliersDTO> supplierssDTO = new ArrayList<>();
        for (Suppliers entity : suppliersList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            SuppliersDTO dto = new SuppliersDTO(entity, selfLink);
            supplierssDTO.add(dto);
        }

        return new ResponseEntity<>(supplierssDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/suppliers")
    public ResponseEntity<SuppliersDTO> addSuppliers(@RequestBody Suppliers newSuppliers) throws NoSuchSuppliersException, NoSuchFruitException {
        suppliersService.createSuppliers(newSuppliers);
        Link link = linkTo(methodOn(SuppliersController.class).getSuppliers(newSuppliers.getId())).withSelfRel();

        SuppliersDTO suppliersDTO = new SuppliersDTO(newSuppliers, link);

        return new ResponseEntity<>(suppliersDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/suppliers/{suppliers_id}")
    public ResponseEntity<SuppliersDTO> updateSuppliers(@RequestBody Suppliers uSuppliers, @PathVariable Long suppliers_id) throws NoSuchSuppliersException, NoSuchFruitException {
        suppliersService.updateSuppliers(uSuppliers, suppliers_id);
        Suppliers suppliers = suppliersService.getSuppliers(suppliers_id);
        Link link = linkTo(methodOn(SuppliersController.class).getSuppliers(suppliers_id)).withSelfRel();

        SuppliersDTO suppliersDTO = new SuppliersDTO(suppliers, link);

        return new ResponseEntity<>(suppliersDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/suppliers/{suppliers_id}")
    public  ResponseEntity deleteSuppliers(@PathVariable Long suppliers_id) throws NoSuchSuppliersException {
        suppliersService.deleteSuppliers(suppliers_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
