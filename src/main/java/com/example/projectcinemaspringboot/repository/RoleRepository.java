package com.example.projectcinemaspringboot.repository;

import com.example.projectcinemaspringboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
