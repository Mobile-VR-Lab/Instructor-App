package com.example.mobilevrlab.rest;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {

    private final OkHttpClient client;
    private final Gson gson = new Gson();
    private final String baseUrl = "https://localhost:8080/"; // replace with actual server URL

    private BufferedReader input;
    private PrintWriter output;
    private boolean listening = false;
    private Thread listenerThread;

    private static RestClient instance;

    // Private constructor to prevent instantiation
    private RestClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    // Thread-safe method to get the instance
    public static synchronized RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
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

        if (command == Command.GET_STATUSES) { // TODO remove this
            String mockResponse = "[{\"name\":\"Headset 1\",\"connectionStatus\":\"Connected\",\"batteryLevel\":\"80\",\"errorStatus\":\"\"}]";
            callback.onCompleted(mockResponse, null);
            return;
        }

        String data = gson.toJson(command);
        RequestBody body = RequestBody.create(data, MediaType.get("application/json; charset=utf-8"));

        // Construct the request
        Request request = new Request.Builder()
                .url(baseUrl + "endpoint") // replace with the actual endpoint
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (callback != null) {
                    callback.onCompleted(null, e);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (callback != null) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        callback.onCompleted(response.body().string(), null);
                    } else {
                        callback.onCompleted(null, new IOException("Unexpected code " + response));
                    }
                }
            }
        });
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
                while (listening) {
                    // Construct the GET request to a specific endpoint that provides updates
                    Request request = new Request.Builder()
                            .url(baseUrl + "endpoint") // replace with actual endpoint
                            .build();

                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful() && response.body() != null) {
                        String message = response.body().string();
                        notifySubscribers(message);
                    }

                    // Sleep before polling again
                    Thread.sleep(100); // pollingInterval is the time in milliseconds
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        listenerThread.start();
    }

    public void stopListening() {
        listening = false;
        if (listenerThread != null) {
            listenerThread.interrupt();
        }
    }



}
