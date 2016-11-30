::ECHO OFF 
::CMD will no longer show us what command it’s executing(cleaner)
ECHO As a network admin, I’m getting tired of having to type these commands in! Hopefully, this saves me some time in the long run. 
:: change directory to target/classes
cd C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\DogSQL-master\target\classes
::Need to change directory names to reflect the correct path
java -cp .;..\..\dependencies\* com.company.DogDB
:: Give the user some time to see the results. Because this is our last line, the program will exit and the command window will close once this line finishes. 
pause>null