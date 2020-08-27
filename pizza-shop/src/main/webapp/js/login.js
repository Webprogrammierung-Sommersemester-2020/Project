import AuthService from "./services/authservice.js";

window.onload = () => {
    if (AuthService.isAuthorized()) {
        if (window.sessionStorage.getItem("pizzas")) {
            //window.location.href = "cart.html";
        } else {
            //window.history.back();
        }
    }

}
const username = document.getElementById("username");
const password = document.getElementById("password");
const loginForm = document.getElementById("login-form");
const registerButton = document.getElementById("registerBtn");

loginForm.addEventListener("submit", (event) => {
        event.preventDefault();
        login();
    }
);

registerButton.onclick = () => {
    window.location.href = "register.html";
}

function login() {
    if(AuthService.isAuthorized()){
        if(AuthService.setAuthTokenToSessionAs("auth")){
            window.sessionStorage.setItem("username",username.value);
            if (window.sessionStorage.getItem("pizzas")) {
                window.location.href = "cart.html";
            } else {
                window.history.back();
            }
        }
    }
    else if(!AuthService.isAuthorized()){
        AuthService.sendAuthRequest(username.value, password.value);
        if (AuthService.isAuthorized()) {
            if (AuthService.setAuthTokenToSessionAs("auth")) {
                if (window.sessionStorage.getItem("pizzas")) {
                    window.sessionStorage.setItem("username", username.value);
                    window.location.href = "cart.html";
                } else {
                    window.history.back();
                }
            }
        }
    }
    else {
        window.location.href="index.html";
    }
}

