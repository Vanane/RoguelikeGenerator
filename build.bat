del sources.txt
for /f %%A in ('forfiles /s /m *.java /c "cmd /c echo @relpath"') do echo %%~A >> sources.txt

javac -cp "src;lib/*;src/plugins/*" -d bin .\src\com\pitchounous\PluginLoader.java @sources.txt