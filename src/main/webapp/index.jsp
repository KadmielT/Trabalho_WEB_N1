<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>MiniLoja</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>
    <div id = "index_body_container">
        <h2>MiniLoja</h2>
        <% if (request.getAttribute("erro") != null) { %>
            <p class="erro"><%= request.getAttribute("erro") %></p>
        <% } %>
        <form action="cadastrar" method="post">
            Nome: <input type="text" name="nome" required><br>
            E-mail: <input type="email" name="email" required><br>
            Senha: <input type="password" name="senha" required><br>
            Confirmar Senha: <input type="password" name="confirmarSenha" required><br>
            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html>