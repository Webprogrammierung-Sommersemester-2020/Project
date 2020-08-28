import HttpService from "./httpservice.js";

export default class AuthService {
    token = "";

    static sendAuthRequest = (username, password) => {
        const credentials = "Basic " + btoa(username + ":" + password)
        HttpService.doRequest("/pizza-shop/api/auth/token", "GET", {"Authorization": credentials})
            .then(
                (response) => {
                    this.token = response;
                    if (this.token) {
                        window.sessionStorage.setItem("token", this.token);
                    }
                }
            )
            .catch(error => console.log(error))
    }

    static isAuthorized() {
        this.token = window.sessionStorage.getItem("token");
        if (!this.token) {
            return false;
        }
        return true;
    }

    static setAuthTokenToSessionAs(itemName) {
        if (this.token) {
            const authToken = "Bearer " + this.token;
            window.sessionStorage.setItem(itemName, authToken);
            return true;
        }
        return false;
    }

    static logout(){
        return HttpService.doRequest("/pizza-shop/api/auth/logout", "POST", {"Content-Type":"application/json"});
    }
}