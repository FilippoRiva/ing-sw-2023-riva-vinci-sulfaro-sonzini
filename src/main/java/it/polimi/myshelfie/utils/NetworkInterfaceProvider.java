package it.polimi.myshelfie.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

public class NetworkInterfaceProvider {
    //returns the first active network interface he's able to find. otherwise it returns localhost
    public static String getInterfaceHostName(){
        System.out.println("--------------------------------------------------------");
        System.out.println("You have not provided your own IP.\n"+
                "Please provide your IP or press enter to use a random active network interface : ");
        String input = "";
        try {
            input = new BufferedReader(new InputStreamReader(System.in)).readLine();
            if (!input.equals("") && !InetAddress.getByName(input).isReachable(1000)) {
                System.out.println("   !! The specified IP is not reachable.");
                input = "";
            }
        } catch (UnknownHostException ex){
            System.err.println("Not a valid hostname");
            input = "";
        } catch (IOException e) {
            System.err.println("Unable to get user input");
            input = "";
        }
        if(!input.equals("")) return input;
        System.out.println("Loading Random Network Interface...");
        String result = "";
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface net : Collections.list(netInterfaces)) {
                if (net.isUp() && !net.isLoopback()) {
                    for (InetAddress addr : Collections.list(net.getInetAddresses())) {
                        if (!addr.getHostAddress().contains(":")) { //only IPv4
                            result = addr.getHostAddress();
                            break;
                        }
                    }
                }
            }
            if (result.equals("")) result = "localhost";
        } catch (SocketException e) {
            throw new RuntimeException("Unable to get Network Interfaces");
        }
        return result;
    }
}
