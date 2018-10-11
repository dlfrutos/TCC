/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadtest1;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author daniel
 */
public class DownloadTest1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String FILE_URL = "https://github.com/dlfrutos/TCC/blob/master/app-java-bd/mavenproject2/JsonFile.json";
        String FILE_NAME = "JsonFile2.json";

        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
                FileOutputStream fileOutputStream
                = new FileOutputStream(FILE_NAME)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (Exception e) {
            printStackTrace();
            System.out.println("Deu merda.");
        }

    }

}
