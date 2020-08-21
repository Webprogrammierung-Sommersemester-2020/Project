import PizzaService from "./services/pizzaservice.js";
import HttpService from "./services/httpservice.js";


window.onload = () => {
    HttpService.doRequest('/pizza-shop/api/pizza/get/all', 'GET')
        .then((jsonString) => {
            const pizzas = JSON.parse(jsonString);
        })
        .catch(err => console.log(err));
}








