package com.algorythm;

import com.draw.View;
import com.graph.Graph_input;

import java.io.*;
import java.util.ArrayList;
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
            stockId = getStockId();
            factoring(graph, vertexes);
        } else {
            System.out.println(" <--- Loop (The END)---> ");
            log2File("\n <--- Loop (The END)---> ");
        }

    }

    private void log2File(String str){
        String savestr = "src/data/log.txt";
        File f = new File(savestr);

        PrintWriter out = null;
        if ( f.exists() && !f.isDirectory() ) {
            try {
                out = new PrintWriter(new FileOutputStream(new File(savestr), true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.append(str);
            out.close();
        } else {
            try {
                out = new PrintWriter(savestr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.println(str);
            out.close();
        }
    }


    private double factoring (Graph_input graph, int[] vertexes){
        if ((vertexes[stockId] != 0) || (vertexes[stockId] != 1)){
                if(stockId < graph.getNodes().size()){
                    return calculateCoverage(vertexes);
                } else {
                    stockId++;
                    System.out.println(" <--- Branch Loop (The END)---> ");
                    log2File("\n <--- Branch Loop (The END)---> ");
                    return calculateCoverage(vertexes);
                }
        } else {
            vertexes[stockId] = 1;
            setReliability(graph.getNodes().get(vertexes[stockId]).getReliability() * factoring(graph,vertexes));
            vertexes[stockId] = 0;
            setReliability(getReliability() + (1 - graph.getNodes().get(vertexes[stockId]).getReliability()) * factoring(graph,vertexes));
        }

        return calculateCoverage(vertexes);
    }

    private double calculateCoverage(int[] vertexes){
        //View view = new View();
        //view.countPixels(vertexes);

        return 0.44 * Math.random();
    }

    /*private void rel(Graph_input graph){
        if (nodesId[0] == nodesId[2]) {
            System.out.println(" <--- Loop (The END)---> ");
            log2File("\n <--- Loop (The END)---> ");
        } else {
            for (int i = 0; i < graph.getNodes().get(nodesId[0]).getRelations().size(); i++) {
                if (!checkedNodes.contains(graph.getNodes().get(nodesId[0]).getRelations().get(i))) {
                    nodesId[1] = graph.getNodes().get(nodesId[0]).getRelations().get(i);
                    if (graph.getNodes().get(nodesId[1]).getReliability() > 0) {
                        setCoverage(getCoverage() +
                                graph.getNodes().get(nodesId[1]).getReliability() *
                                        graph.getNodes().get(nodesId[1]).getCoverage()
                        );
                        System.out.println(getCoverage());
                        log2File("\n" + getCoverage());
                        checkedNodes.add(nodesId[1]);
                        nodesId[2] = nodesId[0];
                        nodesId[0] = nodesId[1];
                    }
                }
            }

            System.out.println("Array : ");
            log2File("\nArray : ");
            System.out.print("| ");
            log2File("| ");
            for (Integer check : checkedNodes) {
                System.out.print(check + " | ");
                log2File(check + " | ");
            }
            System.out.println("");
            log2File("\n");
            nodesId[0] = getStockId();
            rel(graph);
        }
    }*/

}
