<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario, java.util.List" %>
<%@ page import="utils.Utils" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meu Perfil</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) { %>
            <div id="header_container">
                <div id="menu_container">
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
        <%
            String nome = usuario.getNome();
            String iniciais = Utils.obterIniciais(nome);
        %>
        <div id="center_container"><div id="img_container_big"><%= iniciais %></div></div>
        <ul>
            <%
                if (usuario != null) { %>
                    <li><b>Nome:</b> <%= nome %></li>
                    <li><b>Email:</b> <%= usuario.getEmail() %></li>
            <% } else { %>
                    <p>Nenhum usuário logado.</p>
            <% } %>
        </ul>
    </div>
<script src="js/script.js"></script>
</body>
</html>