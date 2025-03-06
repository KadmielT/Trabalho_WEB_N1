package controller;

import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cadastrar")
public class UsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        if (!senha.equals(confirmarSenha)) {
            request.setAttribute("erro", "As senhas não coincidem!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        Usuario novoUsuario = new Usuario(nome, email, senha);
        request.getSession().setAttribute("usuario", novoUsuario);

        response.sendRedirect("listaProdutos"); // Redireciona para a loja
    }
}