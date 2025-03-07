package controller;

import model.Carrinho;
import model.ItemCarrinho;
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
        List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("listaProdutos");

        if ("adicionar".equals(acao)) {
            String nomeProduto = request.getParameter("nomeProduto");
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));
            Produto produto = produtos.stream().filter(p -> p.getNome().equals(nomeProduto)).findFirst().orElse(null);

            if (produto != null && produto.getQuantidade() >= quantidade) {
                carrinho.adicionar(produto, quantidade);
                produto.setQuantidade(produto.getQuantidade() - quantidade);
                response.sendRedirect("listaProdutos");
            }

        } else if ("remover".equals(acao)) {
            String nomeProduto = request.getParameter("nomeProduto");
            ItemCarrinho itemRemovido = carrinho.getItens().stream()
                    .filter(item -> item.getProduto().getNome().equals(nomeProduto))
                    .findFirst().orElse(null);
            if (itemRemovido != null) {
                Produto produto = produtos.stream().filter(p -> p.getNome().equals(nomeProduto)).findFirst().orElse(null);
                if (produto != null) {
                    produto.setQuantidade(produto.getQuantidade() + itemRemovido.getQuantidade()); // Devolve ao estoque
                }
                carrinho.remover(nomeProduto);
                response.sendRedirect("carrinho");
            }
        } else if ("editar".equals(acao)) {
            String nomeProduto = request.getParameter("nomeProduto");
            int novaQuantidade = Integer.parseInt(request.getParameter("quantidade"));
            ItemCarrinho item = carrinho.getItens().stream()
                    .filter(i -> i.getProduto().getNome().equals(nomeProduto))
                    .findFirst().orElse(null);
            if (item != null) {
                Produto produto = produtos.stream().filter(p -> p.getNome().equals(nomeProduto)).findFirst().orElse(null);
                if (produto != null) {
                    int quantidadeAtual = item.getQuantidade();
                    int diferenca = novaQuantidade - quantidadeAtual;
                    if (produto.getQuantidade() >= diferenca) { // Verifica estoque suficiente
                        produto.setQuantidade(produto.getQuantidade() - diferenca);
                        item.setQuantidade(novaQuantidade);
                    }
                    response.sendRedirect("carrinho");
                }
            }
        }
    }
}