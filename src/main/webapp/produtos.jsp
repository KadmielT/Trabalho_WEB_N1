<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Produto, java.util.List" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Produtos</title>
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
    <div id="body_container">
        <div id="cadastro_produtos">
            <h2>Cadastro de Produtos</h2>

            <%-- Exibir mensagem de erro, se existir --%>
            <% if (request.getAttribute("erro") != null) { %>
                <p style="color: red; font-weight: bold;"><%= request.getAttribute("erro") %></p>
            <% } %>

            <form action="produtos" method="post">
                Nome: <input type="text" name="nome" required><br>
                Descrição: <input type="text" name="descricao" required><br>
                Preço: <input type="number" step="0.01" name="preco" min="0" required><br>
                Quantidade: <input type="number" name="quantidade" min="0" required><br>
                <button type="submit">Cadastrar</button>
            </form>
        </div>
    </div>
<script src="js/script.js"></script>
</body>
</html>