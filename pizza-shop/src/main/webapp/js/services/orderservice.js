import HttpService from "./httpservice.js";

export default class OrderService {
    static createOrder(order) {
        let authToken = window.sessionStorage.getItem("auth");
        if (authToken) {
            return HttpService.doRequest("/pizza-shop/api/order/create", "POST",
                {"Authorization": authToken, "Content-Type": "application/json"}, JSON.stringify(order));
        } else {
            console.log("createOrder() failed:\n" + "Token: " + authToken + "\n" + "Data: \n" + "\n");
        }
    }
}