package com.upc.trabajofinal2.repository;

import com.upc.trabajofinal2.entity.Rol;
import com.upc.trabajofinal2.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.rol = :rol")
    List<Usuario> findByRole(@Param("rol") String rol);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users_roles (user_id, role_id ) VALUES (:user_id, :rol_id)", nativeQuery = true)
    public Integer insertUserRol(@Param("user_id") Long user_id, @Param("rol_id") Long rol_id);
}
