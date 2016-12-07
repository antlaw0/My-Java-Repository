ECHO OFF 
cd C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube 
mvn compileSQL
cd C:\Users\Anthony\Desktop\Class Projects\My-Java-Repository\Rubix Cube SQL\target\classes
java -cp .;..\..\dependencies\* com.company.CubeDBGUI
pause>null