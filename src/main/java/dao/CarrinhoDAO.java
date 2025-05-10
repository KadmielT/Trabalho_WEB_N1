package dao;

import model.Carrinho;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class CarrinhoDAO {

    private EntityManager em;

    public CarrinhoDAO() {
        em = Persistence.createEntityManagerFactory("usuarioPU").createEntityManager();
    }

    // Salvar carrinho
    public void salvar(Carrinho carrinho) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(carrinho);  // Salva o carrinho no banco de dados
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;  // Lança novamente a exceção
        }
    }

    // Buscar todos os carrinhos
    public List<Carrinho> buscarTodos() {
        return em.createQuery("SELECT c FROM Carrinho c", Carrinho.class).getResultList();
    }

    // Buscar carrinho por id
    public Carrinho buscarPorId(int id) {
        return em.find(Carrinho.class, id);
    }

    // Atualizar carrinho
    public void atualizar(Carrinho carrinho) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(carrinho);  // Atualiza o carrinho no banco
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;  // Lança novamente a exceção
        }
    }

    // Remover carrinho
    public void remover(int id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Carrinho carrinho = em.find(Carrinho.class, id);
            if (carrinho != null) {
                em.remove(carrinho);  // Remove o carrinho do banco de dados
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;  // Lança novamente a exceção
        }
    }
}
