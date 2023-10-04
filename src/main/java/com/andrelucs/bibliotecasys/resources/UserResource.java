package com.andrelucs.bibliotecasys.resources;

import com.andrelucs.bibliotecasys.model.Client;
import com.andrelucs.bibliotecasys.model.Login;
import com.andrelucs.bibliotecasys.services.UserService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users/")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Client>> getAll(){
        List<Client> response = userService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Client> get(@PathVariable Long id){
        Client response = userService.get(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Client> insert(@RequestBody Client obj)  {
        Client response = userService.insert(obj);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "all")
    public ResponseEntity<List<Client>> insertAll(@RequestBody List<Client> obj){
        List<Client> response = userService.insertAll(obj);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Client> updateById(@PathVariable Long id, @RequestBody Client obj, @RequestParam(value = "partialUpdate", defaultValue = "false") Boolean partial){
        Client response = userService.updateById(id, obj, partial);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "login")
    public ResponseEntity<Client> login(@RequestParam("email") String email, @RequestParam("pass") String senha){
        Client userObject = userService.login(new Login(email, senha));
        return ResponseEntity.ok(userObject);
    }

}
