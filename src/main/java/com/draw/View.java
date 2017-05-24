package com.draw;

import com.graph.Graph_input;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by aleksandrkucherov on 03.11.16.
 */
public class View {

    private static int EDGE_COUNTER;
    
    private final String IMAGE_PATH = "src/data/graph_image.png";

    public int countPixels(){

        BufferedImage image;
        int count = 0;
        int image_size = 0;

        try {
            File pathToFile = new File(this.IMAGE_PATH);
            image = ImageIO.read(pathToFile);
            try {
                image_size = image.getWidth() * image.getHeight();
                for (int i = 0; i < image.getWidth(); i++)
                    for (int j = 0; j < image.getHeight(); j++) {
                        Color c = new Color(image.getRGB(i, j));
                        if (!c.equals(Color.WHITE))
                            count++;
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Amount of all pixels: " + image_size);
        System.out.println("Amount of coloured pixels: " + count);
        return count;
    }

    public Graph graph(Graph_input graph){
        String styleSheet =
                "node { " +
                        "size: 10px; " +
                        "fill-color: #777; " +
                         "text-mode: hidden; " +
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

        String style_old = styleSheet;

        Graph graph_new = new MultiGraph(
                graph.getInfo().getName() + " by " + graph.getInfo().getAuthor()
        );
        graph_new.addAttribute("ui.quality");
        graph_new.addAttribute("ui.antialias");
        graph_new.setStrict(true);
        graph_new.setAutoCreate( true );
        View.EDGE_COUNTER=0;


        for (int i=0; i< graph.getNodes().size();i++){

            styleSheet += style_old;
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

            //styleSheet = style_old + styleSheet;
            graph_new.display(false);
            graph_new.addAttribute("ui.stylesheet", styleSheet);
            graph_new.addAttribute("ui.screenshot", IMAGE_PATH);

            try{
                this.countPixels();
                //if (this.countPixels() > 0) graph_new.clearAttributes();
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        for (int i=0; i< graph.getNodes().size();i++){
            for (int j=0; j<graph.getNodes().get(i).getRelations().size();j++){
                graph_new.addEdge(
                        String.valueOf(View.EDGE_COUNTER),
                        String.valueOf(graph.getNodes().get(i).getId()),
                        String.valueOf(graph.getNodes().get(i).getRelations().get(j))
                );

                View.EDGE_COUNTER++;
            }

        }

        return graph_new;
    }











}
