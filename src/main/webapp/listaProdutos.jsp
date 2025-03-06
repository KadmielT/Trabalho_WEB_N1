<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Produto, java.util.List" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Produtos</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) { %>
            <div id="header_container"><div id="menu_container"><a href="produtos">Início</a><a href="listaProdutos">Lista de Produtos</a><a href="carrinho">Ver Carrinho</a></div><div id="menu_container"><p id="text">IMG</p><p id = "text"><%= usuario.getNome() %></p><a href="index.jsp">Voltar</a></div></div>
        <% }
    %>
    <div id="body_container">
        <div id="produtos">
            <h2>Produtos Disponíveis</h2>
            <ul>
                <%
                    List<Produto> produtos = (List<Produto>) request.getAttribute("listaProdutos");
                    if (produtos != null && !produtos.isEmpty()) {
                        for (Produto p : produtos) { %>
                            <li><%= p.getNome() %> - <%= p.getDescricao() %> - R$<%= p.getPreco() %> (Qtd: <%= p.getQuantidade() %>)
                                <form action="carrinho" method="post" style="display:inline;">
                                    <input type="hidden" name="acao" value="adicionar">
                                    <input type="hidden" name="nomeProduto" value="<%= p.getNome() %>">
                                    <input type="number" name="quantidade" min="1" max="<%= p.getQuantidade() %>" required style="width: 50px;">
                                    <button type="submit">Adicionar ao Carrinho</button>
                                </form>
                            </li>
                <%      }
                    } else { %>
                        <p>Nenhum produto cadastrado.</p>
                <% } %>
            </ul>
        </div>
    </div>
</body>
</html>