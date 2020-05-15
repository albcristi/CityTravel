
function logInFailed(){
    $("#invalidUserData")
        .css("display","block");
}

$(document).ready( () => {
    $("#invalidUserData")
        .css("display", "none");

    $("#log-in-button")
        .click(
            function () {
                let user_name = $("#user-name-input").val();
                let password = $("#usr-password-p").val();
                $.ajax(
                    {
                        url: 'http://localhost:8080/log-in-servlet',
                        type: 'POST',
                        data: JSON.stringify({user_name: user_name, password: password}),
                        dataType: 'json',
                        statusCode: {
                            200: function () {
                                console.log("log-in: success");
                            },
                            400: function () {
                                console.log("log-in: failed");
                                logInFailed();
                            }
                        }
                    }
                )
            }
        );
});