package com.upc.trabajofinal2.repository;

import com.upc.trabajofinal2.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByRol (String rol);

}
