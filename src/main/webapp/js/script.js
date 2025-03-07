document.addEventListener("DOMContentLoaded", function () {
    var dropbtn = document.querySelector(".dropbtn");
    var dropdownMenu = document.getElementById("dropdownMenu");

    dropbtn.addEventListener("click", function (event) {
        event.stopPropagation(); // Evita fechar ao clicar no botão
        dropdownMenu.classList.toggle("active");
    });

    // Fecha o dropdown ao clicar fora
    document.addEventListener("click", function (event) {
        if (!dropbtn.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.classList.remove("active");
        }
    });
});