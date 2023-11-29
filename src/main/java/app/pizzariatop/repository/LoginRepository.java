package app.pizzariatop.repository;

import app.pizzariatop.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByUsername(String login);
}
