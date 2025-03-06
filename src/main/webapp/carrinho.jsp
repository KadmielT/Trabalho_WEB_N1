<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Carrinho, model.ItemCarrinho" %>
<%@ page import="model.Usuario, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carrinho de Compras</title>
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
        <h2>Carrinho de Compras</h2>
        <ul>
            <%
                Carrinho carrinho = (Carrinho) request.getAttribute("carrinho");
                if (carrinho != null && !carrinho.getItens().isEmpty()) {
                    for (ItemCarrinho item : carrinho.getItens()) { %>
                        <li><%= item.getProduto().getNome() %> - Qtd: <%= item.getQuantidade() %> - Subtotal: R$<%= item.getSubtotal() %>
                            <form action="carrinho" method="post" style="display:inline;">
                                <input type="hidden" name="acao" value="remover">
                                <input type="hidden" name="nomeProduto" value="<%= item.getProduto().getNome() %>">
                                <button type="submit">Remover</button>
                            </form>
                        </li>
            <%      }
                    %>
                    <p>Total: R$<%= carrinho.getTotal() %></p>
                    <a href="pedido.jsp">Finalizar Pedido</a>
            <% } else { %>
                    <p>Carrinho vazio.</p>
            <% } %>
        </ul>
        <a href="produtos">Continuar Comprando</a>
    </div>
</body>
</html>