package com.codecool.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private final String serverAddress;
    private final int port = 2100;

    public Client(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    // temporary constructor with hard coded address (localhost)
    public Client(){
        serverAddress = "192.168.150.191";
    }

    public void run(){
        String clientIp;
        try{
            InetAddress address = InetAddress.getLocalHost();
            clientIp = address.getHostAddress();
            System.out.println("client ip address: " + clientIp);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Socket client = null;
        try {
            client = new Socket(serverAddress, port);
            OutputStream os = client.getOutputStream();
            InputStreamReader inp = new InputStreamReader(client.getInputStream());
            BufferedReader bf = new BufferedReader(inp);
            PrintWriter pw = new PrintWriter(os, true);
            System.out.println(bf.readLine());
            client.close();

        } catch (IOException e) {
            System.err.println("Failed to connect or no server running!");;
        }


    }

}
