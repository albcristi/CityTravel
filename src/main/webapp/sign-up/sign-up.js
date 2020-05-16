
function signUpFailed(){
    $("#sgn-up-invalidUserData")
        .css("display","block");
}

function validateUserInput(user_name, password){
   return user_name.length > 3 && password.length > 3;
}

$(document).ready( () => {

    $("#sgn-up-invalidUserData")
        .css("display", "none");
    $("#sgn-up-invalid-length")
        .css("display", "none");

    $("#sgn-up-button")
        .click(
            function () {
                $("#sgn-up-invalidUserData")
                    .css("display", "none");
                $("#sgn-up-invalid-length")
                    .css("display", "none");

                let user_name = $("#sgn-up-user-name-input").val();
                let password = $("#sgn-up-usr-password-input").val();
                if(!validateUserInput(user_name,password)){
                    $("#sgn-up-invalid-length")
                        .css("display", "block");
                    return;
                }
                $.ajax(
                    {
                        url: 'http://localhost:8080/sign-up-servlet',
                        type: 'POST',
                        data: JSON.stringify({user_name: user_name, password: password}),
                        dataType: 'json',
                        statusCode: {
                            200: function () {
                                console.log("sign-up: success");
                                alert("Welcome To The Group :)\nYou will be redirected to the log-in page")
                                window.location = '../log-in/log_in.jsp'
                            },
                            400: function () {
                                console.log("sign-up: failed");
                                signUpFailed();
                            }
                        }
                    }
                )
            }
        );
});