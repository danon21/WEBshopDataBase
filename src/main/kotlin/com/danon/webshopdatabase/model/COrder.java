package com.danon.webshopdatabase.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class COrder {

    @Id
    //@GenericGenerator(name="UUIDGenerator", strategy = "uuid2")
    //@GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private
    UUID id;

    @Column(name = "purchase_date", columnDefinition = "DATE")
    LocalDate purchase_date;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    CUser owner;

//    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "good", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "good", nullable = false)
    CGood good;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        COrder order = (COrder) o;
        return Objects.equals(id, order.id) && Objects.equals(purchase_date, order.purchase_date) && Objects.equals(owner, order.owner) && Objects.equals(good, order.good);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, purchase_date, owner, good);
    }

    //Возвращение и запись информации по пользователю
    public CUser getOwner() {return owner;}
    public void setOwner(CUser owner) {this.owner = owner;}

    //Возвращение и запись информации по товару
    public CGood getGood(){return good;}
    public void setGood(CGood good) {this.good = good;}

    //Возвращение и запись информации по дате покупки
    public LocalDate getPurchase_date(){return purchase_date;}
    public void setPurchase_date(LocalDate purchase_date) {this.purchase_date = purchase_date;}

    //Возвращение и запись информации по id заказа
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public COrder(){
        id = null;
        owner = null;
        good = null;
        purchase_date = LocalDate.now();
    }

    public COrder(UUID id, CUser owner, CGood good, LocalDate date){
        setId(id);
        setOwner(owner);
        setGood(good);
        setPurchase_date(date);
    }
}
