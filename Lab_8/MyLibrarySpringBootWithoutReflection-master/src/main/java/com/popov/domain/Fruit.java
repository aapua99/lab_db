package com.popov.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "fruits")
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "name_shop", referencedColumnName = "id")
    private Shop shop;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "suppliers_fruits",
            schema = "popov15",
            joinColumns = {@JoinColumn(
                    name = "fruit",
                    nullable = false
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "supplier",
                    nullable = false
            )}
    )
    private Set<Suppliers> suppliers;


    public Set<Suppliers> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<Suppliers> suppliers) {
        this.suppliers = suppliers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fruit that = (Fruit) o;

        if (id != that.id) return false;
        if (shop != that.shop) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Math.toIntExact(id);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }


}
