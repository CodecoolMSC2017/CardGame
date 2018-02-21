package com.codecool.api.network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Host{
    private InetAddress servAddress = null;
    private final int port = 2101;

    public Host() {
        try {
            servAddress = InetAddress.getLocalHost();
            this.start();
        } catch (UnknownHostException e) {
            System.err.println("The host is unknow.");
        }
    }

    public String getServerAddress() {
        return servAddress.getHostAddress();

    }

    private void start() {
        Runnable serverTask = () -> {
            try {
                System.out.println("Server IP address : " + servAddress.getHostAddress() + " port:" + port);

                ServerSocket server = new ServerSocket(port);
                System.out.println("waiting for client to connect");
                while (true) {                                              // infinte for testing
                    Socket sSocket = server.accept();
                    ObjectOutputStream outToClient = new ObjectOutputStream(sSocket.getOutputStream());
                    ObjectInputStream inFromClient = new ObjectInputStream(sSocket.getInputStream());
                    outToClient.writeObject(inFromClient.readObject()); // sending back the data as is right now
                    sSocket.close();
                }

                //server.close();  temporary not used

            } catch (IOException e) {
                System.err.println("Error during processing client requests");
                ;
            } catch (NullPointerException n) {
                System.err.println("No server ip established");
            } catch (ClassNotFoundException e) {
                System.err.println("Error, no class found, might did not get data from the client");
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }
}

