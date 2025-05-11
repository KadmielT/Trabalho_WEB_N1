package dao;

import jakarta.persistence.EntityTransaction;
import model.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import utils.JPAUtil;

import java.util.List;

public class PedidoDAO {

    private EntityManager em = JPAUtil.getEntityManager();

    public PedidoDAO() {}

    // Método para salvar pedido
    public void salvarPedido(Pedido pedido) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(pedido); // Isso já persiste os itens se cascade estiver correto
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar pedido", e);
        } finally {
            em.close();
        }
    }

    public List<Pedido> buscarPedidosPorUsuarioId(int usuarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT DISTINCT p FROM Pedido p " +
                            "LEFT JOIN FETCH p.itens i " +
                            "LEFT JOIN FETCH i.produto " +
                            "WHERE p.usuario.id = :usuarioId", Pedido.class
            );
            query.setParameter("usuarioId", usuarioId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pedidos", e);
        } finally {
            em.close();
        }
    }

}
