package com.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by aleksandrkucherov on 03.11.16.
 */

public class ReadJSON {
    private String json;

    public String getJSON() throws IOException {

        try(BufferedReader br = new BufferedReader(new FileReader("src/data/graph_input.json"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            json = sb.toString();
            return json;
        }
    }

}


