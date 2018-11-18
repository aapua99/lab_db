package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "suppliers", schema = "popov15", catalog = "")
public class SuppliersEntity {
    private String name;
    private String city;
    private List<FruitsEntity> fruits;

    public SuppliersEntity(){

    }

    public SuppliersEntity(String name, String city){
        this.name=name;
        this.city=city;
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
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SuppliersEntity that = (SuppliersEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(
            name = "suppliers_fruits",
            schema = "popov15",
            joinColumns = {@JoinColumn(
                    name = "supplier",
                    nullable = false
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "fruit",
                    nullable = false
            )}
    )
    public List<FruitsEntity> getFruits() {
        return fruits;
    }

    public void setFruits(List<FruitsEntity> fruits) {
        this.fruits = fruits;
    }

    public void addFruit(FruitsEntity fruitsEntity) {
        if (!this.getFruits().contains(fruitsEntity)) {
            this.getFruits().add(fruitsEntity);
        }

    }
    public void deleteFruits(FruitsEntity fruitsEntity){
        for (int i=0; i<this.getFruits().size(); i++){
            if(fruitsEntity==this.getFruits().get(i)){
                this.getFruits().remove(i);
            }
        }
    }
}
