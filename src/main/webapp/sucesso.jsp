<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Operação Realizada</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) { %>
            <div id="header_container"><div id="menu_container"><a href="produtos.jsp">Início</a><a href="carrinho">Ver Carrinho</a></div><div id="menu_container"><p id="text">IMG</p><p id = "text"><%= usuario.getNome() %></p><a href="index.jsp">Voltar</a></div></div>
        <% }
    %>
    <div id = "body_container">
        <h2>Operação realizada com sucesso!</h2>
        <a href="produtos">Ver Produtos</a> | <a href="carrinho">Ver Carrinho</a>
    </div>
</body>
</html>