package com.popov.service;

import com.popov.Repository.FruitRepository;
import com.popov.Repository.SuppliersRepository;
import com.popov.domain.Fruit;
import com.popov.domain.Suppliers;
import com.popov.exceptions.NoSuchSuppliersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class SuppliersService {
    @Autowired
    SuppliersRepository suppliersRepository;
    @Autowired
    FruitRepository fruitRepository;

    public Suppliers getSuppliers(Long suppliers_id) throws NoSuchSuppliersException {
        Suppliers suppliers = suppliersRepository.findById(suppliers_id).get();//2.0.0.M7
        if (suppliers == null) throw new NoSuchSuppliersException();
        return suppliers;
    }

    public Set<Suppliers> getSupplierssByFruitId(Long book_id) throws NoSuchSuppliersException {
        Fruit fruit = fruitRepository.findById(book_id).get();//2.0.0.M7
        if (fruit == null) throw new NoSuchSuppliersException();
        return fruit.getSuppliers();
    }

    public List<Suppliers> getAllSupplierss() {
        return suppliersRepository.findAll();
    }

    @Transactional
    public void createSuppliers(Suppliers suppliers) {
        suppliersRepository.save(suppliers);
    }

    @Transactional
    public void updateSuppliers(Suppliers uSuppliers, Long suppliers_id) throws  NoSuchSuppliersException {
        Suppliers suppliers = suppliersRepository.findById(suppliers_id).get();//2.0.0.M7
        if (suppliers == null) throw new NoSuchSuppliersException();
        //update
        suppliers.setName(uSuppliers.getName());
        suppliers.setCity(uSuppliers.getCity());

    }

    @Transactional
    public void deleteSuppliers(Long suppliers_id) throws NoSuchSuppliersException {
        Suppliers suppliers = suppliersRepository.findById(suppliers_id).get();//2.0.0.M7

        if (suppliers == null) throw new NoSuchSuppliersException();
        suppliersRepository.delete(suppliers);
    }
}
