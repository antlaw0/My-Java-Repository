ECHO OFF 
cd C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\target\classes\com\company
del CubeDB.class
copy "C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\src\main\java\com\company\CubeDB.class" "C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\target\classes\com\company\CubeDB.class"
cd C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\target\classes
::Need to change directory names to reflect the correct path
java -cp .;..\..\dependencies\* com.company.CubeDB
:: Give the user some time to see the results. Because this is our last line, the program will exit and the command window will close once this line finishes. 
pause>null