package controller;

import model.Pedido;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/minhasCompras")
public class MinhasComprasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verifica se o usuário está logado
        if (request.getSession().getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Carrega os pedidos da sessão
        List<Pedido> pedidos = (List<Pedido>) request.getSession().getAttribute("pedidos");
        request.setAttribute("pedidos", pedidos);

        // Encaminha para o JSP
        request.getRequestDispatcher("minhasCompras.jsp").forward(request, response);
    }
}