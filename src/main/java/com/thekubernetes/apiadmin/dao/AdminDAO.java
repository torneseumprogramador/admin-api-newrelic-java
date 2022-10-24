package com.thekubernetes.apiadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thekubernetes.apiadmin.model.Admin;

public interface AdminDAO extends JpaRepository<Admin, Integer> {
    public Admin findByEmailEquals(String email);
}
