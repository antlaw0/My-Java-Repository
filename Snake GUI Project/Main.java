package com.company;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here

        String key = null;
        //Read key from file
        try (BufferedReader reader = new BufferedReader(new FileReader("key.txt"))) {
            key = reader.readLine();
            System.out.println(key);   //Just checking...
        } catch (Exception ioe) {
            System.out.println("No key file found, or could not read key. Please verify key.txt present");
            System.exit(-1);   //Quit program - need to fix before continuing.
        }

        // TO DO Use Maps API to get MCTC's elevation


    }
}


