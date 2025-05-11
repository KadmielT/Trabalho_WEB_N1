package controller;

import dao.PedidoDAO;
import dao.ProdutoDAO;
import model.Carrinho;
import model.ItemCarrinho;
import model.Pedido;
import model.Produto;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/finalizarPedido")
public class FinalizarPedidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (carrinho == null || carrinho.getItens().isEmpty() || usuario == null) {
            response.sendRedirect("carrinho"); // Volta ao carrinho se inválido
            return;
        }

        ProdutoDAO produtoDAO = new ProdutoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        // Verifica estoque
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produtoAtualizado = produtoDAO.buscarTodos().stream()
                    .filter(p -> p.getId() == item.getProduto().getId())
                    .findFirst().orElse(null);

            if (produtoAtualizado == null || produtoAtualizado.getQuantidade() < item.getQuantidade()) {
                request.setAttribute("erro", "Estoque insuficiente para o produto: " + item.getProduto().getNome());
                request.getRequestDispatcher("carrinho.jsp").forward(request, response);
                return;
            }
        }

        // Atualiza estoque no banco
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produtoAtualizado = produtoDAO.buscarTodos().stream()
                    .filter(p -> p.getId() == item.getProduto().getId())
                    .findFirst().orElse(null);

            if (produtoAtualizado != null) {
                produtoAtualizado.setQuantidade(produtoAtualizado.getQuantidade() - item.getQuantidade());
                produtoDAO.salvar(produtoAtualizado); // merge via DAO
            }
        }

        // Cria o pedido com dados do carrinho
        List<ItemCarrinho> itensClonados = new ArrayList<>();
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produtoOriginal = item.getProduto();

            Produto produtoClonado = new Produto();
            produtoClonado.setId(produtoOriginal.getId()); // importante para evitar persistência como novo
            produtoClonado.setNome(produtoOriginal.getNome());
            produtoClonado.setPreco(produtoOriginal.getPreco());
            produtoClonado.setQuantidade(produtoOriginal.getQuantidade());

            ItemCarrinho itemClonado = new ItemCarrinho();
            itemClonado.setProduto(produtoClonado);
            itemClonado.setQuantidade(item.getQuantidade());

            itensClonados.add(itemClonado);
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setData(new Date());
        pedido.setItens(itensClonados);
        pedido.setTotal(carrinho.getTotal());

        // Persiste o pedido e os itens associados
        pedidoDAO.salvarPedido(pedido);

        // Limpa carrinho
        carrinho.getItens().clear();
        request.getSession().setAttribute("carrinho", carrinho);

        response.sendRedirect("minhasCompras");
    }
}
