package com.graph;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Graph_input {

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("nodes")
    @Expose
    private List<Node> nodes = new ArrayList<Node>();

    /**
     *
     * @return
     * The info
     */
    public Info getInfo() {
        return info;
    }

    /**
     *
     * @param info
     * The info
     */
    public void setInfo(Info info) {
        this.info = info;
    }

    /**
     *
     * @return
     * The nodes
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     *
     * @param nodes
     * The nodes
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}
