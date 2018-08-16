package co.id.wargamandiri.helper;

import android.util.Log;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpFileUpload implements Runnable {
    URL connectURL;
    byte[] dataToServer;
    FileInputStream fileInputStream = null;
    String fileName;
    String responseString;
    String status = "0";

    public HttpFileUpload(String urlString, String fileName, String uriFile) {
        try {
            this.fileName = fileName;
            this.fileInputStream = new FileInputStream(uriFile);
            this.connectURL = new URL(urlString);
        } catch (Exception e) {
            Log.i("HttpFileUpload", "URL Malformatted");
        }
    }

    public String Send() {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag = "fSnd";
        try {
            Log.e(Tag, "Starting Http File Sending to URL");
            HttpURLConnection conn = (HttpURLConnection) this.connectURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("uploaded_file", this.fileName);
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + this.fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            Log.e(Tag, "Headers are written");
            int bufferSize = Math.min(this.fileInputStream.available(), 5120);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = this.fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bufferSize = Math.min(this.fileInputStream.available(), 5120);
                bytesRead = this.fileInputStream.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            this.fileInputStream.close();
            dos.flush();
            Log.e(Tag, "File Sent, Response: " + String.valueOf(conn.getResponseCode()));
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while (true) {
                int ch = is.read();
                if (ch != -1) {
                    b.append((char) ch);
                } else {
                    this.status = b.toString();
                    Log.i("Response", this.status);
                    dos.close();
                    return this.status;
                }
            }
        } catch (MalformedURLException ex) {
            Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            return this.status;
        } catch (IOException ioe) {
            Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            return this.status;
        }
    }

    public void run() {
    }
}
