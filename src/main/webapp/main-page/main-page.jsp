
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>City Travelling </title>
    <link rel="stylesheet" type="text/css" href="main-page.css">
    <script src="../jquery-2.0.3.js"></script>
    <script src="main-page.js"></script>
</head>
<body>
    <div class="log-out-container">
        <div id="l-cont-1">
            <p id="userNameUpBar"></p>
        </div>
        <div  id="l-cont-2">
            <label id="log-out-button">Log Out</label>
        </div>
    </div>
    <div class="main-container">

        <div class="navigation-container">
            <!-- Here we should have the following buttons:
                o previous : go back to a given station
                o next: only if a neighbour station has
                  been selected, otherwise no action
                o generate route: if user wants to see
                 the created route -- ONLY IF AT LEAST
                 ONE CITY HAS BEEN SELECTED
            -->
            <div id="previous-cont">
                <label id="previous-button">Go back to station</label>
                <input id="prev-city-input"></input>
            </div>
            <div id="next-container">
                 <label id="next-button">Add to Route</label>
            </div>
            <div id="generate-route-container">
                <label id="generate-route">Generate Route</label>
            </div>
        </div>
        <div id="current-city-container">
            <!-- This Label will show the Current City / Station -->
            <label id="current-city-label"></label>
            <label id="selected-city-label"></label>
        </div>
        <div id="msgdiv">
            <p id="infoMessageCitiesContainer"></p>
        </div>
        <div id="cities-container">
            <!-- HERE WE WILL SHOW THE CITY NEIGHBOURS / ALL CITIES IF ROUTE IS EMPTY -->
        </div>
    </div>
</body>
</html>
