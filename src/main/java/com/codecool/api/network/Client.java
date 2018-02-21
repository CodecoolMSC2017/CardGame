package com.codecool.api.network;

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
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            // out.writeObject(...);
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            //Object obj = (Object)in.readObject();


            client.close();

        } catch (IOException e) {
            System.err.println("Failed to connect or no server running!");;
        }


    }

}
