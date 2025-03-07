<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Carrinho, model.ItemCarrinho" %>
<%@ page import="model.Usuario, java.util.List" %>
<%@ page import="utils.Utils" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carrinho de Compras</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) { %>
            <div id="header_container">
                <div id="menu_container">
                    <a href="produtos" id="menu_text">Cadastro de Produtos</a>
                    <a href="listaProdutos" id="menu_text">Lista de Produtos</a>
                </div>
                <div id="menu_container">
                    <%
                    String nome = usuario.getNome();
                    String iniciais = Utils.obterIniciais(nome);
                    %>
                    <div id="img_container"><%= iniciais %></div>
                    <label for="opcoes"></label>
                    <div class="dropdown">
                        <button class="dropbtn"><%= nome %></button>
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