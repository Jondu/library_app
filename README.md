# Personal library app
Enables user to generate a list of books he/she owns and keep track on to whom any book has been lent to.  
Can add friend names and phone nr that can be attatched to books.  
All users can have only one book attatched to them.  

###About 
Back end built with Spring boot, Gradle.  
Front end with bootstrap, jQuery

###Configuration
1. Uses postgres database named "personallibrary", user and password information must be set at db.properties.  
2. Port set to 8080.  

###Known issues
1. No way to differentiate between users with same names. Can be avoided by writing in full name.
2. No way to delete users or books.
