package com.danon.webshopdatabase.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class CUser {
    @Id
    //@GenericGenerator(name="UUIDGenerator", strategy = "uuid2")
    //@GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false) //нельзя обновлять и оставлять пустой колонку
    private UUID id;

    @Column(name = "login", updatable = true, nullable = false)

    private String login;

    @Column(name = "name", updatable = true, nullable = false)
    private String name;

    @Column(name = "sex", updatable = true, nullable = false)
    private boolean sex;

    @Column(name = "date_birth", columnDefinition = "DATE")
    private LocalDate dateBirth;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)//не ставить одновременно EAGER тут и в COrder
    @JsonIdentityReference(alwaysAsId = true)
    private List<COrder> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CUser cUser = (CUser) o;
        return sex == cUser.sex && Objects.equals(id, cUser.id) && Objects.equals(login, cUser.login) && Objects.equals(name, cUser.name) && Objects.equals(dateBirth, cUser.dateBirth) && Objects.equals(orders, cUser.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, sex, dateBirth, orders);
    }

    //ID
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    //Login
    public String getLogin() {return login;}
    public void setLogin(String log) {
        if (log.length() < 50) {
            this.login = log;
        }
    }
    //Name
    public String getName() {return name;}
    public void setName(String name){
        if (name.length() > 2 && name.length() < 50){
            this.name = name;
        }
    }
    //Sex
    public boolean getSex(){return sex;}
    public void setSex(boolean sex){this.sex = sex;}
    //Date of Birth
    public LocalDate getDateBirth(){return dateBirth;}
    public void setDateBirth(LocalDate DB){
        int age = CUser.getAge(DB);
        if (age > 1 && age < 120){
            this.dateBirth = DB;
        }
    }
    public int getAge(){
        LocalDate now = LocalDate.now();
        return now.getYear() - this.dateBirth.getYear();
    }
    public static int getAge(LocalDate date){
        LocalDate now = LocalDate.now();
        if (date == null){
            return now.getYear();
        }
        return now.getYear()-date.getYear();
    }
    public CUser(){
        id = null;
        login = "";
        name = "";
        sex=true;
        dateBirth=LocalDate.now();
    }
    //Конструктор
    public CUser(UUID id, String login, String name, LocalDate dateBirth, boolean sex){
        this.id = id;
        setLogin(login);
        setName(name);
        setDateBirth(dateBirth);
        setSex(sex);
    }

    public List<COrder> getOrders() {
        return orders;
    }
}