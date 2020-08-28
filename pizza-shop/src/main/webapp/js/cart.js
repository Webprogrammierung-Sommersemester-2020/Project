import PizzaService from "./services/pizzaservice.js";
import AuthService from "./services/authservice.js";
import {UserService} from "./services/userservice.js";
import HttpService from "./services/httpservice.js";
import OrderService from "./services/orderservice.js";

let pizzas;
let purchaseButton = document.getElementById("purchaseBtn");
let cancelBtn = document.getElementById("cancelBtn");
let userName;
let user;
let order;

window.onload = () => {
    pizzas = JSON.parse(window.sessionStorage.getItem("pizzas"));
    userName = window.sessionStorage.getItem("username");
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

purchaseButton.addEventListener("click", () =>
    {
        sendOrderAndRedirect();
    }
);

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

function sendOrderAndRedirect() {
    let token = window.sessionStorage.getItem("auth");
    if (token) {
        UserService.getUserByUserName(userName)
            .then(result => {
                user = JSON.parse(result);
                if (user && pizzas) {
                    order = {
                        user: user,
                        pizzas: pizzas,
                    }
                    OrderService.createOrder(order)
                        .then(responseStatus => {
                            if (responseStatus === 201) {
                                window.sessionStorage.setItem("pizzas", JSON.stringify(pizzas));
                                window.sessionStorage.setItem("deliveryAddress", JSON.stringify(user.address));
                                window.sessionStorage.setItem("order", JSON.stringify(order));
                                window.location.href = "orderconfirmed.html";
                            } else {
                                console.log("Order was not created, response status is: " + responseStatus)
                            }
                        })
                        .catch(error => console.log(error))
                }
            })
            .catch(error => console.log(error))
    } else {
        window.location.href = "login.html";
    }
}