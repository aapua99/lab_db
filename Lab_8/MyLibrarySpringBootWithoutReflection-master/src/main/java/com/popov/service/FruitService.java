package com.popov.service;

import com.popov.Repository.FruitRepository;
import com.popov.Repository.ShopRepository;
import com.popov.Repository.SuppliersRepository;
import com.popov.domain.Fruit;
import com.popov.domain.Shop;
import com.popov.domain.Suppliers;
import com.popov.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class FruitService {
    @Autowired
    FruitRepository fruitRepository;

    @Autowired
    SuppliersRepository suppliersRepository;

    @Autowired
    ShopRepository shopRepository;

    public Set<Fruit> getSuppliersByFruitID(Long person_id) throws NoSuchFruitException {
        Suppliers suppliers = suppliersRepository.findById(person_id).get();//2.0.0.M7
        if (suppliers == null) throw new NoSuchFruitException();
        return suppliers.getFruits();
    }

    public Fruit getFruit(Long fruit_id) throws NoSuchFruitException {
//        Fruit fruit = fruitRepository.findOne(fruit_id);//1.5.9
        Fruit fruit = fruitRepository.findById(fruit_id).get();//2.0.0.M7
        if (fruit == null) throw new NoSuchFruitException();
        return fruit;
    }

    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    @Transactional
    public void createFruit(Fruit fruit) {
        fruitRepository.save(fruit);
    }

    @Transactional
    public void updateFruit(Fruit uFruit, Long fruit_id) throws NoSuchFruitException, NoSuchSuppliersException {
//        Fruit fruit = fruitRepository.findOne(fruit_id);//1.5.9
        Fruit fruit = fruitRepository.findById(fruit_id).get();//2.0.0.M7
        if (fruit == null) throw new NoSuchSuppliersException();
        //update
        fruit.setName(uFruit.getName());
        fruit.setShop(uFruit.getShop());

    }

    @Transactional
    public void deleteFruit(Long fruit_id) throws NoSuchSuppliersException {
//        Fruit fruit = fruitRepository.findOne(fruit_id);//1.5.9
        Fruit fruit = fruitRepository.findById(fruit_id).get();//2.0.0.M7

        if (fruit == null) throw new NoSuchSuppliersException();
//        if (fruit.getPersons().size() != 0) throw new ExistsPersonForFruitException();
        fruitRepository.delete(fruit);
    }

    @Transactional
    public void addShopForFruit(Long shop, Long fruit_id){
        Fruit fruit = fruitRepository.findById(fruit_id).get();//2.0.0.M7
        Shop newShop=shopRepository.findById(shop).get();
        System.out.println(shop.toString());
        fruit.setShop(newShop);
        fruitRepository.save(fruit);
    }

    @Transactional
    public void addSuppliersForFruit(Long suppliers_id, Long fruit_id)
            throws NoSuchFruitException, NoSuchSuppliersException, AlreadyExistsFruitInSuppliersException {
//        Person person = personRepository.findOne(person_id);//1.5.9
        Fruit fruit = fruitRepository.findById(fruit_id).get();//2.0.0.M7
        if (fruit == null) throw new NoSuchFruitException();
//        Book book = bookRepository.findOne(book_id);//1.5.9
        Suppliers suppliers = suppliersRepository.findById(suppliers_id).get();//2.0.0.M7
        if (suppliers == null) throw new NoSuchSuppliersException();
        if (fruit.getSuppliers().contains(suppliers)) throw new AlreadyExistsFruitInSuppliersException();
        fruit.getSuppliers().add(suppliers);
        fruitRepository.save(fruit);
    }

    @Transactional
    public void removeBookForPerson(Long person_id, Long suplliers_id)
            throws NoSuchFruitException, NoSuchSuppliersException, FruitHasNotSupplierskException {
        Fruit fruit = fruitRepository.findById(person_id).get();//2.0.0.M7
        if (fruit == null) throw new NoSuchFruitException();
//        Book book = bookRepository.findOne(book_id);//1.5.9
        Suppliers suppliers = suppliersRepository.findById(suplliers_id).get();//2.0.0.M7
        if (suppliers == null) throw new NoSuchSuppliersException();
        if (!fruit.getSuppliers().contains(suppliers)) throw new FruitHasNotSupplierskException();
        fruit.getSuppliers().remove(suppliers);
        fruitRepository.save(fruit);
    }
}
