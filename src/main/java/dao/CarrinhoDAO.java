package dao;

import jakarta.persistence.TypedQuery;
import model.Carrinho;
import model.ItemCarrinho;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

import java.util.List;

public class CarrinhoDAO {
    private EntityManager em = JPAUtil.getEntityManager();

    public CarrinhoDAO() {}

    public void salvar(Carrinho carrinho) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // Verifica se o carrinho não tem id, indicando que é novo
            if (carrinho.getId() == null) {
                em.persist(carrinho); // Persiste um novo carrinho
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
                em.persist(item); // Persiste o item do carrinho
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
                return resultados.get(0); // Retorna o primeiro carrinho encontrado
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro se ocorrer
        }
        return null; // Retorna null caso não encontre carrinho
    }

    public void removerItem(ItemCarrinho item) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // Verifica se o item já está gerenciado pelo EntityManager
            ItemCarrinho itemGerenciado = em.contains(item) ? item : em.merge(item);
            em.remove(itemGerenciado); // Remove o item do carrinho

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Lança novamente a exceção
        }
    }
}
