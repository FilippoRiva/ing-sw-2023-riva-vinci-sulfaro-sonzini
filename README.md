# Software Engineering Project A.A. 2022-2023

**Progetto d'Ingegneria del Software A.A. 2022-2023**

**Professor:** Prof. Gianpaolo Cugola

**Group:** GC16

**Students**:

- Filippo Riva (matriculation number: 957652)
- Antonio Sulfaro (matriculation number: 960053)
- Francesco Vinci (matriculation number: )
- Anna Sonzini (matriculation number: 956790)

---

## Implemented features

| Feature                           | Implemented                                              |
|-----------------------------------|----------------------------------------------------------|
| All the rules ("Regole complete") | :heavy_check_mark:                                       |
| CLI                               | :heavy_check_mark:                                       |
| GUI                               | :heavy_check_mark:                                       |
| Socket                            | :heavy_check_mark:                                       |
| RMI                               | :heavy_check_mark:                                       |
| Advanced functionality 1 (FA 1)   | :heavy_check_mark: Multiple matches ("Partite multiple") |
| Advanced functionality 2 (FA 2)   | :heavy_check_mark: Chat                                  |

---

## Server / Client Launch

The following instructions are meant to be executed inside the [`./deliverables/`](./deliverables) folder

### Linux/Mac

#### Server

1. In a terminal Window run

``` bash
./server.sh [ server_ip ]
```

Where :

- server_ip : the ip of the machine you're running the server on.

If no server_ip flag is provided a random active network interface will be used to export java RMI objects.
Not setting a server_ip is not recommended if you're going to use java RMI since it may not be able to publish its
remote objects.

#### Client

1. In a terminal Window run

``` bash
./client.sh  [ -n ] [ client_ip ] [ server_ip ] 
```

Where :

- -n : disables ANSI escape sequences (just in case your terminal doesn't support them).
- client_ip : the ip of the machine you're running the client on.
- server_ip : the ip of the machine where the server is running.

For optimal Textual UI experience it is recommended not to set the -n option
and to use a terminal of at least 63 characters wide and 33 characters tall.

Keep in mind that parameters ordering is NOT meant to be mixed. Otherwise, the startup script might not work properly.

If no client_ip flag is provided a random active network interface will be used to export java RMI objects.
Not setting a client_ip is not recommended if you're going to use java RMI since it may not be able to connect
properly to the java RMI server.

If your terminal doesn't support ANSI escape sequences, or you're experiencing weird textual UI behaviour, please use
the -n option.

### Windows

#### Server

##### From CMD

1. In a CMD windows launched as administrator run

``` bash
powershell.exe .\server.ps1 [ server_ip ]
```

##### From PowerShell

1. In a PowerShell windows launched as administrator run

``` bash
.\server.ps1 [ server_ip ]
```

Where :

- server_ip : the ip of the machine you're running the server on.

If no server_ip flag is provided a random active network interface will be used to export java RMI objects.
Not setting a server_ip is not recommended if you're going to use java RMI since it may not be able to publish its
remote objects.

#### Client

##### From CMD

1. In a CMD windows launched as administrator run

``` bash
powershell.exe .\client.ps1  [ -n ] [ client_ip server_ip ] 
```

##### From PowerShell

1. In a PowerShell windows launched as administrator run

``` bash
.\client.ps1  [ -n ] [ client_ip server_ip ] 
```

If launching these commands you get an error like "+ FullyQualifiedErrorId : UnauthorizedAccess" follow these steps:
1. Open a PowerShell window as administrator
2. Type `Set-ExecutionPolicy Unrestricted` and press Enter
3. Confirm your choice typing S and press Enter

Now you are able to launch the application from PowerShell.


Where :

- -n : disables ANSI escape sequences (just in case your terminal doesn't support them).
- client_ip : the ip of the machine you're running the client on.
- server_ip : the ip of the machine where the server is running.

For optimal Textual UI experience it is recommended not to set the -n option
and to use a terminal of at least 63 characters wide and 33 characters tall.

Keep in mind that parameters ordering is NOT meant to be mixed. Otherwise, the startup script might not work properly.

If no client_ip flag is provided a random active network interface will be used to export java RMI objects.
Not setting a client_ip is not recommended if you're going to use java RMI since it may not be able to connect
properly to the java RMI server.

If your terminal doesn't support ANSI escape sequences, or you're experiencing weird textual UI behaviour, please use
the -n option.

### Additional options
When launching the server it's possible to set the serverLog level of detail.
ServerLog is used to get an ongoing list of updates of what's happening in the server, at various level of detail:
- SEVERE: shows bugs and critical errors (lowest level of detail);
- WARNING: shows non critical errors;
- INFO: shows generic network and controller info;
- CONFIG: shows config;
- FINE: shows network additional info;
- FINER: shows controller info;
- FINEST: shows debug controller info (highest level of detail);

By setting a certain level of detail, messages from lower levels of detail are also shown.

This feature is used for monitoring client interactions with the server.

To do this is necessary to set the LOG_LEVEL environmental variable.

#### Linux/MacOS
``` bash
export LOG_LEVEL=<LEVEL_OF_DETAIL>
```
####Windows
``` bash
$Env:LOG_LEVEL = "<LEVEL_OF_DETAIL>"
```



