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
            <div id="header_container"><p id="text">IMG</p><p id = "text"><%= usuario.getNome() %></p></div>
        <% }
    %>
    <div id = "body_container">
        <h2>Operação realizada com sucesso!</h2>
        <a href="index.jsp">Voltar ao Cadastro</a> | <a href="produtos">Ver Produtos</a> | <a href="carrinho">Ver Carrinho</a>
    </div>
</body>
</html>