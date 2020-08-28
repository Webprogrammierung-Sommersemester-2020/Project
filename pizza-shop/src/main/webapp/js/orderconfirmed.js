import PizzaService from "./services/pizzaservice.js";

let orderPizzas;
let order;
let user;
let pizzasToOffer = new Array();
let test;

window.onload = () => {
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
                    console.log("Pizza to offer: ");
                    console.log(pizzasToOffer);
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

