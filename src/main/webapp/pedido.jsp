<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pedido Finalizado</title>
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
    <div id = "body_container">
        <h2>Pedido finalizado com sucesso!</h2>
        <p>Obrigado pela compra!</p>
        <a href="produtos">Voltar às compras</a>
    </div>
</body>
</html>