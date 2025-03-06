package controller;

import model.Carrinho;
import model.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        if (carrinho == null) {
            carrinho = new Carrinho();
            request.getSession().setAttribute("carrinho", carrinho);
        }
        request.setAttribute("carrinho", carrinho);
        request.getRequestDispatcher("carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
            request.getSession().setAttribute("carrinho", carrinho);
        }

        if ("adicionar".equals(acao)) {
            String nomeProduto = request.getParameter("nomeProduto");
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));

            List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("produtos");
            Produto produto = produtos.stream().filter(p -> p.getNome().equals(nomeProduto)).findFirst().orElse(null);

            if (produto != null && produto.getQuantidade() >= quantidade) {
                carrinho.adicionar(produto, quantidade);
            }
        } else if ("remover".equals(acao)) {
            String nomeProduto = request.getParameter("nomeProduto");
            carrinho.remover(nomeProduto);
        }

        response.sendRedirect("listaProdutos");
    }
}