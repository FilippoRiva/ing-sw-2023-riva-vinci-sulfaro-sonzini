if [ "$1" = "-n" ]
then
  echo Disabling ANSI escape sequences
  export ANSI_ON=false
  java --module-path jar/client/client-lin --module ingsw.myshelfie/it.polimi.myshelfie.ClientApp -jar jar/client/client-lin/ms_client_lin.jar --client "$2" --server "$3"
else
  export ANSI_ON=true
  java --module-path jar/client/client-lin --module ingsw.myshelfie/it.polimi.myshelfie.ClientApp -jar jar/client/client-lin/ms_client_lin.jar --client "$1" --server "$2"
fi
