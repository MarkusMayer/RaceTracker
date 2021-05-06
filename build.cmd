cd mods
rmdir /s/q raceTracker
rmdir /s/q raceTrackerGUI
cd..
dir /s /b RaceTracker\src\*.java > sources.txt & javac --module-path %PATH_TO_FX%;c:\progs\guava\ -d mods/raceTracker @sources.txt & del sources.txt
dir /s /b RaceTrackerGUI\*.java > sources.txt & javac --module-path %PATH_TO_FX%;c:\progs\guava\;.\mods\ -d mods/raceTrackerGUI @sources.txt & del sources.txt
copy .\raceTracker\resources\* .\mods\raceTracker\
copy .\raceTrackerGUI\raceTrackerGUI\resources\* .\mods\raceTrackerGUI\
copy .\raceTrackerGUI\raceTrackerGUI\src\raceTrackerGUI\*.fxml .\mods\raceTrackerGUI\raceTrackerGUI

rmdir /s /q Z:\markus\raceTrackerGUI\guava
rmdir /s /q Z:\markus\raceTrackerGUI\mods
md z:\markus\raceTrackerGUI\guava
md z:\markus\raceTrackerGUI\mods
copy c:\progs\guava\*.jar z:\markus\raceTrackerGUI\guava
xcopy .\mods\* z:\markus\raceTrackerGUI\mods\ /E/H

