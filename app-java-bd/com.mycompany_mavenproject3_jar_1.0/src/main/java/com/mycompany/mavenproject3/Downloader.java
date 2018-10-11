/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Daniel
 */
public class Downloader implements Runnable {

    String link;
    File out;

    public Downloader(String url, File out) {
        this.link = url;
        this.out = out;
    }


    @Override
    public void run() {
        try {
            URL url = new URL(link);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            double filesize = (double) http.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(this.out);
            BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
            byte[] buffer = new byte[1024];
            double downloaded = 0.00;
            int read;
            double percentDownloaded = 0.00;
            while ((read = in.read(buffer, 0, 1024)) >= 0) {
                bout.write(buffer, 0, read);
                downloaded += read;
                percentDownloaded = (downloaded * 100) / filesize;
                String percent = String.format("%4.f", percentDownloaded);
                System.out.println("Downloaded" + percent + "% of the file.");
            }
            bout.close();
            in.close();
            System.out.println("Download completo.");
        } catch (Exception e) {
        }
    }

}
