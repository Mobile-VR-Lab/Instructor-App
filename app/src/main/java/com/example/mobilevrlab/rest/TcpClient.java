package com.example.mobilevrlab.rest;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class TcpClient {

    private final String serverIp = "0.0.0.0"; // Replace with actual IP
    private final int serverPort = 8080; // Replace with the actual port
    private final Gson gson = new Gson();

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean listening = false;
    private Thread listenerThread;

    private static TcpClient instance;

    // Private constructor to prevent instantiation
    private TcpClient() {

    }

    // Thread-safe method to get the instance
    public static synchronized TcpClient getInstance() {
        if (instance == null) {
            instance = new TcpClient();
        }
        return instance;
    }

    public interface ResponseCallback {
        void onCompleted(String response, Exception e);
    }

    public static void sendCommandToServer(Command command, ResponseCallback callback) {
        getInstance().sendCommandToServer_(command, callback);
    }
    protected void sendCommandToServer_(Command command, ResponseCallback callback) {
        String data = gson.toJson(command);
        sendDataToServer(data, callback);
    }

    protected void sendDataToServer(String data, ResponseCallback callback) {
        new Thread(() -> {
            try (Socket socket = new Socket(serverIp, serverPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println(data); // Send data to server

                // Wait for response
                String response = in.readLine();
                if (callback != null) {
                    callback.onCompleted(response, null); // Call the callback with the response
                }

            } catch (IOException e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.onCompleted(null, e); // Call the callback with the error
                }
            }
        }).start();
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }

    private final List<MessageListener> subscribers = new ArrayList<>();

    public void subscribe(MessageListener listener) {
        if (listener != null && !subscribers.contains(listener)) {
            subscribers.add(listener);
            if (subscribers.size() == 1) {
                startListening();
            }
        }
    }

    public void unsubscribe(MessageListener listener) {
        subscribers.remove(listener);
        if (subscribers.size() == 0) {
            stopListening();
        }
    }

    private void notifySubscribers(String message) {
        for (MessageListener listener : subscribers) {
            listener.onMessageReceived(message);
        }
    }

    private void startListening() {
        if (listening) {
            return;
        }
        listening = true;
        listenerThread = new Thread(() -> {
            try {
                socket = new Socket(serverIp, serverPort);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);

                while (listening) {
                    String message = input.readLine();
                    if (message != null) {
                        notifySubscribers(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                stopListening();
            }
        });
        listenerThread.start();
    }

    public void stopListening() {
        listening = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (listenerThread != null) {
                listenerThread.interrupt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
