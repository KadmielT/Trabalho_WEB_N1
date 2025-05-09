package controller;

import model.Produto;
import dao.ProdutoDAO;  // Importando o DAO
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {
    private ProdutoDAO produtoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        produtoDAO = new ProdutoDAO(); // Inicializando o ProdutoDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperar lista de produtos do banco
        List<Produto> produtos = produtoDAO.buscarTodos();
        request.setAttribute("listaProdutos", produtos);
        request.getRequestDispatcher("produtos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");
            double preco = Double.parseDouble(request.getParameter("preco"));
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));

            if (nome != null && !nome.matches("^[^\"']+$")) {
                request.setAttribute("erro", "O nome não pode conter aspas simples ou aspas duplas.");
                request.getRequestDispatcher("produtos.jsp").forward(request, response);
                return;
            }

            if (preco < 0 || quantidade < 0) {
                request.setAttribute("erro", "Preço e quantidade não podem ser negativos.");
                request.getRequestDispatcher("produtos.jsp").forward(request, response);
                return;
            }

            Produto novoProduto = new Produto(nome, descricao, preco, quantidade);
            produtoDAO.salvar(novoProduto); // Salvar no banco

            response.sendRedirect("listaProdutos"); // Redirecionar para lista de produtos
        } catch (NumberFormatException e) {
            request.setAttribute("erro", "Valores inválidos. Verifique os campos preenchidos.");
            request.getRequestDispatcher("produtos.jsp").forward(request, response);
        }
    }
}
