$env:ANSI_ON = 'true'
Start-Process java -ArgumentList "--module-path", "./jar/client/", "--module", "ingsw.myshelfie/it.polimi.myshelfie.ClientApp",  "-jar", "./jar/client/ms_client_win.jar"
# the actual command to launch it is
# java --module-path ./jar/client --module ingsw.myshelfie/it.polimi.myshelfie.ClientApp -jar ./jar/client/ms_client_win.jar
