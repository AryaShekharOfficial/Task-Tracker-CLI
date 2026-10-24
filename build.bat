@echo off
javac -d bin src\*.java
jar cvfm task-cli.jar manifest.txt -C bin .
set "SCRIPT_DIR=%cd%\task-cli.jar"
echo @echo off > task-cli.bat
echo java -jar "%SCRIPT_DIR%" %%* >> task-cli.bat
echo To run 'task-cli' from anywhere, add this folder to your PATH: %cd%