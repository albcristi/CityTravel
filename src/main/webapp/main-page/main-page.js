
function redirectFirstPage() {
    alert("Something went wrong...\nYou'll be redirected to the first page!");
    window.location = '../index.jsp';
}

function getCityNeighbours(currentCityId, myFunction = (param)=>{console.log("default function")}){
    $.ajax({
        url: 'http://localhost:8080/city-neighbours-servlet',
        type: 'GET',
        data: {city_id:currentCityId},
        dataType: 'json',
        statusCode:{
            200: function (response) {
                console.log("getCityNeighbours: success --");
                response.forEach( e => console.log(e));
                //apply function
                myFunction(response);
            },
            401:
            function () {
                redirectFirstPage()
            }
        }
    })
}

$(document).ready(()=>{
    var user_name = '';
    var user_id = '';
    getCityNeighbours(1);
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
                redirectFirstPage()
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