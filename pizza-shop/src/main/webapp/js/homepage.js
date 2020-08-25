import PizzaService from "./services/pizzaservice.js";
import HttpService from "./services/httpservice.js";

let pizzas;
let offerContainer = document.querySelector(".offer");
window.onload = () => {
 HttpService.doRequest("/pizza-shop/api/pizza/get/all", "GET")
     .then(jsonString=>{
         pizzas = JSON.parse(jsonString);
         pizzas.forEach((pizza)=>{
             PizzaService.addPizzaElementToPage(pizza, "Zum Warenkorb Hinzu&uuml;gen");
             let addToCardButton = document.getElementById("eventFor"+pizza.name);
             addToCardButton.onclick = () =>{
                 PizzaService.addPizzaToList(pizza);
             }
         });
     })
     .catch(error=>console.log(error))
}

offerContainer.onclick = () =>{
    redirectToPizzaGenerator();
}

function redirectToPizzaGenerator(){
    window.location.href="pizzabuilder.html";
}





