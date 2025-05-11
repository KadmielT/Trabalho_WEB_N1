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

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();

            // Verifica se o usuário já existe
            Usuario usuarioExistente = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (usuarioExistente != null) {
                // Usuário já existe, verifica a senha
                if (usuarioExistente.getSenha().equals(senha)) {
                    // Senha correta, faz login
                    if (usuarioExistente.getEmail().equals("admin@email.com")) {
                        request.getSession().setAttribute("usuario", usuarioExistente);
                        response.sendRedirect("listaProdutosAdmin");
                    } else {
                        request.getSession().setAttribute("usuario", usuarioExistente);
                        response.sendRedirect("listaProdutos");
                    }
                } else {
                    // Senha incorreta
                    request.setAttribute("erro", "Senha incorreta para o e-mail informado.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                // Novo usuário, salva no banco
                tx = em.getTransaction();
                tx.begin();

                Usuario novoUsuario = new Usuario();
                novoUsuario.setNome(nome);
                novoUsuario.setEmail(email);
                novoUsuario.setSenha(senha);
                em.persist(novoUsuario);

                tx.commit();

                if (novoUsuario.getEmail().equals("admin@email.com")) {
                    request.getSession().setAttribute("usuario", novoUsuario);
                    response.sendRedirect("listaProdutosAdmin");
                } else {
                    request.getSession().setAttribute("usuario", novoUsuario);
                    response.sendRedirect("listaProdutos");
                }
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new ServletException("Erro ao cadastrar ou autenticar o usuário", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
