package controller;

import model.Pedido;
import dao.PedidoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/minhasCompras")
public class MinhasComprasServlet extends HttpServlet {
    private PedidoDAO pedidoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        pedidoDAO = new PedidoDAO(); // Inicializa o PedidoDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verifica se o usuário está logado
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        if (Objects.equals(usuario.getEmail(), "admin@email.com")) {
            request.getRequestDispatcher("listaProdutosAdmin.jsp").forward(request, response);
        } else {
            // Recupera o ID do usuário logado
            int usuarioId = usuario.getId();

            // Carrega os pedidos do banco de dados
            List<Pedido> pedidos = pedidoDAO.buscarPedidosPorUsuarioId(usuarioId);

            // Define os pedidos como atributo na requisição
            request.setAttribute("pedidos", pedidos);

            // Encaminha para o JSP
            request.getRequestDispatcher("minhasCompras.jsp").forward(request, response);
        }
    }
}
