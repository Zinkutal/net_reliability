package com.draw;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.randomWalk.RandomWalk;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import static java.lang.Thread.sleep;

/**
 * Created by aleksandrkucherov on 11/18/16.
 */
public class TestRandomWalk {
    public void TestRandomWalk(Graph graph) {
        /*
        Graph graph         = new MultiGraph("random walk");
        Generator gen       = new DorogovtsevMendesGenerator();
        RandomWalk rwalk    = new RandomWalk();

        // We generate a 400 nodes Dorogovstev-Mendes graph.
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<400; i++) {
            gen.nextEvents();
        }
        gen.end();

        // We display the graph.
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.display();
        */

        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        //graph.display();

        RandomWalk rwalk    = new RandomWalk();

        // We configure the random walk to use twice as
        // much entities as nodes in the graph. To use
        // a small evaporation on the number of passes
        // per element and a last visited edge list of
        // 40 elements.
        rwalk.setEntityCount(graph.getNodeCount()*2);
        rwalk.setEvaporation(0.97);
        rwalk.setEntityMemory(40);
        rwalk.init(graph);

        // Compute the walks for 3000 steps only as an
        // example, but the test could run forever with
        // a dynamic graph if needed.
        for(int i=0; i<3000; i++) {
            rwalk.compute();
            updateGraph(graph, rwalk);
            try {
                //sleep(10000);
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        rwalk.terminate();

        // Only when finished we change the edges colors
        // according to the number of passes. This call could
        // be made inside the loop above to show the evolution
        // of the entities passes.
        //updateGraph(graph, rwalk);

        // We take a small screen-shot of the result.
        graph.addAttribute("ui.screenshot", "randomWalk.png");
    }

    /**
     * Update the edges with colors corresponding to entities passes.
     */
    public void updateGraph(Graph graph, RandomWalk rwalk) {
        double mine = Double.MAX_VALUE;
        double maxe = Double.MIN_VALUE;

        // Obtain the maximum and minimum passes values.
        for(Edge edge: graph.getEachEdge()) {
            double passes = rwalk.getPasses(edge);
            if(passes>maxe) maxe = passes;
            if(passes<mine) mine = passes;
        }

        // Set the colors.
        for(Edge edge:graph.getEachEdge()) {
            double passes = rwalk.getPasses(edge);
            double color  = ((passes-mine)/(maxe-mine));
            edge.setAttribute("ui.color", color);
        }
    }

    protected static String styleSheet =
            "edge {"+
                    "	size: 2px;"+
                    "	fill-color: red, yellow, green, #444;"+
                    "	fill-mode: dyn-plain;"+
                    "}"+
                    "node {"+
                    "	size: 6px;"+
                    "	fill-color: #444;"+
                    "}";
}