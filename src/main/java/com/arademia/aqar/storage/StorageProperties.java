package com.arademia.aqar.storage;


import com.arademia.aqar.util.SupportingTools;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@ConfigurationProperties("storage")
public class StorageProperties {

    private static String absolutePath = null;
    private static String filesDirectory = null;
    private String uploadRoute = null;

    // The supporting tools include the check of whether the DC/Fin is a twin or
    // not
    private static SupportingTools util = new SupportingTools();

    public StorageProperties() {
        checkFoldersExistence();
    }

    public String getLocation() {
        return uploadRoute;
    }

    public void setLocation(String location) {
        this.uploadRoute = location;
    }

    // This is a simple method that gets the absolute path of the project
    private String getPath() {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath;
        try {
            fullPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            fullPath = "";
        }
        String pathArr[] = fullPath.split("/WEB-INF/classes/");
        fullPath = pathArr[0];
        return fullPath.substring(1);
    }

    // This method checks the existence of the folders and create them if needed
    private void checkFoldersExistence() {
        // initialize the main routes depending on whether we are executing on Server or in Local Machine
        if (util.isExecutingOnServer()) {
            absolutePath = "/var/lib/tomcat/webapps/";
        } else {
            absolutePath = getPath();
            System.out.println(absolutePath);
        }

        //Initialize all the routes and the urls
        filesDirectory = absolutePath + "Backme-files/";
        uploadRoute = filesDirectory + "uploads/";

        // Create the Folders where they are messing
        if (!new File(filesDirectory).exists()) {
            new File(filesDirectory).mkdirs();
        }
        if (!new File(uploadRoute).exists()) {
            new File(uploadRoute).mkdirs();
        }
    }
}