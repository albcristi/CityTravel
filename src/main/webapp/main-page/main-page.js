
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


function generateRouteRequest(callOnSuccess= (response)=>{console.log("default functio")
                                                            response.forEach(e=>console.log(e))}){
    $.ajax({
        url: 'http://localhost:8080/user-route-servlet',
        type: 'GET',
        dataType: 'json',
        statusCode:{
            200: function (response) {
               callOnSuccess(response);
            },
            401: function () {
                redirectFirstPage();
            }
        }
    })
}


function loadUserRouteStatus(){

}

$(document).ready(()=>{
    var user_name = '';
    var user_id = '';
    var currentCityJsonObject = null;

    loadUserRouteStatus()
    // GET INFORMATION ABOUT USER
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

    // LOG OUT: CLICKED EVENT HANDLER
    $("#l-cont-2") // we click the div, since the label of the log-out is too small
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

    // GENERATE ROUTE: CLICKED EVENT HANDLER
    $("#generate-route-container")
        .click(() =>{
            generateRouteRequest(); // TODO: ADD CALL-BACK FUNCTION
        })
});