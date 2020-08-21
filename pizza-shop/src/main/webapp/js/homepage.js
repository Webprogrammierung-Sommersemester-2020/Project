import PizzaService from "./services/pizzaservice.js";
import HttpService from "./services/httpservice.js";


window.onload = () => {
    HttpService.doRequest('/pizza-shop/api/pizza/get/all', 'GET')
        .then((jsonString) => {
            window.sessionStorage.setItem("pizzas", jsonString);
            const pizzas = JSON.parse(jsonString);
            pizzas.forEach((pizza) => {
                let index = 1;
                createOfferElement(pizza.name, index);
                index++;
            })
        })
        .catch(err => console.log(err));
}


function createOfferElement(pizzaName, index) {
    let offersContainer = document.getElementById("offers")
    let offerContainer = document.createElement("div");
    let imageContainer = document.createElement("div");
    let figure = document.createElement("figure");
    let figureCaption = document.createElement("figcaption");
    let image = document.createElement("img");
    let header = document.createElement("h3");

    imageContainer.className = "image";
    offerContainer.className = "offer";
    figure.className = "offer__shape";
    figureCaption.className = "offer__caption";
    header.className = "header";

    header.id = header.className + "-" + index;
    figure.id = figure.className + "-" + index;
    imageContainer.id = imageContainer.className + "-" + index;
    offerContainer.id = offerContainer.className + "-" + index;

    header.innerText = pizzaName;

    image.src = "img/" + pizzaName + ".jpg";

    figureCaption.appendChild(header)
    figure.append(image, figureCaption)
    offerContainer.appendChild(figure);
    imageContainer.appendChild(offerContainer);
    offerContainer.onclick = () => PizzaService.findPizzaByNameAndSetInSession(pizzaName);

    offersContainer.appendChild(imageContainer);

}







