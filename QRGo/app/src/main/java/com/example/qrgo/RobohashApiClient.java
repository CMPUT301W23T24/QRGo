package com.example.qrgo;
import android.os.AsyncTask;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class RobohashApiClient {
    public interface OnCompleteListener {
        void onComplete(String imageAsString, Exception e);
    }

    public CompletableFuture<String> requestImageAsync(String imageUrl) {
        CompletableFuture<String> future = new CompletableFuture<>();

        new ImageRequestTask(new OnCompleteListener() {
            @Override
            public void onComplete(String imageAsString, Exception e) {
                if (e != null) {
                    future.completeExceptionally(e);
                } else {
                    future.complete(imageAsString);
                }
            }
        }).execute(imageUrl);

        return future;
    }

    private static class ImageRequestTask extends AsyncTask<String, Void, String> {

        private OnCompleteListener listener;

        public ImageRequestTask(OnCompleteListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... urls) {
            String imageUrl = urls[0];
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Request failed with HTTP error code: " + responseCode);
                }

                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();

                byte[] imageBytes = outputStream.toByteArray();
                return Base64.getEncoder().encodeToString(imageBytes);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String imageAsString) {
            if (imageAsString != null) {
                listener.onComplete(imageAsString, null);
            } else {
                listener.onComplete(null, new Exception("Image request failed"));
            }
        }
    }
}
