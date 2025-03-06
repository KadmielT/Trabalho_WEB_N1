<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Usuários</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div id = "body_container">
        <h2>Usuário Cadastrado</h2>
        <ul>
            <%
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                if (usuario != null) { %>
                    <li><%= usuario.getNome() %></li>
                    <li><%= usuario.getEmail() %></li>
            <%      }
                } else { %>
                    <p>Nenhum usuário logado.</p>
            <% } %>
        </ul>
        <a href="index.jsp">Voltar</a> | <a href="produtos">Ver Produtos</a>
    </div>
</body>
</html>