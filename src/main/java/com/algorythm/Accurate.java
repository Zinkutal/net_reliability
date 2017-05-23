package com.algorythm;

import com.graph.Graph_input;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by aleksandrkucherov on 12/16/16.
 */
public class Accurate {
    private static double getCoverage() {
        return coverage;
    }

    private static void setCoverage(double coverage) {
        Accurate.coverage = coverage;
    }

    static private double coverage;
    private ArrayList<Integer> checkedNodes =new ArrayList<>();

    private static int getStockId() {
        return stockId;
    }

    private static void setStockId(int stockId) {
        Accurate.stockId = stockId;
    }

    static private int stockId;

    static private int[] nodesId = new int[3];

    public void graph_init(Graph_input graph){
        for (int i=0; i < graph.getNodes().size(); i++){
            if(graph.getNodes().get(i).getStock() == Boolean.TRUE){
                setStockId(i);
                nodesId[0] = getStockId();
                break;
            }
        }
        setCoverage(0);
        if (graph.getNodes().get(nodesId[0]).getReliability() > 0) {

            setCoverage(getCoverage() +
                    graph.getNodes().get(getStockId()).getReliability() *
                            graph.getNodes().get(getStockId()).getCoverage()
            );
            //System.out.println(getCoverage());
            checkedNodes.add(nodesId[0]);

            nodesId[2]=666;
                rel(graph);
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
        }
        else {
            try {
                out = new PrintWriter(savestr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.println(str);
            out.close();
        }
    }

    private void rel(Graph_input graph){
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
    }

}
