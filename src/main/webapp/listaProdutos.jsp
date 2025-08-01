<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Produto, java.util.List" %>
<%@ page import="model.Usuario, java.util.List" %>
<%@ page import="utils.Utils" %>
<%@ page import="java.util.Objects" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Produtos</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) { %>
            <div id="header_container">
                <div id="menu_container">
                    <a href="listaProdutos" id="menu_text">Lista de Produtos</a>
                </div>
                <div id="menu_container">
                    <%
                    String nome = usuario.getNome();
                    String iniciais = Utils.obterIniciais(nome);
                    %>
                    <div id="img_container"><%= iniciais %></div>
                    <label for="opcoes"></label>
                    <div class="dropdown">
                        <button class="dropbtn"><%= nome %></button>
                        <div class="dropdown-content" id="dropdownMenu">
                            <a href="listar.jsp">Ver Perfil</a>
                            <a href="carrinho">Ver Carrinho</a>
                            <a href="minhasCompras">Meus pedidos</a>
                            <a href="index.jsp">Sair</a>
                        </div>
                    </div>
                </div>
            </div>
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
                            <li>
                                <div id="product_container">
                                    <div id="product_name_container"><%= p.getNome() %></div>
                                    <div id="product_description_container">
                                        <%= p.getDescricao() %>
                                    </div>
                                    <div id="product_add_to_cart_container">
                                        <div id="product_price_container">
                                            <b>R$ <%= p.exibirPreco() %></b>
                                        </div>
                                        <div id="product_form_container">
                                            Estoque: <%= p.getQuantidade() %>
                                            <form action="carrinho" method="post" style="display:inline;">
                                                <input type="hidden" name="acao" value="adicionar">
                                                <input type="hidden" name="nomeProduto" value="<%= p.getNome() %>">
                                                <input type="number" name="quantidade" min="1" max="<%= p.getQuantidade() %>" required style="width: 50px;">
                                                <button type="submit">Adicionar ao Carrinho</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </li>
                <%      }
                    } else { %>
                        <p>Nenhum produto cadastrado.</p>
                <% } %>
            </ul>
        </div>
    </div>
    <script src="js/script.js"></script>
    <script>
        <% if (session.getAttribute("produtoAdicionado") != null) { %>
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: "O produto '<%= session.getAttribute("produtoAdicionado") %>' foi adicionado ao carrinho!",
                timer: 3000,
                toast: true
            });
            <% session.removeAttribute("produtoAdicionado"); %>
        <% } %>
    </script>
</body>
</html>
