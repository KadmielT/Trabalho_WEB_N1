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
                    <a href="produtos">Início</a>
                    <a href="listaProdutos">Lista de Produtos</a>
                    <a href="carrinho">Ver Carrinho</a>
                </div>
                <div id="menu_container">
                    <a href="minhasCompras">Minhas Compras</a>
                    <p id="text">IMG</p>
                    <label for="opcoes"></label>
                    <div class="dropdown">
                        <button class="dropbtn"><%= usuario.getNome() %></button>
                        <div class="dropdown-content" id="dropdownMenu">
                            <a href="listar.jsp">Ver Perfil</a>
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
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                        if (carrinho != null && !carrinho.getItens().isEmpty()) {
                            for (ItemCarrinho item : carrinho.getItens()) { %>
                                <li><%= item.getProduto().getNome() %> - R$<%= item.getProduto().getPreco() %> (Qtd: <%= item.getQuantidade() %>)</li>
                    <%      }
                        } else { %>
                            <p>Seu carrinho está vazio.</p>
                    <% } %>
                </ul>

                <% if (carrinho != null && !carrinho.getItens().isEmpty()) { %>
                    <form action="finalizarPedido" method="post">
                        <button type="submit">Finalizar Pedido</button>
                    </form>
                <% } %>
    </div>
<script src="js/script.js"></script>
</body>
</html>