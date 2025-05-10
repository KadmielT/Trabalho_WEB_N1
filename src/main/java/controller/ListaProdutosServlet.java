package controller;

import model.Produto;
import dao.ProdutoDAO;  // <- Importação do DAO
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/listaProdutos")
public class ListaProdutosServlet extends HttpServlet {
    private ProdutoDAO produtoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        produtoDAO = new ProdutoDAO(); // Inicializa o DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Busca os produtos do banco de dados
        List<Produto> produtos = produtoDAO.buscarTodos();

        // Envia para a tela de listagem
        request.setAttribute("listaProdutos", produtos);
        request.getSession().setAttribute("listaProdutos", produtos);
        request.getRequestDispatcher("listaProdutos.jsp").forward(request, response);
    }
}
