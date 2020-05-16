

$(document).ready(()=>{
    var user_name = '';
    var user_id = '';
    $.ajax({
        url: 'http://localhost:8080/user-session-servlet',
        type: 'GET',
        dataType: 'json',
        statusCode:{
            200: function (res) {
                user_name = res.user_name;
                user_id = res.user_id;
                document.getElementById("userNameUpBar")
                    .textContent = user_name;
            },
            401: function () {
                alert("Something went wrong...");
                window.location = '../index.jsp';
            }
        }
        }
    );

    $("#log-out-button")
        .click( () => {
            $.ajax({
                url: 'http://localhost:8080/log-out-servlet',
                type: 'GET',
                dataType: 'json',
                statusCode: {
                    200: function () {
                        window.location = '../index.jsp';
                    }
                }
            });
        });

});