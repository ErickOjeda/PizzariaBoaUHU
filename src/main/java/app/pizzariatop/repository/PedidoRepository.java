package app.pizzariatop.repository;

import app.pizzariatop.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {

}
