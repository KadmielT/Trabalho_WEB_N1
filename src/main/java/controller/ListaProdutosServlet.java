package controller;

import model.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/listaProdutos")
public class ListaProdutosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("listaProdutos");
        if (produtos == null) {
            produtos = new ArrayList<>();
            request.getSession().setAttribute("listaProdutos", produtos);
        }
        request.setAttribute("listaProdutos", produtos);
        request.getRequestDispatcher("listaProdutos.jsp").forward(request, response);
    }
}