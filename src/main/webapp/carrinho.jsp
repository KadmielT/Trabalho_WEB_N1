<h2>Carrinho de Compras</h2>
<ul>
    <%
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho != null && !carrinho.getItens().isEmpty()) {
            for (ItemCarrinho item : carrinho.getItens()) { %>
                <li><%= item.getProduto().getNome() %> - R$<%= item.getProduto().getPreco() %> (Qtd: <%= item.getQuantidade() %>)</li>
    <%      }
        } else { %>
            <p>Seu carrinho está vazio.</p>
    <% } %>
</ul>

<% if (carrinho != null && !carrinho.getItens().isEmpty()) { %>
    <form action="finalizarPedido" method="post">
        <button type="submit">Finalizar Pedido</button>
    </form>
<% } %>

<a href="produtos.jsp">Continuar Comprando</a>
