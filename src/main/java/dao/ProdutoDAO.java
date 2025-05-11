package dao;

import model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

import java.util.List;

public class ProdutoDAO {

    public ProdutoDAO() {}

    public void salvar(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(produto);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Produto> buscarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }
}
