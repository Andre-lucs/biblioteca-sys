package com.andrelucs.bibliotecasys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "administrador")
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer nivel;
    private String nome;
    @JsonIgnore
    private String senha;
    @Column(unique = true)
    private String email;

    @JsonIgnore
    public String getSenha() {
        return senha;
    }
    @JsonProperty
    public void setSenha(String senha) {
        this.senha = senha;
    }

    @JsonIgnore
    public boolean isValid(){
        return nome != null && senha != null && email != null && nivel != null;
    }

    public Admin(Integer nivel, String nome, String senha, String email) {
        if(nivel > 3) nivel = 3;
        if(nivel < 1) nivel = 1;
        this.nivel = nivel;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    @JsonIgnore
    public int getAccessLevel() {
        return getNivel();
    }
}
