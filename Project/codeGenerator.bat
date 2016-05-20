cd /d %~dp0
java -Dfile.encoding=UTF-8 -Xms64m -Xmx1024m -cp ..\project_out\production\MusicSite;lib\log4j-1.2.16.jar org.ailab.wimfra.codeGenerator.CodeGenGUI
pause