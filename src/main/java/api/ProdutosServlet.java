package api;

import model.Produto;
import dao.ProdutoDAO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/get_produtos")
public class ProdutosServlet extends HttpServlet {
    private ProdutoDAO produtoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        produtoDAO = new ProdutoDAO(); // Inicializa o DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Define o tipo de resposta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Busca os produtos do banco de dados
        List<Produto> produtos = produtoDAO.buscarTodos();

        // Converte a lista para JSON
        Gson gson = new Gson();
        String json = gson.toJson(produtos);

        // Escreve o JSON na resposta
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
