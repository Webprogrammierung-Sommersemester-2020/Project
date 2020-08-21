import HttpService from "./httpservice.js";

export default class PizzaService {

    static findPizzaByNameAndSetInSession(pizzaName) {
        HttpService.doRequest("/pizza-shop/api/pizza/get/name?name=" + pizzaName, "GET")
            .then((jsonString) => {
                window.sessionStorage.setItem("details", jsonString);
                window.location.href = "details.html";
            }).catch(error => console.log(error))
    }
}