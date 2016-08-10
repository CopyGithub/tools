@echo off
rem 定义环境变量
set localPath=%~dp0
rem 清理上次build的残留文件
rm -r -f bin
rm -f build.xml
rm -f %localPath%UIAutomation.jar
rem 获取build.xml文件
call android create uitest-project -n UIAutomation -t android-23 -p %localPath%.
rem 编译jar包
call ant build
pause