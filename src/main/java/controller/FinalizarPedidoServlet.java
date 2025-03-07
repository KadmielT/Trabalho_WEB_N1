package controller;

import model.Carrinho;
import model.ItemCarrinho;
import model.Pedido;
import model.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/finalizarPedido")
public class FinalizarPedidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        if (carrinho == null || carrinho.getItens().isEmpty()) {
            response.sendRedirect("carrinho"); // Volta ao carrinho se estiver vazio
            return;
        }

        List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("listaProdutos");

        // Verifica se há estoque suficiente para todos os itens antes de finalizar
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produto = produtos.stream()
                    .filter(p -> p.getNome().equals(item.getProduto().getNome()))
                    .findFirst().orElse(null);
            if (produto == null || produto.getQuantidade() < item.getQuantidade()) {
                request.setAttribute("erro", "Estoque insuficiente para o produto: " + item.getProduto().getNome());
                request.getRequestDispatcher("carrinho.jsp").forward(request, response);
                return;
            }
        }

        // Cria o pedido usando o construtor correto
        Pedido pedido = new Pedido(new ArrayList<>(carrinho.getItens()), carrinho.getTotal());
        pedido.setData(new Date());

        // Debita o estoque
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produto = produtos.stream()
                    .filter(p -> p.getNome().equals(item.getProduto().getNome()))
                    .findFirst().orElse(null);
            if (produto != null) {
                produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            }
        }

        // Adiciona o pedido à lista de pedidos (assumindo que existe uma lista na sessão)
        List<Pedido> pedidos = (List<Pedido>) request.getSession().getAttribute("pedidos");
        if (pedidos == null) {
            pedidos = new ArrayList<>();
            request.getSession().setAttribute("pedidos", pedidos);
        }
        pedidos.add(pedido);

        // Limpa o carrinho após finalizar
        carrinho.getItens().clear();

        response.sendRedirect("minhasCompras"); // Redireciona para a página de pedidos
    }
}