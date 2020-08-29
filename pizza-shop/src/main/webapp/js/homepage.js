import PizzaService from "./services/pizzaservice.js";

let pizzas;
let offerContainer = document.getElementById("offer");
let cartButton = document.getElementById("cart");

window.onload = () => {
    PizzaService.getAllPizzas()
        .then(jsonString => {
            pizzas = JSON.parse(jsonString);
            pizzas.forEach((pizza) => {
                PizzaService.addPizzaElementToPage(pizza, "Zum Warenkorb Hinzuf&uuml;gen");
                let addToCardButton = document.getElementById("eventFor" + pizza.name);
                addToCardButton.onclick = () => {
                    PizzaService.addPizzaToList(pizza);
                    addToCardButton.innerHTML = "Eingef&uuml;gt"
                    addToCardButton.setAttribute("style",
                        "color:green; " +
                        "border:none;"+
                        "cursor: not-allowed;" +
                        "pointer-events:none;" +
                        "opacity:0.5; " +
                        "text-decoration:none;");
                }
            });
        })
        .catch(error => console.log(error))
}

offerContainer.onclick = () => {
    window.location.href = "pizzabuilder.html";
}

cartButton.onclick = () => {
    window.location.href = "cart.html";
}






