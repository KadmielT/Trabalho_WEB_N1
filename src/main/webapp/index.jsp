<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Usuário</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h2>Cadastro de Usuário</h2>
    <% if (request.getAttribute("erro") != null) { %>
        <p style="color:red;"><%= request.getAttribute("erro") %></p>
    <% } %>
    <form action="cadastrar" method="post">
        Nome: <input type="text" name="nome" required><br>
        E-mail: <input type="email" name="email" required><br>
        Senha: <input type="password" name="senha" required><br>
        Confirmar Senha: <input type="password" name="confirmarSenha" required><br>
        <button type="submit">Cadastrar</button>
    </form>
</body>
</html>