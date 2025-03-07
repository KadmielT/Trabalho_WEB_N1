package utils;

public class Utils {
    public static String obterIniciais(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "";
        }

        String[] palavras = texto.trim().split("\\s+");
        if (palavras.length == 1) {
            return palavras[0].substring(0, 1).toUpperCase(); // Retorna apenas a inicial da única palavra
        }

        return (palavras[0].charAt(0) + "" + palavras[palavras.length - 1].charAt(0)).toUpperCase();
    }
}