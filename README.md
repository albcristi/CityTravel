# CityTravel
Simple web application that uses JSP/Servlet technology

---

# Problem Task

Solve the following problem using the JSP/Servlet technology. State information (between web requests) is always stored in a database;<br/> you may store some state information in cookies/session objects. Write a web application for choosing a transportation route. The database
has a list of cities, each having a list of neighboring cities. The displays a web page with the current station chosen by the user and all    the neighboring cities to which this station is connected. The user can then choose a new destination which becomes the new current   station   and so on... At any time the user can specify that the current station is the final destination and in this case,   the application displays the complete route selected by the user so far. The user should also be able to change his/her mind and   come back to a previously selected station and change it.


----

#  Some specific requirements 

All web pages should be accessible only if the user logs in using a username and a password (create a session each time a user logs in,   and destroy the session when the user logs out). Have in mind the user experience when you implement the problem:

- add different validation logic for input fields
- do not force the user to input an ID for an item if he wants to delete/edit/insert it; this should happen automatically (e.g. the user - - clicks an item from a list, and a page/modal prepopulated with the data for that particular item is opened, where the user can edit it)
- add confirmation when the user deletes/cancels an item
do a bare minimum CSS that at least aligns the various input fields
