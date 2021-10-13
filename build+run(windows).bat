@echo off
mode 128, 48 
type src\META-INF
start /WAIT /B javac -d ./bin @src/META-INF
echo.
echo ^>^> Compiled! If no errors have been presented // Press any button to continue.
pause > nul
cd ./bin > nul
cls > nul
start /WAIT /B java pyramidbuilder.PyramidBuilder
pause