import HttpService from "./httpservice.js";

export default class PizzaService {

    static findPizzaByNameAndSetInSession(pizzaName) {
        HttpService.doRequest("/pizza-shop/api/pizza/get/name?name=" + pizzaName, "GET")
            .then((jsonString) => {
                window.sessionStorage.setItem("details", jsonString);
                window.location.href = "details.html";
            }).catch(error => console.log(error))
    }

    static addPizzaElementToPage(pizza, btnName) {
        let name = pizza.name;
        let ingredients = "";
        let price = pizza.price;
        let pictureName = ""
        let sizes = "";

        if(pizza.name.toLowerCase() === "Generierte pizza".toLowerCase()){
            pictureName = "Custom";
            name="Custom"
        }
        else {
            pictureName = pizza.name;
        }
        pizza.ingredients.forEach((ingredient) => {
            ingredients += ingredient.name + ": " + ingredient.price + " &euro;<br>";
        });


        let orderContentContainer = document.querySelector(".order-container");

        let rowDivContainer = document.createElement("div");
        rowDivContainer.className = "row";

        let orderDivContainer = document.createElement("div");
        orderDivContainer.className = "order";

        let imgContainer = document.createElement("img");
        imgContainer.src = "img/"+pictureName+".jpg";

        let figureContainer = document.createElement("figure");
        figureContainer.className = "order__shape";

        let h3Tag = document.createElement("h3");
        h3Tag.innerHTML = name;

        let paragraphTag = document.createElement("p");
        paragraphTag.innerHTML = ingredients+ "<h4>Gesamtpreis: " + price + " &euro; <a href='#' id='eventFor"+name+"' >"+btnName+"</a></h4><br>";

        let orderTextContainer = document.createElement("div");
        orderTextContainer.className = "order__text";


        figureContainer.appendChild(imgContainer);
        orderTextContainer.append(h3Tag, paragraphTag)
        orderDivContainer.append(figureContainer, orderTextContainer);
        rowDivContainer.appendChild(orderDivContainer);
        orderContentContainer.appendChild(rowDivContainer);

    }

    static addPizzaToList(pizza) {
        let pizzas = JSON.parse(window.sessionStorage.getItem("pizzas"));
        let localPizza = {
            name: pizza.name,
            ingredients: pizza.ingredients.map((ingredient)=>{
                return ingredient;
            }),
            sizes: pizza.sizes.map((size)=>{
                return size;
            }),
            price: pizza.price
        }

        if (!pizzas){
            pizzas = new Array();
        }

        pizzas.push(localPizza);
        const pizzasJson = JSON.stringify(pizzas)
        window.sessionStorage.setItem("pizzas",pizzasJson);
        console.log(pizzas);
    }

    static deletePizzaFromList(pizzaName){
        let pizzas = JSON.parse(window.sessionStorage.getItem("pizzas"))
        if (pizzas){
            const pizzaEntry = pizzas.find(pizza=>pizza.name === pizzaName);
            const index = pizzas.indexOf(pizzaEntry);
            pizzas.splice(index,1);
            window.sessionStorage.setItem("pizzas",JSON.stringify(pizzas));
            window.location.reload();
        }
        console.log("Session is empty or do not contains \"pizzas\" as item")
    }
}