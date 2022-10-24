package com.thekubernetes.apiadmin.AdminServiceTest;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thekubernetes.apiadmin.dao.AdminDAO;
import com.thekubernetes.apiadmin.dto.AdminDTO;
import com.thekubernetes.apiadmin.model.Admin;

@RunWith(SpringRunner.class)
@DataJpaTest
public class adminServiceTest {


    @Autowired
    private AdminDAO adminDAO;

    @Test
    public void createAdminTest(){

        Admin admin = new Admin();

        admin.setName("Zé das Coves");
        admin.setEmail("ze@dascoves.com");
        admin.setPassword("123456");
        adminDAO.save(admin);

       Assertions.assertThat(admin.getId()).isNotNull();
       Assertions.assertThat(admin.getName()).isEqualTo("Zé das Coves");
       Assertions.assertThat(admin.getEmail()).isEqualTo("ze@dascoves.com");
       Assertions.assertThat(admin.getPassword()).isEqualTo("123456");

    }

    @Test
    public void useAdminDTOTest(){
        AdminDTO adminDTO = new AdminDTO(1, "maria", "e@mail.com");
        Assertions.assertThat(adminDTO.getId()).isNotNull().isEqualTo(1);
        Assertions.assertThat(adminDTO.getName()).isEqualTo("maria");
        Assertions.assertThat(adminDTO.getEmail()).isEqualTo("e@mail.com");

    }

    @Test
    public void getAllAdminsAndChargeInAdminDTO(){
        Admin admin1 = new Admin();

        admin1.setName("Zé das Coves");
        admin1.setEmail("ze@dascoves.com");
        admin1.setPassword("123456");

        Admin admin2 = new Admin();

        admin2.setName("maria das Coves");
        admin2.setEmail("maria@dascoves.com");
        admin2.setPassword("654321");

        adminDAO.save(admin1);
        adminDAO.save(admin2);

        List<AdminDTO> admins = new ArrayList<AdminDTO>();
        for (Admin admin : adminDAO.findAll()) {
            admins.add(new AdminDTO(admin.getId(), admin.getName(), admin.getEmail()));
        }

        Assertions.assertThat(admins.size()).isEqualTo(2);
    }

    @Test
    public void getAdminByIdTest(){

        Admin admin1 = new Admin();

        admin1.setName("Zé das Coves");
        admin1.setEmail("ze@dascoves.com");
        admin1.setPassword("123456"); 
        
        adminDAO.save(admin1);
        
        var admin = adminDAO.findById(admin1.getId());

        System.out.println(admin.get().getId());

        Assertions.assertThat(admin.get().getId()).isNotNull();
    }

    @Test
    public void updateAdminTest(){

        Admin admin1 = new Admin();

        admin1.setName("Zé das Coves");
        admin1.setEmail("ze@dascoves.com");
        admin1.setPassword("123456"); 
        
        adminDAO.save(admin1);
        Optional<Admin> admin = adminDAO.findById(admin1.getId());

        admin.get().setName("José das Coves");
        Admin admin2 = new Admin();
        admin2.setName(admin.get().getName());
        admin2.setId(admin.get().getId());
        admin2.setEmail(admin.get().getEmail());
        admin2.setPassword(admin.get().getPassword());
        adminDAO.save(admin2);
        Optional<Admin> admin3 = adminDAO.findById(admin1.getId());

        Assertions.assertThat(admin3.get().getName()).isEqualTo("José das Coves");
    }

    @Test
   public void deleteByIdTest(){

    Admin admin1 = new Admin();

    admin1.setName("Zé das Coves");
    admin1.setEmail("ze@dascoves.com");
    admin1.setPassword("123456"); 
    
    adminDAO.save(admin1);

    Optional<Admin> admin = adminDAO.findById(admin1.getId());
    adminDAO.deleteById(admin.get().getId());
    Optional<Admin> admin2 = adminDAO.findById(admin1.getId());
    Assertions.assertThat(admin2).isEmpty();
   } 

   @Test
   public void gerarTokenDeAdminLogadoTest(){

    Admin admin1 = new Admin();

    admin1.setName("Zé das Coves");
    admin1.setEmail("ze@dascoves.com");
    admin1.setPassword("123456"); 
    
    adminDAO.save(admin1);

    AdminDTO adminTest = new AdminDTO();


    adminTest.setEmail("ze@dascoves.com");
    var admin = adminDAO.findByEmailEquals(adminTest.getEmail());

    System.out.println(admin);

    Assertions.assertThat(admin.getEmail()).isEqualTo("ze@dascoves.com");
    Assertions.assertThat(admin.getPassword()).isEqualTo("123456");

   }


    
}
