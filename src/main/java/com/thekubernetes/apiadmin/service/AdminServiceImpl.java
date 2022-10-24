package com.thekubernetes.apiadmin.service;

import java.util.ArrayList;
import java.util.List;


import com.thekubernetes.apiadmin.dao.AdminDAO;
import com.thekubernetes.apiadmin.dto.AdminDTO;
import com.thekubernetes.apiadmin.dto.AdminLoginDTO;
import com.thekubernetes.apiadmin.exception.ApiRequestException;
import com.thekubernetes.apiadmin.model.Admin;
import com.thekubernetes.apiadmin.security.ApiCrypto;
import com.thekubernetes.apiadmin.security.Token;
import com.thekubernetes.apiadmin.security.TokenUtil;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminDAO dao;

    @Override
    public ResponseEntity<Object> createAdmin(Admin novo) {
        if (novo.getName() == null) {
              return ResponseEntity.status(400).body("{\"message\":\"Campo 'Name' precisa ser informado.\"}");
        }
        if (novo.getEmail() == null) {
            return ResponseEntity.status(400).body("{\"message\":\"Campo 'Email' precisa ser informado.\"}");
        }
        if (novo.getPassword() == null) {
            return ResponseEntity.status(400).body("{\"message\":\"Campo 'password' precisa ser informado.\"}");
        }
        try {
            novo.setPassword(ApiCrypto.encryptToSave(novo.getPassword()));
            dao.save(novo);
            AdminDTO create = new AdminDTO(novo.getId(), novo.getName(), novo.getEmail());
            return ResponseEntity.status(201).body(create);
        } catch (Exception e) {

            throw new ApiRequestException("Não foi possível Cadastrar admin.");
        }

    }

    @Override
    public ResponseEntity<Object> getAdmins() {

        List<AdminDTO> admins = new ArrayList<AdminDTO>();

        try {
            for (Admin admin : dao.findAll()) {
                admins.add(new AdminDTO(admin.getId(), admin.getName(), admin.getEmail()));
            }
            if (admins.isEmpty()) {

                return ResponseEntity.status(404).body("{\"message\":\"Não existe Admin cadastrado.\"}");
            }
            return ResponseEntity.status(200).body(admins);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Erro interno");
        }
    }

    @Override
    public ResponseEntity<Object> getByIdAdmin(Integer id) {

        try {
            var admin = dao.findById(id);

            if (admin.isEmpty()) {

                return ResponseEntity.status(404).body("{\"message\":\"Não existe admin para o Id informado.\"}");

            }
            AdminDTO adminDTO = new AdminDTO(admin.get().getId(), admin.get().getName(), admin.get().getEmail());
            return ResponseEntity.status(200).body(adminDTO);

        } catch (Exception ex) {

            ex.getStackTrace();
            throw new ApiRequestException("Não foi possível encontrar admin com o Id informado");
        }

    }

    @Override
    public ResponseEntity<Object> updateAdmin(Admin admin, Integer id) {

        var adminOptional = dao.findById(id);

        if (adminOptional.isEmpty()) {

            return ResponseEntity.status(404).body("{\"message\":\"Não existe admin para o Id informado.\"}");
        }
        if(admin.getId() == null){
            throw new ApiRequestException("{\"message\":\"Campo 'Id' é obrigatório.\"}");
        }
        if (admin.getPassword() == null) {
            return ResponseEntity.status(400).body("{\"message\":\"Campo 'password' precisa ser informado.\"}");
        }
        try {
            admin.setPassword(ApiCrypto.encryptToSave(admin.getPassword()));
            dao.save(admin);
            AdminDTO create = new AdminDTO(admin.getId(), admin.getName(), admin.getEmail());
            return ResponseEntity.status(201).body(create);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Não foi possível atualizar admin");
        }
    }

    @Override
    public ResponseEntity<Object> deleteAdmin(Integer id) {

        var admin = dao.findById(id);

        if (admin.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\":\"Não existe admin para o Id informado.\"}");
        }
        try {
            dao.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Não foi possível deletar admin");
        }

    }

    @Override
    public ResponseEntity<Object> gerarTokenDeAdminLogado(AdminLoginDTO dadosLogin) {
        Admin admin = dao.findByEmailEquals(dadosLogin.getEmail());
        if (admin != null) {
            boolean passwordIsValid = ApiCrypto.comparePassword( dadosLogin.getPassword(), admin.getPassword());

            if (passwordIsValid) {
                Token token= new Token (TokenUtil.createToken(admin));
                return ResponseEntity.status(200).body("{\"token\":\"" +token.getToken()+"\",\n\"id\":\"" + admin.getId() +"\"}");
            }else{
                return ResponseEntity.status(400).body("{\"message\":\"Email ou senha inválidos.\"}");
            }
        }
        return ResponseEntity.status(400).body("{\"message\":\"Email ou senha inválidos.\"}");
    }

}
