package dao;

import model.ItemCarrinho;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class ItemCarrinhoDAO {

    private EntityManager em;

    public ItemCarrinhoDAO() {
        em = Persistence.createEntityManagerFactory("usuarioPU").createEntityManager();
    }

    // Método para salvar um item no carrinho
    public void salvar(ItemCarrinho itemCarrinho) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(itemCarrinho); // Salva o item no carrinho
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Lança novamente a exceção
        }
    }

    // Método para buscar todos os itens de um carrinho
    public List<ItemCarrinho> buscarTodos() {
        return em.createQuery("SELECT i FROM ItemCarrinho i", ItemCarrinho.class).getResultList();
    }

    // Método para buscar um item específico por ID de carrinho
    public List<ItemCarrinho> buscarPorCarrinho(Long idCarrinho) {
        return em.createQuery("SELECT i FROM ItemCarrinho i WHERE i.carrinho.id = :idCarrinho", ItemCarrinho.class)
                .setParameter("idCarrinho", idCarrinho)
                .getResultList();
    }

    // Método para remover um item do carrinho
    public void remover(ItemCarrinho itemCarrinho) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(itemCarrinho) ? itemCarrinho : em.merge(itemCarrinho)); // Remove o item do carrinho
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Lança novamente a exceção
        }
    }
}
