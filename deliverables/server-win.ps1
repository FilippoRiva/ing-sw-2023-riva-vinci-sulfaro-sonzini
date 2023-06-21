$server=$args[0]
if($server -eq $null){
    java -jar "jar\server\ms_server.jar"
}else{
    java -jar "jar\server\ms_server.jar" --rmi $server
}
