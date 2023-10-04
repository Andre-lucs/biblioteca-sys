package com.andrelucs.bibliotecasys.services;

import com.andrelucs.bibliotecasys.model.Client;
import com.andrelucs.bibliotecasys.model.Login;
import com.andrelucs.bibliotecasys.repositories.ClientRepository;
import com.andrelucs.bibliotecasys.services.exception.BadRequestException;
import com.andrelucs.bibliotecasys.services.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client get(Long id){
        return clientRepository.findById(id).orElse(null);
    }

    public Client insert(Client obj){
        if(obj.isInvalid()) throw new BadRequestException("Some attribute is null. Object :"+obj);
        try {
            return clientRepository.save(obj);
        }catch (IllegalArgumentException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<Client> insertAll(List<Client> obj) {
        for (Client i : obj) {
            if(i.isInvalid()) throw new BadRequestException("Some attribute is null");
        }
        return clientRepository.saveAll(obj);
    }

    public void deleteById(Long id) {
        try{
            clientRepository.deleteById(id);
        }catch (IllegalArgumentException e){
            throw new BadRequestException("Given Id is invalid:"+id, "client");
        }
    }

    public Client updateById(Long id, Client obj, Boolean partialUpdate) {
        if(obj.isInvalid() && !partialUpdate) throw new BadRequestException("Update object has null atributes. "+ obj);
        Client c;
        try {
            c = clientRepository.getReferenceById(id);
        }catch (EntityNotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        updateClientData(c, obj);
        try {
            return clientRepository.save(c);
        }catch (IllegalArgumentException e){
            throw new BadRequestException("Invalid Data:"+ c, "client");
        }
    }

    private void updateClientData(Client Old, Client New){
        if(New.getCpf() != null) Old.setCpf(New.getCpf());
        if(New.getEmail() != null) Old.setEmail(New.getEmail());
        if(New.getNome() != null) Old.setNome(New.getNome());
        if(New.getIdade() != null) Old.setIdade(New.getIdade());
        if(New.getSenha() != null) Old.setSenha(New.getSenha());
        if(New.getTelefone() != null) Old.setTelefone(New.getTelefone());
    }

    public Client login(Login login) {
        return clientRepository.findByEmailAndSenha(login.email(), login.senha()).orElse(null);
    }
}
