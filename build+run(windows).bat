@echo off
mode 128, 48 
type src\META-INF
start /WAIT /B ./jdk1.8.0_301/bin/javac.exe -d ./bin @src/META-INF
echo.
echo ^>^> Compiled! If no errors have been presented // Press any button to continue.
pause > nul
cd ./bin > nul
cls > nul
start /WAIT /B ../jdk1.8.0_301/bin/java.exe pyramidbuilder.PyramidBuilder
pause