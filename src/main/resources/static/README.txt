

This is a SpringBoot web application with an Embedded H2 Database of Encrypted Strings.
We use Spring JPA to connect to the H2 Database.

It is also makes use of Json Web Token to implement the security.

User try to login from a HTML form, sending over https username and password with POST.
When the web application gets the credentials, encrypt them and look for them into the in memory database.

If the user exists, the Application generates a Token and sends back to the client as a cookie.

The client saves it into the browser as it was a normal cookie and sends it back to the Application for every request.

Data from the server is passed always through JSON rest data.

The client makes the parsing of the data and visualize it, as soon as it gets the response.

********************************
DATABASE:

users: ID, USERNAME, PASSWORD, PERMISSION

operations: ID, DATE, VALUE, DESCRIPTION, FK_ACCOUNT1, FK_ACCOUNT2

accounts: ID, FK_USER, TOTAL

********************************