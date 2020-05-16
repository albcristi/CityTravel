var currentCity=null;

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


function generateRouteRequest(callOnSuccess= (response)=>{console.log("default function");
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
    generateRouteRequest(
        (response) => {
            if(response.length === 0){
                // CASE WHEN USER HAS NO ROUTE INITIALISED
                // we need to show all available cities/stations
                getCityNeighbours(-1, (response) => {
                    showStationInformation("","current-city-label","Select the Start Station");
                    generateList(response, "cities-container");
                });
                return;
            }
            // CASE WHEN USER HAS SOME STATIONS IN HIS ROUTE
            // SHOW CURRENT STATION INFORMATION AND STATION
            // NEIGHBOURS
            response.sort((el1,el2)=>el2.id-el1.id);
            window.currentCity = response[0];
            // SHOW CURRENT STATION
            showStationInformation(response[0],"current-city-label","Current Station");
            // SHOW NEIGHBOUR STATIONS
            getCityNeighbours(response[0].city_id, (response) => {
                generateList(response, "cities-container");
            });
        }
    )
}

// GENERATES A LIST OF STATIONS AND APPENDS IT TO
// A CONTAINER GIVEN VIA ITS ID
function generateList(response, citiesContainer) {
    let listItem = document.createElement("ul");
    listItem.setAttribute("id","stations");
    for (let city of response) {
        listValue = document.createElement("li");
        listValue.textContent = city.city_name;
        listValue.setAttribute("id",city.city_id); // PUT THE STATION ID AS THE ID ATTR.
                                                                // will make life easier :D
        listItem.appendChild(listValue);
    }
    document.getElementById(citiesContainer)
        .appendChild(listItem);
}

// ADDS TEXT TO THE ELEMENT HAVING THE SPECIFIED ID
function showStationInformation(station, elementID, beforeMessage){
    document.getElementById(elementID)
        .textContent = `${beforeMessage}: ${station.city_name}`;
}

$(document).ready(()=>{
    var user_name = '';
    var user_id = '';

    // find if user already has a route or not
    // and initialize information...
    loadUserRouteStatus();

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