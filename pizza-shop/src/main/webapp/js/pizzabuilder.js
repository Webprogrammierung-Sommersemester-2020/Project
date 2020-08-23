const BASE_PRICE = 15.00;
const INGREDIENT_PRICE = 0.50;

let pizza = {
    name: "Generated Pizza",
    ingredients: [
        {
            name: "Kaese",
            price: 0.0
        },
        {
            name: "Tomate",
            price: 0.0
        },
        {
            name: "Paprika",
            price: 0.5
        },
        {
            name: "Pilze",
            price: 0.5
        },
        {
            name: "Gruene Pfeffer",
            price: 0.5
        },
        {
            name: "Weisse Sosse",
            price: 0.5
        },
        {
            name: "Gluten-frei",
            price: 0.5
        },
    ],
    sizes: [],
    price: 17.50
}

let pizzas = new Array();

let buttonPepperonni = document.querySelector(".btn-pepperonni");
let buttonMushrooms = document.querySelector(".btn-mushrooms");
let buttonGreenPeppers = document.querySelector(".btn-green-peppers");
let buttonSauce = document.querySelector(".btn-sauce");
let buttonCrust = document.querySelector(".btn-crust");

buttonPepperonni.addEventListener("click", () => {
    buttonPepperonni.classList.toggle("active");
    toggleVisibilityOfElementChildren("#peppers", "pep")
    toggleVisibilityByElementId("li-pepperoni");
    calcPrice();
});

buttonMushrooms.addEventListener("click", () => {
    let cap = document.getElementsByClassName("up");
    let stem = document.getElementsByClassName("bottom");

    buttonMushrooms.classList.toggle("active");
    calcPrice();

    for (let j = 0; j < stem.length; j++) {
        stem[j].classList.toggle("stem");
        cap[j].classList.toggle("cap");
    }
    toggleVisibilityByElementId("li-mushrooms");
});

buttonGreenPeppers.addEventListener("click", () => {
    buttonGreenPeppers.classList.toggle("active");
    toggleVisibilityOfElementChildren("#green-peppers", "green-pepper")
    toggleVisibilityByElementId("li-green-peppers");
    calcPrice();
});

buttonSauce.addEventListener("click", () => {
    buttonSauce.classList.toggle("active");
    toggleVisibilityOfElementChildren(".crust", "sauce")
    toggleVisibilityByElementId("li-sauce");
    calcPrice();
})

buttonCrust.addEventListener('click', () => {
    buttonCrust.classList.toggle("active")
    toggleVisibilityByElementId("li-crust");
    calcPrice();
})

function toggleVisibilityOfElementChildren(element, toggleClass) {
    let elements = document.querySelector(element).children;
    for (let i = 0; i < elements.length; i++) {
        elements[i].classList.toggle(toggleClass);
    }
}

function toggleVisibilityByElementId(id) {
    let li_element = document.getElementById(id);
    li_element.style.display === "none" ? li_element.style.display = "block" : li_element.style.display = "none";
}

function calcPrice() {
    let pricePerIngredient = INGREDIENT_PRICE;
    pizza.price = BASE_PRICE;

    if (buttonPepperonni.classList.contains("active")) {
        addIngredientWithPriceToPizza("Paprika", pricePerIngredient);
    }
    else {
        deleteIngredientFromPizza("Paprika")
    }

    if (buttonMushrooms.classList.contains("active")) {
        addIngredientWithPriceToPizza("Pilze", pricePerIngredient);
    }
    else {
        deleteIngredientFromPizza("Pilze")
    }

    if (buttonGreenPeppers.classList.contains("active")) {
        addIngredientWithPriceToPizza("Gruene Pfeffer", pricePerIngredient);
    }
    else {
        deleteIngredientFromPizza("Gruene Pfeffer")
    }
    if (buttonSauce.classList.contains("active")) {
        addIngredientWithPriceToPizza("Weisse Sosse", pricePerIngredient);
    }
    else {
        deleteIngredientFromPizza("Weisse Sosse")
    }
    if (buttonCrust.classList.contains("active")) {
        addIngredientWithPriceToPizza("Gluten-frei", pricePerIngredient);
    }
    else {
        deleteIngredientFromPizza("Gluten-frei")
    }
    let priceElement = document.getElementById("totalPrice");
    priceElement.innerHTML = pizza.price;
}

function addIngredientWithPriceToPizza(ingredient, pricePerIngredient) {
    pizza.ingredients.push({name: ingredient, price: pricePerIngredient});
    pizza.price += pricePerIngredient;
}

function deleteIngredientFromPizza(ingredient){
    let component = pizza.ingredients.find(i=>i.name === ingredient);
    if(component){
        let index = pizza.ingredients.indexOf(component);
        pizza.ingredients.splice(index);
    }
}

function addPizzaToList() {
    pizzas.push(pizza)
    const pizzasJson = JSON.stringify(pizzas)
    window.sessionStorage.setItem("pizzas",pizzasJson);
}