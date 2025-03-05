<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Usuários</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h2>Usuários Cadastrados</h2>
    <ul>
        <%
            List<Usuario> usuarios = (List<Usuario>) session.getAttribute("usuarios");
            if (usuarios != null) {
                for (Usuario u : usuarios) { %>
                    <li><%= u.getNome() %> - <%= u.getEmail() %></li>
        <%      }
            } else { %>
                <p>Nenhum usuário cadastrado.</p>
        <% } %>
    </ul>
    <form action="index.jsp" method="get"><button type="submit">Voltar</button></form>
</body>
</html>