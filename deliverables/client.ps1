if ($args[0] -eq "-n")
{
    Write-Output "Disabling ANSI escape sequences"
    $env:ANSI_ON = "false"
    $client = $args[1]
    $server = $args[2]
    if (($client -eq $null) -or ($server -eq $null))
    {
        java -jar "jar\client\client-win\softeng-gc16.jar"
    }
    else
    {
        java -jar "jar\client\client-win\softeng-gc16.jar" --client $client --server $server
    }
}
else
{
    Write-Output "Enabling ANSI escape sequences"
    $env:ANSI_ON = "true"
    $client = $args[0]
    $server = $args[1]
    if (($client -eq $null) -or ($server -eq $null))
    {
        java -jar "jar\client\client-win\softeng-gc16.jar"
    }
    else
    {
        java -jar "jar\client\client-win\softeng-gc16.jar" --client $client --server $server
    }
}