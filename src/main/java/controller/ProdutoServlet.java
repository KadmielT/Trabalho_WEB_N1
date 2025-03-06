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

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("listaProdutos");
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


            if (preco < 0 || quantidade < 0) {
                request.setAttribute("erro", "Preço e quantidade não podem ser negativos.");
                request.getRequestDispatcher("produtos.jsp").forward(request, response);
                return;
            }

            Produto novoProduto = new Produto(nome, descricao, preco, quantidade);
            List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("listaProdutos");
            produtos.add(novoProduto);
            request.getSession().setAttribute("listaProdutos", produtos);

            response.sendRedirect("listaProdutos");
        } catch (NumberFormatException e) {
            request.setAttribute("erro", "Valores inválidos. Verifique os campos preenchidos.");
            request.getRequestDispatcher("produtos.jsp").forward(request, response);
        }
    }
}