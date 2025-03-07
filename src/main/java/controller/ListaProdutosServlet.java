package controller;

import model.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/listaProdutos")
public class ListaProdutosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("listaProdutos");
        if (produtos == null) {
            produtos = new ArrayList<>();
            produtos.add(new Produto("Notebook Acer 18.5", "Notebook Acer 18.5 - 16GB de Memória RAM, 1TB de Memória SSDM2, Processador AMD Ryzen 7 7200X, Placa de vídeo NVIDIA GeForce 4090Ti", 8500.00, 10));
            produtos.add(new Produto("Mouse Razer HLM-3", "Mouse sem fio Razer HLM-3 Modelo Gamer, com sensor de 20.000 DPI e switches ópticos, o Razer DeathAdder V2 oferece precisão e cliques rápidos. Seu design ergonômico e cabo Speedflex garantem conforto e movimentos suaves. Personalização total com RGB Chroma", 350.00, 25));
            produtos.add(new Produto("Teclado Logitech G Pro X", "O Teclado mecânico apresenta switches mecânicos personalizáveis, permitindo uma experiência de digitação única. Com iluminação RGB LIGHTSYNC, oferece total personalização e performance de alto nível em um design compacto e durável, ideal para jogadores profissionais.", 275.00, 15));
            request.getSession().setAttribute("listaProdutos", produtos);
        }
        request.setAttribute("listaProdutos", produtos);
        request.getRequestDispatcher("listaProdutos.jsp").forward(request, response);
    }
}