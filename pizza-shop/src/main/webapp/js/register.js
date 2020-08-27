import {UserService} from "./services/userservice.js";

let registerForm = document.getElementById("registerForm");

registerForm.addEventListener("submit", (event) => {
    event.preventDefault();
    createUser();

})

function createUser() {
    let checkedRole;
    let roles = document.getElementsByName('role');

    for (let i = 0; i < roles.length; i++) {
        if (roles[i].checked) {
            checkedRole = roles[i].value;
        }
    }

    const user = {
        userName: document.getElementById("username").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
        role: checkedRole,
        address: {
            street: document.getElementById("street").value,
            number: parseInt(document.getElementById("number").value),
            suffix: document.getElementById("suffix").value,
            zipCode: document.getElementById("zipcode").value,
            city: document.getElementById("city").value
        }
    }
    UserService.createUser(JSON.stringify(user));
}