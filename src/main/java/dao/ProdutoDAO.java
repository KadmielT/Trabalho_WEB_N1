package dao;

import model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

import java.util.List;

public class ProdutoDAO {

    private EntityManager em = JPAUtil.getEntityManager();

    public ProdutoDAO() {}

    public void salvar(Produto produto) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(produto);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Produto> buscarTodos() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }
}
