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
            $("#current-city-label").text("");
            $("#selected-city-label").text("");
            $("#selected-city-label").removeAttr("class");
            $("#infoMessageCitiesContainer").text("Neighbour Stations:")
            $("#cities-container").children()
                .remove();
            $("#next-container").unbind("click");
            $("#next-container")
                .click(()=>{
                    let cityClassId = $("#selected-city-label").attr("class");
                    if(cityClassId === undefined){
                        alert("You need to select a station, in order to be able to add it to your Route");
                        return;
                    }
                    addCityToRoute(cityClassId);
                });

            if(response.length === 0){
                // CASE WHEN USER HAS NO ROUTE INITIALISED
                // we need to show all available cities/stations

                getCityNeighbours(-1, (response) => {
                    showStationInformation({city_name: "", city_id:-1},"current-city-label","Select the Start Station");
                    generateList(response, "cities-container");
                });
                return;
            }
            // CASE WHEN USER HAS SOME STATIONS IN HIS ROUTE
            // SHOW CURRENT STATION INFORMATION AND STATION
            // NEIGHBOURS
            response.sort((el1,el2)=>el2.route_id-el1.route_id);
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
    $("li").unbind("click");

    $("li")
        .click(function (){
            let stationId = $(this).attr("id");
            let stationName = $(this).text();
            let station = {city_name: stationName, city_id: stationId};
            showStationInformation(station,"selected-city-label","Selected Station");
        })
}

// ADDS TEXT TO THE ELEMENT HAVING THE SPECIFIED ID
function showStationInformation(station, elementID, beforeMessage){
    console.log(station);
    document.getElementById(elementID)
        .textContent = `${beforeMessage}: ${station.city_name}`;
    document.getElementById(elementID).setAttribute("class",station.city_id);
}

// ADDS THE SELECTED CITY TO THE USER ROUTE
function addCityToRoute(cityId){
    $.ajax({
        url: 'http://localhost:8080/user-route-servlet',
        type: 'POST',
        data: {city_id: cityId},
        dataType: 'json',
        statusCode:{
            200:
                function () {
                    console.log("success");
                    loadUserRouteStatus();
                },
            401: function () {
                redirectFirstPage();
            }
        }
    });

}

// MOVES BACK USER TO A CITY FROM ITS ROUTE
function goBackToStation(city_name){
    $.ajax({
        url: 'http://localhost:8080/user-route-servlet',
        type: 'GET',
        dataType: 'json',
        statusCode:{
            200:
                function (response) {
                  let cityID = null;
                  response.sort((el1,el2)=>el1.route_id - el2.route_id);
                  response.forEach(
                      el => {
                          if(el.city_name === city_name)
                              cityID = el.city_id;
                      }
                  );
                  if(cityID===null){
                      alert("City not found in your route!Try again!");
                      return;
                  }
                  $.ajax(
                      {
                          url: 'http://localhost:8080/user-route-servlet?city_id='+cityID,
                          type: 'DELETE',
                          data: {city_id: cityID},
                          dataType: 'json',
                          statusCode: {
                              200: function (result) {
                                    if(result===true){
                                        alert(`Moved back to station:${city_name}`);
                                        loadUserRouteStatus();
                                        return;
                                    }
                                    alert("This is your current station!");
                                    loadUserRouteStatus();
                              },
                              401: function () {
                                    redirectFirstPage();
                              }
                          }
                      }
                  )

                },
            401: function () {
                redirectFirstPage();
            }
        }
    });
}

function showFinalRoute(stations){
    $("#cities-container ul").remove();
    $("#infoMessageCitiesContainer").text("Your Generated Route");
    generateList(stations, "cities-container");
    stations.sort((el1,el2)=>el1.route_id-el2.route_id);
    console.log(stations);
    $("li").unbind("click");
    $("#current-city-label").text(`Start Station: ${stations[0].city_name}`);
    $("#selected-city-label").text(`End Station: ${stations[stations.length-1].city_name}`);
    $("#selected-city-label").removeAttr("class");
    $("#next-container").unbind("click");
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
            generateRouteRequest(showFinalRoute); // TODO: ADD CALL-BACK FUNCTION
        });

    // CLICK EVENT HANDLER FOR ADDING A CITY/STATION TO THE ROUTE
    $("#next-container")
        .click(()=>{
            let cityClassId = $("#selected-city-label").attr("class");
            if(cityClassId === undefined){
                alert("You need to select a station, in order to be able to add it to your Route");
                return;
            }
            addCityToRoute(cityClassId);
        });

    // CLICK EVENT HANDLER FOR GOING BACK TO AN EXISTING CITY/STATION
    $("#previous-button")
        .click(()=>{
            let userInputCity = $("#prev-city-input").val();
            if(userInputCity.length === 0){
                alert("Please enter a city name in order to move back");
                return;
            }
            goBackToStation(userInputCity);
        })
});