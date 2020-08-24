let pizzas = JSON.parse(window.sessionStorage.getItem("pizzas"));
console.log(pizzas);

pizzas.forEach((pizza) => {
    createOrderItemElement(pizza);
});

function createOrderItemElement(pizza) {
    let name = pizza.name;
    let ingredients = "";
    let price = pizza.price;
    let pictureName = ""
    let sizes = "";

    if(pizza.name.toLowerCase() === "Generated pizza".toLowerCase()){
        pictureName = "Custom";
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
    paragraphTag.innerHTML = ingredients + "<h4>Gesamtpreis: " + price + " &euro; <a href='#'>L&ouml;schen ?</a></h4><br>";

    let orderTextContainer = document.createElement("div");
    orderTextContainer.className = "order__text";


    figureContainer.appendChild(imgContainer);
    orderTextContainer.append(h3Tag, paragraphTag)
    orderDivContainer.append(figureContainer, orderTextContainer);
    rowDivContainer.appendChild(orderDivContainer);
    orderContentContainer.appendChild(rowDivContainer);

    console.log(orderContentContainer);
}