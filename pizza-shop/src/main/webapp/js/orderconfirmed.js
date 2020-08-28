import PizzaService from "./services/pizzaservice.js";
import AuthService from "./services/authservice.js";

let orderPizzas;
let order;
let user;
let pizzasToOffer = new Array();

const toShopButton = document.getElementById("toShop");
const toGeneratorButton = document.getElementById("toGenerator");
const logoutButton = document.getElementById("logout");

window.onload = () => {
    window.sessionStorage.removeItem("pizzas");

    order = JSON.parse(window.sessionStorage.getItem("order"));
    user = order.user;
    orderPizzas = order.pizzas;

    console.log(orderPizzas);
    console.log(order);
    console.log(user);

    PizzaService.getAllPizzas()
        .then(result => {
            let storePizzas = JSON.parse(result);
            storePizzas.forEach(sPizza => {
                let foundIngredient = sPizza.ingredients.find(ingredient => ingredient.name === "Champignons"); //TODO:Ersetzen Champignons mit meist vorkommenden ingredienten
                if (foundIngredient) {
                    pizzasToOffer.push(sPizza);
                }
            });

            if (pizzasToOffer.length > 0) {
                const header = document.getElementById("header");
                header.innerHTML = "Haben Sie Interesse an unsere weitere Produkte ?";
                pizzasToOffer.forEach(
                    pizza => {
                        PizzaService.addPizzaElementToPage(pizza, "Bestellen ?");
                        let addToCardButton = document.getElementById("eventFor" + pizza.name);
                        addToCardButton.onclick = () => {
                            PizzaService.addPizzaToList(pizza);
                            window.location.href="cart.html";
                        }
                    }
                );
            } else {
                const header = document.getElementById("header");
                header.innerHTML = "Ihre bestellung wird gleich versandt";
                orderPizzas.forEach(
                    pizza => {
                        PizzaService.addPizzaElementToPage(pizza, "Nochmal bestellen ?");
                        let addToCardButton = document.getElementById("eventFor" + pizza.name);
                        addToCardButton.onclick = () => {
                            PizzaService.addPizzaToList(pizza);
                        }
                    }
                );
            }
        })
        .catch(error => console.log(error));
}

toShopButton.onclick = () => {
    window.location.href = "index.html";
}

toGeneratorButton.onclick = () => {
    window.location.href = "pizzabuilder.html";
}

logoutButton.onclick = () => {
    AuthService.logout()
        .then(
            httpStatus => {
                if (httpStatus === 204) {
                    window.sessionStorage.clear();
                    window.location.href = "index.html";
                }
            }
        )
        .catch(error => console.log(error))
}