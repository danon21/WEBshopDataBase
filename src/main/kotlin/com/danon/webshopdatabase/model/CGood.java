package com.danon.webshopdatabase.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "good")
public class CGood {
    @Id
    //@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    //@GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false) //нельзя обновлять и оставлять пустой колонку
    private UUID id;

    @Column(name = "Good_Name", updatable = true, nullable = false)
    private String GoodName;

    @Column(name = "price", updatable = true, nullable = false)
    private double price;

    @Column(name = "category", updatable = true, nullable = false)
    private String category;

    @OneToMany(mappedBy = "good", fetch = FetchType.LAZY)//не ставить одновременно EAGER тут в CUSER иначе все идет по пизде
    private List<COrder> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CGood cGood = (CGood) o;
        return Double.compare(cGood.price, price) == 0 && Objects.equals(id, cGood.id) && Objects.equals(GoodName, cGood.GoodName) && Objects.equals(category, cGood.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, GoodName, price, category);
    }

    //ID
    public UUID getId() {
        return id;
    }

    public void setGood_id(UUID gID) {
        this.id = gID;
    }

    //Name
    public String getGoodName() {
        return GoodName;
    }

    public void setGoodName(String goodName) {
        this.GoodName = goodName;
    }

    //Price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //Category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CGood() {
        id = null;
        GoodName = "";
        category = "";
        price = 0;
    }

    public CGood(UUID id, String good_name, double price, String category) {
        setGood_id(id);
        setGoodName(good_name);
        setPrice(price);
        setCategory(category);
    }


    public List<COrder> getOrders() {
        return orders;
    }
}