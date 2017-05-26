package com.algorythm;

import com.draw.View;
import com.graph.Generate;
import com.graph.Graph_input;

import java.io.*;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by aleksandrkucherov on 12/16/16.
 */
public class Accurate {
    static private double coverage;
    private static double getCoverage() {
        return coverage;
    }
    private static void setCoverage(double coverage) {
        Accurate.coverage = coverage;
    }

    static private double reliability;
    private static double getReliability() {
        return reliability;
    }
    private static void setReliability(double reliability) {
        Accurate.reliability = reliability;
    }


    private ArrayList<Integer> checkedNodes =new ArrayList<>();

    static private int stockId;
    private static int getStockId() {
        return stockId;
    }
    private static void setStockId(int stockId) {
        Accurate.stockId = stockId;
    }

    static private int[] nodesId = new int[3];
    static private int[] vertexes = new int[3];

    static private String logPath = "src/data/output/log_" + Clock.systemDefaultZone().instant().toString().replaceAll(":", "-") + ".txt";

    public void graph_init(Graph_input graph){
        for (int i=0; i < graph.getNodes().size(); i++){
            if(graph.getNodes().get(i).getStock() == Boolean.TRUE){
                setStockId(i);
                nodesId[0] = getStockId();
                break;
            }
        }
        setCoverage(0);
        System.out.println(" <--- Loop (The END)---> ");
        log2File("\n <--- Loop (The END)---> ");
        if (graph.getNodes().get(nodesId[0]).getReliability() > 0) {
            /*setCoverage(getCoverage() +
                    graph.getNodes().get(getStockId()).getReliability() *
                            graph.getNodes().get(getStockId()).getCoverage()
            );*/
            //checkedNodes.add(nodesId[0]);
            //rel(graph);
            vertexes = Arrays.copyOf(vertexes, graph.getNodes().size());
            Arrays.fill(vertexes, 1);
            stockId  = getStockId();

            System.out.println(" <--- Square coverage ---> " + factoring(graph, vertexes));
        } else {
            System.out.println(" <--- Loop (The END)---> ");
            log2File("\n <--- Loop (The END)---> ");
        }

    }

    private void log2File(String str){
        File f = new File(logPath);

        PrintWriter out = null;
        if ( f.exists() && !f.isDirectory() ) {
            try {
                out = new PrintWriter(new FileOutputStream(new File(logPath), true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.append(str);
            out.close();
        } else {
            try {
                out = new PrintWriter(logPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.println(str);
            out.close();
        }
    }


    private double factoring (Graph_input graph, int[] vertexes){
        int vId = stockId;
        //checkedNodes[]
        if ((vertexes[vId] > 0) || (vertexes[vId] < 2)){
            for (int i = 0; i < graph.getNodes().get(vId).getRelations().size(); i++) {
                if (!checkedNodes.contains(graph.getNodes().get(vId).getRelations().get(i))) {
                    vId = graph.getNodes().get(vId).getRelations().get(i);
                    break;
                }
            }

            calculateCoverage(vertexes);
            System.out.println(" <--- Branch Loop (The END)---> ");
            log2File("\n <--- Branch Loop (The END)---> ");

        } else {
            vertexes[vId] = 2;
            setReliability(graph.getNodes().get(vertexes[vId]).getReliability() * factoring(graph,vertexes));
            vertexes[vId] = 0;
            setReliability(getReliability() + (1 - graph.getNodes().get(vertexes[vId]).getReliability()) * factoring(graph,vertexes));
        }

        return calculateCoverage(vertexes);
    }

    private double calculateCoverage(int[] vertexes){
        Generate gen = null;
        try {
            gen = new Generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        View view = new View();
        view.graph(gen != null ? gen.getGraphInput() : null, vertexes);

        return view.getPixels()/1000;
    }

}
