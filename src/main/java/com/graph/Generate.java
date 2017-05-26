package com.graph;

import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by aleksandrkucherov on 03.11.16.
 */
public class Generate {

    private Graph_input graphInput;

    private String jsonSource = "src/data/input/json/graph_input.json";

    public Generate () throws IOException {

        String rjson = new ReadJSON().getJSON(jsonSource);
        this.graphInput = new Gson().fromJson(rjson, Graph_input.class);

    }

    public Graph_input getGraphInput() {
        return graphInput;
    }

}
