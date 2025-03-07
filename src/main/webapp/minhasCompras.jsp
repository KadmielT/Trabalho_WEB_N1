<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Pedido, model.ItemCarrinho, java.util.List" %>
<%@ page import="model.Usuario" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<html>
<head>
    <title>Minhas Compras</title>
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
    <div id="body_container">
        <div id="minhasCompras">
            <h2>Minhas Compras Finalizadas</h2>
            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
                List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
                if (pedidos != null && !pedidos.isEmpty()) {
                    int pedidoNum = 1;
                    for (Pedido pedido : pedidos) { %>
                        <h3>Pedido #<%= pedidoNum %> - Data: <%= sdf.format(pedido.getData()) %></h3>
                        <ul>
                            <% for (ItemCarrinho item : pedido.getItens()) { %>
                                <li><%= item.getProduto().getNome() %> - Qtd: <%= item.getQuantidade() %> - Subtotal: R$<%= item.getSubtotal() %></li>
                            <% } %>
                        </ul>
                        <p>Total: R$<%= pedido.getTotal() %></p>
                        <hr>
                    <% pedidoNum++; }
                } else { %>
                    <p>Nenhuma compra realizada ainda.</p>
                <% } %>
        </div>
    </div>
<script src="js/script.js"></script>
</body>
</html>