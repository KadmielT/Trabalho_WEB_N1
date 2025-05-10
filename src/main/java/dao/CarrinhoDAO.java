package dao;

import model.Carrinho;
import model.ItemCarrinho;
import model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CarrinhoDAO {
    private EntityManager em;

    public CarrinhoDAO() {
        em = Persistence.createEntityManagerFactory("usuarioPU").createEntityManager();
    }

    public void salvar(Carrinho carrinho) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (carrinho.getId() == 0) {
                em.persist(carrinho); // Salva um novo carrinho
            } else {
                em.merge(carrinho); // Atualiza um carrinho existente
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Lança novamente a exceção
        }
    }

    public void salvarItem(ItemCarrinho item) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (item.getProduto() != null) {
                em.persist(item); // Salva o item no carrinho
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Lança novamente a exceção
        }
    }
}
