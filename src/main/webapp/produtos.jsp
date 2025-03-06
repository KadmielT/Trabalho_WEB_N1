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
            <div id="header_container"><p id="text">IMG</p><p id = "text"><%= usuario.getNome() %></p></div>
        <% }
    %>
    <div id = "body_container">
        <h2>Cadastrar Produto</h2>
        <form action="produtos" method="post">
            Nome: <input type="text" name="nome" required><br>
            Descrição: <input type="text" name="descricao" required><br>
            Preço: <input type="number" step="0.01" name="preco" required><br>
            Quantidade: <input type="number" name="quantidade" required><br>
            <button type="submit">Cadastrar</button>
        </form>

        <h2>Produtos Disponíveis</h2>
        <ul>
            <%
                List<Produto> produtos = (List<Produto>) request.getAttribute("produtos");
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
        <a href="index.jsp">Voltar</a> | <a href="carrinho">Ver Carrinho</a>
    </div>
</body>
</html>