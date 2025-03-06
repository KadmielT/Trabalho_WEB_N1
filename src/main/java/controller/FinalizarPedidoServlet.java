package controller;

import model.Carrinho;
import model.ItemCarrinho;
import model.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/finalizarPedido")
public class FinalizarPedidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        List<Produto> produtos = (List<Produto>) session.getAttribute("listaProdutos");

        if (carrinho != null && produtos != null) {
            for (ItemCarrinho itemCarrinho : carrinho.getItens()) {
                for (Produto p : produtos) {
                    if (p.getNome().equals(itemCarrinho.getProduto().getNome())) {
                        p.setQuantidade(p.getQuantidade() - itemCarrinho.getQuantidade()); // Agora sim reduz o estoque!
                        break;
                    }
                }
            }
        }

        // Limpar carrinho após finalização do pedido
        session.removeAttribute("carrinho");

        // Redirecionar para página de confirmação
        response.sendRedirect("pedido.jsp");
    }
}
