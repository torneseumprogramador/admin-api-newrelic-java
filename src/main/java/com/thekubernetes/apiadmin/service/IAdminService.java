package com.thekubernetes.apiadmin.service;




import org.springframework.http.ResponseEntity;

import com.thekubernetes.apiadmin.dto.AdminLoginDTO;
import com.thekubernetes.apiadmin.model.Admin;


public interface IAdminService {
    public ResponseEntity<Object> createAdmin(Admin admin);
    public ResponseEntity<Object> getAdmins ();
    public ResponseEntity<Object> getByIdAdmin (Integer id);
    public ResponseEntity<Object> updateAdmin (Admin admin, Integer id);
    public ResponseEntity<Object> deleteAdmin(Integer id);
    public ResponseEntity<Object> gerarTokenDeAdminLogado(AdminLoginDTO dadosLogin);
}
