package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Pedido {
    private List<ItemCarrinho> itens;
    private double total;
    private Date data;

    public Pedido(List<ItemCarrinho> itens, double total) {
        this.itens = new ArrayList<>(itens);
        this.total = total;
        this.data = new Date();
    }

    public Pedido() {
        
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public double getTotal() {
        return total;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date date) {
    }

    public void setItens(ArrayList<ItemCarrinho> itemCarrinhos) {
    }

    public void setTotal(double total) {
    }
}