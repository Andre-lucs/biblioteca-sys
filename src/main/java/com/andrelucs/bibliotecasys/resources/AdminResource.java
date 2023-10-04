package com.andrelucs.bibliotecasys.resources;

import com.andrelucs.bibliotecasys.model.Admin;
import com.andrelucs.bibliotecasys.model.Login;
import com.andrelucs.bibliotecasys.services.AdminService;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admins/")
public class AdminResource {
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "")
    public ResponseEntity<List<Admin>> findAll(){
        List<Admin> response = adminService.getAllAdmins();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Admin> findOne(@PathVariable Long id){
        Admin response = adminService.getAdminById(id).orElse(null);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Admin> insert(@RequestBody Admin adminToCreate,
                                        @RequestParam("email") String email, @RequestParam("senha") String senha){
        Preconditions.checkNotNull(adminToCreate, null);
        Admin createdAdmin = adminService.insert(adminToCreate, new Login(email, senha));
        return ResponseEntity.ok(createdAdmin);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam("email") String email, @RequestParam("senha") String senha){
        adminService.deleteById(id, new Login(email, senha));
        return ResponseEntity.ok().header("deleted", "false").build();
    }

    @PutMapping(value = "promote/{id}")
    public ResponseEntity<Void> promote(@PathVariable Long id,
                                       @RequestParam("email") String email, @RequestParam("senha") String senha){
        adminService.promote(id, new Login(email, senha));
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "demote/{id}")
    public ResponseEntity<Void> demote(@PathVariable Long id,
                                        @RequestParam("email") String email, @RequestParam("senha") String senha){
        adminService.demote(id, new Login(email, senha));
        return ResponseEntity.ok().build();
    }
}
