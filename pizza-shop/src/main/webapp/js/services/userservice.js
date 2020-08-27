import HttpService from "./httpservice.js";

export class UserService{
    static createUser(user){
        HttpService.doRequest("/pizza-shop/api/user","POST", {"Content-Type":"application/json"},user)
            .then(
                responseCode => {
                    if (responseCode === 201){
                        window.location.href="login.html"
                    }
                }
            )
            .catch(error=>console.log(error))
    }

    static getUserByUserName(name){
        return HttpService.doRequest("/pizza-shop/api/user/get/"+name,"GET");
    }
}