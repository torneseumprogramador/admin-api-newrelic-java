package com.thekubernetes.apiadmin.controller;

import com.thekubernetes.apiadmin.dto.AdminLoginDTO;
import com.thekubernetes.apiadmin.model.Admin;
import com.thekubernetes.apiadmin.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    
    @Autowired
    private IAdminService service;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Aplicação administradores");
    }

    @GetMapping("/admin")
    public ResponseEntity<Object> getAdmins(){
        return service.getAdmins();
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Object> getAdminById(@PathVariable Integer id){
        return service.getByIdAdmin(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable Integer id){
        return service.deleteAdmin(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAdmin(@RequestBody Admin novo) {
        return service.createAdmin(novo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatAdmin (@RequestBody Admin admin, @PathVariable Integer id){
        return service.updateAdmin(admin, id);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login (@RequestBody AdminLoginDTO dadosLogin){
        return service.gerarTokenDeAdminLogado(dadosLogin);
    }   
}
