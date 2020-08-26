import PizzaService from "./services/pizzaservice.js";
import AuthService from "./services/authservice.js";

let pizzas;
let purchaseButton = document.getElementById("purchaseBtn");
let cancelBtn = document.getElementById("cancelBtn");

window.onload = () => {
    pizzas = JSON.parse(window.sessionStorage.getItem("pizzas"));
    if (pizzas) {
        pizzas.forEach((pizza) => {
            PizzaService.addPizzaElementToPage(pizza, "L&ouml;schen");
            let pizzaName = pizza.name;
            if (pizza.name.toLowerCase() === "Generierte pizza".toLowerCase()) {
                pizzaName = "Custom";
            }
            let deleteButton = document.getElementById("eventFor" + pizzaName);
            deleteButton.onclick = () => {
                PizzaService.deletePizzaFromList(pizza.name);
            }
        });
        fillPurchaseTable(pizzas);
    }
    if (pizzas === null || pizzas.length === 0) {
        purchaseButton.style.visibility = "hidden";
    }
}

purchaseButton.addEventListener("click", () => {
    let token = window.sessionStorage.getItem("auth");
    if (token) {
        // TODO: send order and redirekt to..
        window.location.href = "orderconfirmed.html";
    } else {
        window.location.href = "login.html";
    }
});

cancelBtn.addEventListener("click", () => {
    window.sessionStorage.removeItem("pizzas");
    window.location.href = "index.html";
});

function fillPurchaseTable(pizzas) {
    let totalPrice = 0;
    let purchaseTableBody = document.getElementById("purchaseTableBody");
    let total = document.getElementById("total");
    let bodyContent = "";

    for (let i = 0; i < pizzas.length; i++) {
        bodyContent += "<tr><td>" + pizzas[i].name + "</td>" + "<td>" + pizzas[i].price + " &euro;" + "</td></tr>"
        totalPrice += pizzas[i].price;
    }

    purchaseTableBody.innerHTML = bodyContent;
    total.innerHTML = totalPrice + " &euro;"

}
