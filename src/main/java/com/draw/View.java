package com.draw;

import com.graph.Graph_input;
import org.graphstream.algorithm.randomWalk.RandomWalk;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;


/**
 * Created by aleksandrkucherov on 03.11.16.
 */
public class View {

    private static int edge_counter;

    public Graph graph(Graph_input graph){
        String styleSheet =
                "node { " +
                        "size: 10px; " +
                        "fill-color: #777; " +
                        // "text-mode: hidden; " +
                        "z-index: 0; " +
                        "}" +
                        "edge { " +
                        "shape: line; " +
                        "fill-color: #222; " +
                        "arrow-size: 3px, " +
                        "2px; " +
                        "}"+
                        "node.marked {" +
                        "fill-color: red;" +
                        "}";

        Graph graph_new = new MultiGraph(
                graph.getInfo().getName() + " by " + graph.getInfo().getAuthor()
        );
        graph_new.addAttribute("ui.quality");
        graph_new.addAttribute("ui.antialias");
        graph_new.setStrict(false);
        graph_new.setAutoCreate( true );
        View.edge_counter=0;

        for (int i=0; i< graph.getNodes().size();i++){
            graph_new.addNode(String.valueOf(graph.getNodes().get(i).getId()));
            Node n = graph_new.getNode(String.valueOf(graph.getNodes().get(i).getId()));
            n.setAttribute(
                    "xyz",
                    graph.getNodes().get(i).getCoordinates().get(0) - graph.getNodes().get(i).getCoverage()/2,
                    graph.getNodes().get(i).getCoordinates().get(1) - graph.getNodes().get(i).getCoverage()/2,
                    0
            );
            n.setAttribute("ui.label", String.valueOf(graph.getNodes().get(i).getId()));
            styleSheet += "node#" +
                    String.valueOf(graph.getNodes().get(i).getId()) +
                    " { size: "+ graph.getNodes().get(i).getCoverage() +" ; }";
            if(graph.getNodes().get(i).getStock()){
                n.setAttribute("ui.class", "marked");
            }
        }

        for (int i=0; i< graph.getNodes().size();i++){
            for (int j=0; j<graph.getNodes().get(i).getRelations().size();j++){
                graph_new.addEdge(
                        String.valueOf(View.edge_counter),
                        String.valueOf(graph.getNodes().get(i).getId()),
                        String.valueOf(graph.getNodes().get(i).getRelations().get(j))
                );
                View.edge_counter++;
            }

        }
        graph_new.addAttribute("ui.stylesheet", styleSheet);
        graph_new.display(false);

        try{
            graph_new.addAttribute("ui.screenshot", "src/data/graph_image.png");
        }catch(Exception e){
            e.printStackTrace();
        }

        return graph_new;

    }











}
