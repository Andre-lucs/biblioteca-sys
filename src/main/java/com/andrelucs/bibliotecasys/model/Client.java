package com.andrelucs.bibliotecasys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telefone;
    private Integer idade;
    private String cpf;
    private String nome;
    @JsonIgnore
    private String senha;
    @Column(unique = true)
    private String email;

    @JsonIgnore
    public String getSenha(){return this.senha;}

    @JsonProperty
    public void setSenha(String senha){this.senha = senha;};

    @JsonIgnore
    public boolean isInvalid() {
        return nome == null || senha == null || email == null || telefone == null || idade == null || cpf == null;
    }

    public Client(String telefone, Integer idade, String cpf, String nome, String senha, String email) {
        this.telefone = telefone;
        this.idade = idade;
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

}
