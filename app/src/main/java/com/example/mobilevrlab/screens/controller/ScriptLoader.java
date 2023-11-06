package com.example.mobilevrlab.screens.controller;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class ScriptLoader {
    public final static int OPEN_DIRECTORY_CODE = 2015;

    // Prepare an intent to open the file selection menu for startActivityForResult()
    public Intent getIntentForFileSelection() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/xml"); // Because "application/xml" did not work, but maybe xml header was wrong?
        return intent;
    }

    // Get the selected file's Uri from the returned data from the file selection menu
    public void onActivityResult(int requestCode, int resultCode, Intent resultData, int resultOk, ContentResolver contentResolver) {
        if (requestCode == OPEN_DIRECTORY_CODE && resultCode == resultOk) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                System.out.println("URI: " + uri); // TODO remove in later issue
                try {
                    System.out.println(readTextFromUri(uri, contentResolver)); // TODO remove in later issue
                } catch (IOException e) {
                    System.out.println("Error opening file"); // TODO remove in later issue
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Get the String file contents from a Uri pointing to a file
    public String readTextFromUri(Uri uri, ContentResolver contentResolver) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = contentResolver.openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}