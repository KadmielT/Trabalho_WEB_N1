package model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<ItemCarrinho> itens;

    public Carrinho() {
        this.itens = new ArrayList<>();
    }

    public void adicionar(Produto produto, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getNome().equals(produto.getNome())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        itens.add(new ItemCarrinho(produto, quantidade));
    }

    public void remover(String nomeProduto) {
        itens.removeIf(item -> item.getProduto().getNome().equals(nomeProduto));
    }

    public List<ItemCarrinho> getItens() { return itens; }
    public double getTotal() {
        return itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
    }
}