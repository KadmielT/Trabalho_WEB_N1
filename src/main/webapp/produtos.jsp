<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Produto, java.util.List" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Produtos</title>
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
                    </div><div id="menu_container">
                    <a href="minhasCompras">Minhas Compras</a>
                    <p id="text">IMG</p>
                    <p id = "text"><%= usuario.getNome() %>
                    </p><a href="index.jsp">Voltar</a></div></div>
                <% }
    %>
    <div id="body_container">
        <div id="cadastro_produtos">
            <h2>Cadastrar Produto</h2>

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
</body>
</html>