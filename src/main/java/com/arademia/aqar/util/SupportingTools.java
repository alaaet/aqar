package com.arademia.aqar.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupportingTools {



    public SupportingTools() {

    }

    public String generateString(int size) {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-","").substring(0,size);
    }

    public boolean isExecutingOnServer (){
        String systemipaddress = "";
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader(
                    url_name.openStream()));
            systemipaddress = sc.readLine().trim();
            if (!(systemipaddress.length() > 0)) {
                try {
                    InetAddress localhost = InetAddress.getLocalHost();
                    System.out.println((localhost.getHostAddress()).trim());
                    systemipaddress = (localhost.getHostAddress()).trim();
                } catch (Exception e1) {
                    systemipaddress = "Cannot Execute Properly";
                }
            }
        } catch (Exception e2) {
            systemipaddress = "Cannot Execute Properly";
        }
        if(systemipaddress.contains("167.86.81"))
            return true;
        else return false;
    }

    public String getIp (){
        String systemipaddress = "";
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader(
                    url_name.openStream()));
            systemipaddress = sc.readLine().trim();
            if (!(systemipaddress.length() > 0)) {
                try {
                    InetAddress localhost = InetAddress.getLocalHost();
                    System.out.println((localhost.getHostAddress()).trim());
                    systemipaddress = (localhost.getHostAddress()).trim();
                } catch (Exception e1) {
                    systemipaddress = null;
                }
            }
        } catch (Exception e2) {
            systemipaddress = null;
        }
        return systemipaddress;
    }


    public boolean isValidEmail(String input){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
