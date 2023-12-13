package com.example.voiture.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Long Id;
    private String Nom;
    private Float Age;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public Float getAge() {
        return Age;
    }

    public void setAge(Float age) {
        Age = age;
    }

//    public Client(Long id, String nom, Float age) {
//        Id = id;
//        Nom = nom;
//        Age = age;
//    }
}