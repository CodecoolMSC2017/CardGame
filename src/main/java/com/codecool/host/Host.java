package com.codecool.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Host{
    private InetAddress servAddress = null;
    private final int port = 2100;

    public Host() {
        try {
            servAddress = InetAddress.getLocalHost();
            this.start();
        } catch (UnknownHostException e) {
            System.err.println("The host is unknow.");
        }
    }

    public InetAddress getServerAddress() {
        return servAddress;

    }

    private void start() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    String server_IP = servAddress.getHostAddress();
                    System.out.println("Server IP address : " + server_IP);

                    ServerSocket server = new ServerSocket(port);
                    System.out.println("waiting for client to connect");
                    while (true) {  // infinte for testing
                        Socket sSocket = server.accept();
                        PrintWriter pwOut = new PrintWriter(sSocket.getOutputStream(), true);
                        InputStreamReader inp = new InputStreamReader(sSocket.getInputStream());
                        pwOut.println("ok");
                        BufferedReader bf = new BufferedReader(inp);
                        sSocket.close();
                    }

                    //server.close();

                } catch (IOException e) {
                    System.err.println("Error during processing client requests");
                    ;
                } catch (NullPointerException n) {
                    System.err.println("No server ip established");
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }
}

