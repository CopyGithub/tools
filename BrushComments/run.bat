@echo off
set device=03f2f1bd13ad2b7c
set package=mobi.mgeek.TunnyBrowser
set searchKey=海豚瀏覽器\,
set num=50

adb -s %device% push bin/UIAutomation.jar /data/local/tmp/UIAutomation.jar
for /l %%i in (1,1,1000)  do (
	adb -s %device% shell am force-stop com.android.vending
	adb -s %device% shell uiautomator runtest UIAutomation.jar -e params %package%\,%searchKey%%num% -c com.android.vending.test.BrushComments
)
pause