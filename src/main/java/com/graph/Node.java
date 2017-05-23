package com.graph;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Node {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = new ArrayList<Double>();
    @SerializedName("relations")
    @Expose
    private List<Integer> relations = new ArrayList<Integer>();
    @SerializedName("reliability")
    @Expose
    private Double reliability;
    @SerializedName("coverage")
    @Expose
    private Double coverage;
    @SerializedName("stock")
    @Expose
    private Boolean stock;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The coordinates
     */
    public List<Double> getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @param coordinates
     * The coordinates
     */
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     *
     * @return
     * The relations
     */
    public List<Integer> getRelations() {
        return relations;
    }

    /**
     *
     * @param relations
     * The relations
     */
    public void setRelations(List<Integer> relations) {
        this.relations = relations;
    }

    /**
     *
     * @return
     * The reliability
     */
    public Double getReliability() {
        return reliability;
    }

    /**
     *
     * @param reliability
     * The reliability
     */
    public void setReliability(Double reliability) {
        this.reliability = reliability;
    }

    /**
     *
     * @return
     * The coverage
     */
    public Double getCoverage() {
        return coverage;
    }

    /**
     *
     * @param coverage
     * The coverage
     */
    public void setCoverage(Double coverage) {
        this.coverage = coverage;
    }

    /**
     *
     * @return
     * The stock
     */
    public Boolean getStock() {
        return stock;
    }

    /**
     *
     * @param stock
     * The stock
     */
    public void setStock(Boolean stock) {
        this.stock = stock;
    }

}