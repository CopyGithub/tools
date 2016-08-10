find ../src -name *.java > 1.txt
mkdir target
javac -encoding UTF-8 -d target @1.txt
jar cvfm UninstallApp.jar MANIFEST.MF -C target/ .
rm 1.txt
rm -r target
