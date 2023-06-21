$env:ANSI_ON="true"
$client=$args[0]
$server=$args[1]
if (($client -eq $null) -or ($server -eq $null)){
    java -jar "jar\client\client-win\softeng-gc16.jar"
}
else{
java -jar "jar\client\client-win\softeng-gc16.jar" --client $client --server $server
}