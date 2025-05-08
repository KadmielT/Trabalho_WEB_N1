package controller;

import model.Usuario;
import utils.JPAUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha);

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(novoUsuario);
            tx.commit();
            request.getSession().setAttribute("usuario", novoUsuario);
            response.sendRedirect("listaProdutos"); // Redireciona para a loja
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new ServletException("Erro ao cadastrar usuario", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}