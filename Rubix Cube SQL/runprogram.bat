ECHO OFF 
cd C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\target\classes\com\company
del CubeDBGUI.class
copy "C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\src\main\java\com\company\CubeDBGUI.class" "C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\target\classes\com\company\CubeDBGUI.class"
cd C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\target\classes
::Need to change directory names to reflect the correct path
java -cp .;..\..\dependencies\* com.company.CubeDBGUI
:: Give the user some time to see the results. Because this is our last line, the program will exit and the command window will close once this line finishes. 
pause>null