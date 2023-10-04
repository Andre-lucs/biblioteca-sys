package com.andrelucs.bibliotecasys.services;

import com.andrelucs.bibliotecasys.model.Admin;
import com.andrelucs.bibliotecasys.model.Login;
import com.andrelucs.bibliotecasys.repositories.AdminRepository;
import com.andrelucs.bibliotecasys.services.exception.BadRequestException;
import com.andrelucs.bibliotecasys.services.exception.InvalidAcessException;
import com.andrelucs.bibliotecasys.services.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin insert(Admin newAdmin, Login login){
        if(!newAdmin.isValid()) throw new BadRequestException("Some attribute is null. Object :"+newAdmin);
        if(newAdmin.getAccessLevel() > 3) throw new BadRequestException("Invalid access level in new admin");
        boolean haveAuthority = authenticate(login, newAdmin.getAccessLevel(), null);
        if(!haveAuthority) throw new InvalidAcessException("Cannot create a admin with a higher level than yours");
        try {
            return adminRepository.save(newAdmin);
        }catch (IllegalArgumentException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public void deleteById(Long id, Login authorLogin) {
        try{
            boolean validAction = authenticate(authorLogin, 3, id);
            if(!validAction){
                throw new InvalidAcessException("Invalid Login: " + authorLogin);
            }
            adminRepository.deleteById(id);
        }catch (IllegalArgumentException e){
            throw new BadRequestException("Given Id is invalid:"+id, "admin");
        }
    }

    public void promote(Long id, Login authorLogin){
        Admin admToPromote;
        try{
            admToPromote = adminRepository.getReferenceById(id);
        }catch (EntityNotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        boolean validPromotion = authenticate(authorLogin, admToPromote.getNivel()+1, id);
        if(!validPromotion){
            throw new InvalidAcessException("Bad promotion author");
        }

        admToPromote.setNivel(admToPromote.getNivel()+1);
        adminRepository.save(admToPromote);
    }

    public void demote(Long id, Login authorLogin){
        Admin admToDemote;
        try{
            admToDemote = adminRepository.getReferenceById(id);
        }catch (EntityNotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        boolean validDemotion = authenticate(authorLogin, admToDemote.getAccessLevel(), id);
        if(!validDemotion) throw new InvalidAcessException("Must have a greater than or equal level to the demoting one");
        admToDemote.setNivel(admToDemote.getNivel()-1);
        adminRepository.save(admToDemote);
    }

    private boolean authenticate(Login credentials, Integer requiredAccessLevel, @Nullable Long adminToModify){
        Admin adminToCheck = adminRepository.findByEmailAndSenha(credentials.email(), credentials.senha()).orElse(null);
        if(adminToModify != null) {
            assert adminToCheck != null;
            if (adminToModify.equals(adminToCheck.getId())) return false;
        }
        return adminToCheck != null && adminToCheck.getAccessLevel() >= requiredAccessLevel;
    }

}
