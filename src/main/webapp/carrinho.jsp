<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Carrinho, model.ItemCarrinho" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carrinho de Compras</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) { %>
            <div id="header_container">
                <div id="menu_container">
                    <a href="produtos">Cadastro de Produtos</a>
                    <a href="listaProdutos">Lista de Produtos</a>
                </div>
                <div id="menu_container">
                    <p id="text">IMG</p>
                    <label for="opcoes"></label>
                    <div class="dropdown">
                        <button class="dropbtn"><%= usuario.getNome() %></button>
                        <div class="dropdown-content" id="dropdownMenu">
                            <a href="listar.jsp">Ver Perfil</a>
                            <a href="carrinho">Ver Carrinho</a>
                            <a href="minhasCompras">Meus pedidos</a>
                            <a href="index.jsp">Sair</a>
                        </div>
                    </div>
                </div>
            </div>
        <% }
    %>
    <div id = "body_container">
        <h2>Carrinho de Compras</h2>
        <ul>
                    <%
                        Carrinho carrinho = (Carrinho) request.getAttribute("carrinho");
                        if (carrinho != null && !carrinho.getItens().isEmpty()) {
                            for (ItemCarrinho item : carrinho.getItens()) { %>
                                <li>
                                    <%= item.getProduto().getNome() %> - R$<%= item.getProduto().getPreco() %> (Qtd: <%= item.getQuantidade() %>) - Subtotal: R$<%= item.getSubtotal() %>
                                    <!-- Formulário para editar quantidade -->
                                    <form action="carrinho" method="post" style="display:inline;">
                                        <input type="hidden" name="acao" value="editar">
                                        <input type="hidden" name="nomeProduto" value="<%= item.getProduto().getNome() %>">
                                        <input type="number" name="quantidade" value="<%= item.getQuantidade() %>" min="1" required style="width: 50px;">
                                        <button type="submit">Atualizar</button>
                                    </form>
                                    <!-- Formulário para remover item -->
                                    <form action="carrinho" method="post" style="display:inline;">
                                        <input type="hidden" name="acao" value="remover">
                                        <input type="hidden" name="nomeProduto" value="<%= item.getProduto().getNome() %>">
                                        <button type="submit">Remover</button>
                                    </form>
                                </li>
                    <%      }
                        } else { %>
                            <p>Seu carrinho está vazio.</p>
                    <% } %>
                </ul>
    <% if (carrinho != null && !carrinho.getItens().isEmpty()) { %>
                <p>Total: R$<%= carrinho.getTotal() %></p>
                <form action="finalizarPedido" method="post">
                    <button type="submit">Finalizar Pedido</button>
                </form>
                <a href="listaProdutos">Continuar Comprando</a>
            <% } %>
        </div>
        <script src="js/script.js"></script>
    </body>
    </html>