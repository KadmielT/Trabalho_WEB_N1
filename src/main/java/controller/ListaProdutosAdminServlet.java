package controller;

import model.Produto;
import dao.ProdutoDAO;  //
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/listaProdutosAdmin")
public class ListaProdutosAdminServlet extends HttpServlet {
    private ProdutoDAO produtoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        produtoDAO = new ProdutoDAO(); // Inicializa o DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (!Objects.equals(usuario.getEmail(), "admin@email.com")) {
            request.getRequestDispatcher("listaProdutos.jsp").forward(request, response);
        } else {
            // Busca os produtos do banco de dados
            List<Produto> produtos = produtoDAO.buscarTodos();

            // Envia para a tela de listagem
            request.setAttribute("listaProdutos", produtos);
            request.getSession().setAttribute("listaProdutos", produtos);
            request.getRequestDispatcher("listaProdutosAdmin.jsp").forward(request, response);
        }
    }
}
