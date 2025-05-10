package dao;

import model.ItemCarrinho;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;
import java.util.List;

public class ItemCarrinhoDAO {

    private EntityManager em = JPAUtil.getEntityManager();

    public ItemCarrinhoDAO() {}

    public void salvar(ItemCarrinho itemCarrinho) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(itemCarrinho);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<ItemCarrinho> buscarTodos() {
        return em.createQuery("SELECT i FROM ItemCarrinho i", ItemCarrinho.class).getResultList();
    }

    public List<ItemCarrinho> buscarPorCarrinho(Long idCarrinho) {
        return em.createQuery("SELECT i FROM ItemCarrinho i WHERE i.carrinho.id = :idCarrinho", ItemCarrinho.class)
                .setParameter("idCarrinho", idCarrinho)
                .getResultList();
    }

    public void remover(ItemCarrinho itemCarrinho) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(itemCarrinho) ? itemCarrinho : em.merge(itemCarrinho));
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
