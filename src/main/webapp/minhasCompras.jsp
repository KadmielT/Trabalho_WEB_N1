<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Pedido, model.ItemCarrinho, java.util.List" %>
<%@ page import="model.Usuario" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="utils.Utils" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meus Pedidos</title>
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
    <div id="body_container">
        <div id="minhasCompras">
            <h2>Meus Pedidos</h2>
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