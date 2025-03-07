<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meu Perfil</title>
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
        <h2>Usuário Cadastrado</h2>
        <ul>
            <%
                if (usuario != null) { %>
                    <li><%= usuario.getNome() %></li>
                    <li><%= usuario.getEmail() %></li>
            <% } else { %>
                    <p>Nenhum usuário logado.</p>
            <% } %>
        </ul>
    </div>
<script src="js/script.js"></script>
</body>
</html>