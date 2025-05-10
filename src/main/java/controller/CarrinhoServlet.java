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
        // Carrinho já está na sessão, o JSP irá buscar diretamente de lá.
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

            Produto produto = produtos.stream()
                    .filter(p -> p.getNome().equals(nomeProduto))
                    .findFirst()
                    .orElse(null);

            if (produto != null && produto.getQuantidade() >= quantidade) {
                carrinho.adicionar(produto, quantidade);
                produto.setQuantidade(produto.getQuantidade() - quantidade); // Reduz estoque
                request.getSession().setAttribute("produtoAdicionado", nomeProduto);
            }
            request.setAttribute("listaProdutos", produtos); // envia lista para a tela
            request.getRequestDispatcher("listaProdutos.jsp").forward(request, response); // permanece na tela


        } else if ("remover".equals(acao)) {
            String nomeProduto = request.getParameter("nomeProduto");

            ItemCarrinho itemRemovido = carrinho.getItens().stream()
                    .filter(item -> item.getProduto().getNome().equals(nomeProduto))
                    .findFirst()
                    .orElse(null);

            if (itemRemovido != null) {
                Produto produto = produtos.stream()
                        .filter(p -> p.getNome().equals(nomeProduto))
                        .findFirst()
                        .orElse(null);

                if (produto != null) {
                    produto.setQuantidade(produto.getQuantidade() + itemRemovido.getQuantidade()); // Devolve estoque
                }

                carrinho.remover(nomeProduto);
            }

            response.sendRedirect("carrinho");

        } else if ("editar".equals(acao)) {
            String nomeProduto = request.getParameter("nomeProduto");
            int novaQuantidade = Integer.parseInt(request.getParameter("quantidade"));

            ItemCarrinho item = carrinho.getItens().stream()
                    .filter(i -> i.getProduto().getNome().equals(nomeProduto))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                Produto produto = produtos.stream()
                        .filter(p -> p.getNome().equals(nomeProduto))
                        .findFirst()
                        .orElse(null);

                if (produto != null) {
                    int quantidadeAtual = item.getQuantidade();
                    int diferenca = novaQuantidade - quantidadeAtual;

                    if (diferenca > 0) {
                        // Aumentando quantidade no carrinho
                        if (produto.getQuantidade() >= diferenca) {
                            item.setQuantidade(novaQuantidade);
                            produto.setQuantidade(produto.getQuantidade() - diferenca); // Diminui do estoque
                        }
                    } else if (diferenca < 0) {
                        // Reduzindo quantidade no carrinho
                        item.setQuantidade(novaQuantidade);
                        produto.setQuantidade(produto.getQuantidade() + (-diferenca)); // Devolve ao estoque
                    }
                }
            }

            response.sendRedirect("carrinho");
        }
    }
}
