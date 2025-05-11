package controller;

import model.Carrinho;
import model.ItemCarrinho;
import model.Produto;
import model.Usuario;
import dao.CarrinhoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    private CarrinhoDAO carrinhoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        carrinhoDAO = new CarrinhoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Carrinho carrinho = carrinhoDAO.buscarPorUsuarioId(usuario.getId());

        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
            carrinhoDAO.salvar(carrinho);  // Salva o carrinho vazio, agora o id será gerado
            carrinho = carrinhoDAO.buscarPorUsuarioId(usuario.getId());  // Recupera o carrinho com o id gerado
        }

        // Filtra os itens para mostrar apenas aqueles que possuem id_carrinho != null
        List<ItemCarrinho> itensValidos = carrinho.getItens().stream()
                .filter(item -> item.getCarrinho() != null && item.getCarrinho().getId() != null)
                .collect(Collectors.toList()); // <- cria uma lista mutável


        // Atualiza a lista de itens no carrinho com os itens válidos
        carrinho.setItens(itensValidos);

        request.setAttribute("carrinho", carrinho);
        request.getRequestDispatcher("carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Carrinho carrinho = carrinhoDAO.buscarPorUsuarioId(usuario.getId());
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
        }

        List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("listaProdutos");

        if ("adicionar".equals(acao)) {
            // Lógica para adicionar um item
            String nomeProduto = request.getParameter("nomeProduto");
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));

            Produto produto = produtos.stream()
                    .filter(p -> p.getNome().equals(nomeProduto))
                    .findFirst()
                    .orElse(null);

            if (produto != null && produto.getQuantidade() >= quantidade) {
                carrinho.adicionar(produto, quantidade);
                produto.setQuantidade(produto.getQuantidade() - quantidade);
                carrinhoDAO.salvar(carrinho);
                request.getSession().setAttribute("produtoAdicionado", nomeProduto);
            }

            request.setAttribute("listaProdutos", produtos);
            request.getRequestDispatcher("listaProdutos.jsp").forward(request, response);

        } else if ("remover".equals(acao)) {
            String nomeProduto = request.getParameter("nomeProduto");

            // Encontrar o item do carrinho com base no nome do produto
            ItemCarrinho itemRemovido = carrinho.getItens().stream()
                    .filter(item -> item.getProduto().getNome().equals(nomeProduto))
                    .findFirst()
                    .orElse(null);

            if (itemRemovido != null) {
                // Atualiza a quantidade do produto de volta
                Produto produto = produtos.stream()
                        .filter(p -> p.getNome().equals(nomeProduto))
                        .findFirst()
                        .orElse(null);

                if (produto != null) {
                    produto.setQuantidade(produto.getQuantidade() + itemRemovido.getQuantidade());
                }


                // Remove o item do carrinho (em memória)
                carrinho.remover(nomeProduto);

                // Remove o item do banco
                carrinhoDAO.removerItem(itemRemovido);

                // Atualiza o carrinho (caso queira manter sincronizado no banco)
                carrinhoDAO.salvar(carrinho);


                // Redireciona para a página do carrinho após a remoção
                response.sendRedirect("carrinho");
            }

        } else if ("editar".equals(acao)) {
            // Lógica para editar a quantidade de um item
            String nomeProduto = request.getParameter("nomeProduto");
            int novaQuantidade = Integer.parseInt(request.getParameter("quantidade"));

            ItemCarrinho item = carrinho.getItens().stream()
                    .filter(i -> i.getProduto().getNome().equals(nomeProduto))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                Produto produto = produtos.stream()
                        .filter(p -> p.getNome().equals(nomeProduto))
                        .findFirst()
                        .orElse(null);

                if (produto != null) {
                    int quantidadeAtual = item.getQuantidade();
                    int diferenca = novaQuantidade - quantidadeAtual;

                    if (diferenca > 0) {
                        if (produto.getQuantidade() >= diferenca) {
                            item.setQuantidade(novaQuantidade);
                            produto.setQuantidade(produto.getQuantidade() - diferenca);
                        }
                    } else if (diferenca < 0) {
                        item.setQuantidade(novaQuantidade);
                        produto.setQuantidade(produto.getQuantidade() + (-diferenca));
                    }
                }

                carrinhoDAO.salvar(carrinho);
            }

            response.sendRedirect("carrinho");
        }
    }
}
