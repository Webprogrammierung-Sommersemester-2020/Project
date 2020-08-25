import PizzaService from "./services/pizzaservice.js";

let pizzas;

window.onload = ()=>{
    pizzas = JSON.parse(window.sessionStorage.getItem("pizzas"));
    pizzas.forEach((pizza) => {
        PizzaService.addPizzaElementToPage(pizza, "L&ouml;schen");
        let pizzaName = pizza.name;
        if (pizza.name.toLowerCase() === "Generierte pizza".toLowerCase()){
            pizzaName = "Custom";
        }
        let deleteButton = document.getElementById("eventFor"+pizzaName);
        deleteButton.onclick = () =>{
            PizzaService.deletePizzaFromList(pizza.name);
        }
    });
    fillPurchaseTable(pizzas);
}

function fillPurchaseTable(pizzas){
    let totalPrice = 0;
    let purchaseTableBody = document.getElementById("purchaseTableBody");
    let total = document.getElementById("total");
    let bodyContent = "";

    for(let i = 0; i<pizzas.length; i++){
        bodyContent += "<tr><td>"+pizzas[i].name+"</td>"+"<td>"+pizzas[i].price+" &euro;"+"</td></tr>"
        totalPrice += pizzas[i].price;
    }

    purchaseTableBody.innerHTML = bodyContent;
    total.innerHTML = totalPrice+" &euro;"

}
