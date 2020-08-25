let pizzas = JSON.parse(window.sessionStorage.getItem("pizzas"));
console.log(pizzas);

pizzas.forEach((pizza) => {
    createOrderItemElement(pizza);
});

fillPurchaseTable(pizzas);
function createOrderItemElement(pizza) {
    let name = pizza.name;
    let ingredients = "";
    let price = pizza.price;
    let pictureName = ""
    let sizes = "";

    if(pizza.name.toLowerCase() === "Generierte pizza".toLowerCase()){
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
    paragraphTag.innerHTML = ingredients + "<h4>Gesamtpreis: " + price + " &euro; <a href='#' id='deleteBtn"+name+"' >L&ouml;schen ?</a></h4><br>";

    let orderTextContainer = document.createElement("div");
    orderTextContainer.className = "order__text";


    figureContainer.appendChild(imgContainer);
    orderTextContainer.append(h3Tag, paragraphTag)
    orderDivContainer.append(figureContainer, orderTextContainer);
    rowDivContainer.appendChild(orderDivContainer);
    orderContentContainer.appendChild(rowDivContainer);

    let deleteButton = document.getElementById("deleteBtn"+name);
    deleteButton.onclick = () =>{
        deletePizzaFromList(name);
    }
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
function deletePizzaFromList(pizzaName){
    const pizzaEntry = pizzas.find(pizza=>pizza.name === pizzaName);
    const index = pizzas.indexOf(pizzaEntry);
    pizzas.splice(index,1);
    window.sessionStorage.setItem("pizzas",JSON.stringify(pizzas));
    window.location.reload();
}