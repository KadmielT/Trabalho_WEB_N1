package dao;

import model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class ProdutoDAO {

    private EntityManager em;

    public ProdutoDAO() {
        em = Persistence.createEntityManagerFactory("usuarioPU").createEntityManager();
    }

    public void salvar(Produto produto) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(produto); // Salva o produto no banco
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Lança novamente a exceção
        }
    }

    public List<Produto> buscarTodos() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }
}
