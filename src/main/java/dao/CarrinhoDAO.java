package dao;

import jakarta.persistence.TypedQuery;
import model.Carrinho;
import model.ItemCarrinho;
import model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

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

    public Carrinho buscarPorUsuarioId(int idUsuario) {
        try {
            TypedQuery<Carrinho> query = em.createQuery(
                    "SELECT DISTINCT c FROM Carrinho c LEFT JOIN FETCH c.itens WHERE c.usuario.id = :idUsuario",
                    Carrinho.class);
            query.setParameter("idUsuario", idUsuario);

            List<Carrinho> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                return resultados.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removerItem(ItemCarrinho item) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Garante que o item está sendo gerenciado pela JPA antes de remover
            ItemCarrinho itemGerenciado = em.contains(item) ? item : em.merge(item);
            em.remove(itemGerenciado);

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
