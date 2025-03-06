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
                    <a href="minhasCompras">Lista de Produtos</a>
                    <a href="carrinho">Ver Carrinho</a>
                    </div><div id="menu_container">
                    <a href="minhasCompras">Minhas Compras</a>
                    <p id="text">IMG</p>
                    <p id = "text"><%= usuario.getNome() %>
                    </p><a href="index.jsp">Voltar</a></div></div>
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
</body>
</html>