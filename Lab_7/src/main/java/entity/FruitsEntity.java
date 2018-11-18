package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fruits", schema = "popov15")
public class FruitsEntity {
    private String name;
    private int price;
    private String shop;
    private List<SuppliersEntity> suppliers;

    public FruitsEntity(String name, int price, String shop){
        this.name=name;
        this.price=price;
        this.shop=shop;
    }

    public FruitsEntity(){

    }



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
    public List<SuppliersEntity> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SuppliersEntity> suppliers) {
        this.suppliers = suppliers;
    }

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FruitsEntity that = (FruitsEntity) o;

        if (price != that.price) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + price;
        return result;
    }
    @Column(name = "name_shop")
    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}
