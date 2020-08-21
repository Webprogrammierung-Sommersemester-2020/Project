let pizzaDetails = JSON.parse(window.sessionStorage.getItem("details"));

console.log(pizzaDetails);

let message = pizzaDetails.name + " : " + pizzaDetails.price;
let detail = document.getElementById("details");

detail.innerHTML=message;


