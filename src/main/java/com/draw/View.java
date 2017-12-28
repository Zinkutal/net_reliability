package com.draw;

import com.algorythm.Accurate;
import com.graph.Generate;
import com.graph.Graph_input;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkImages;

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

    private int max_pixels;

    private void setMaxPixels(int max_pixels) {
        this.max_pixels = max_pixels;
    }

    public int getMaxPixels(){
        return this.max_pixels;
    }

    private int pixels;

    private void setPixels(int pixels) {
        this.pixels = pixels;
    }

    public int getPixels(){
        return this.pixels;
    }

    private int countPixels(){
        BufferedImage image;
        int count = 0;

        try {
            File pathToFile = new File(this.IMAGE_PATH);
            image = ImageIO.read(pathToFile);
            setMaxPixels(image.getWidth() * image.getHeight());

            for (int i = 0; i < image.getWidth(); i++)
                for (int j = 0; j < image.getHeight(); j++) {
                    Color c = new Color(image.getRGB(i, j));
                    if (!c.equals(Color.WHITE))
                        count++;
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //System.out.println("Amount of all pixels: " + getMaxPixels());
        System.out.println("Amount of coloured pixels: " + count);
        return count;
    }

    public Graph graph(Graph_input graph, int[] vertexes){
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

        FileSinkImages pic = new FileSinkImages(FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.HD1080);
        pic.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);

        for (int id=0; id < vertexes.length; id++){
         if (vertexes[id] == 2){
             //graph_new.display(false);
             styleSheet += style_old;
             graph_new.addNode(String.valueOf(graph.getNodes().get(id).getId()));
             Node n = graph_new.getNode(String.valueOf(graph.getNodes().get(id).getId()));
             for (int var=0; var < vertexes.length; var++){
                 if (graph.getNodes().get(id).getRelations().contains(var)){
                     n.setAttribute(
                             "xyz",
                             graph.getNodes().get(id).getCoordinates().get(0) - graph.getNodes().get(id).getCoverage()/2,
                             graph.getNodes().get(id).getCoordinates().get(1) - graph.getNodes().get(id).getCoverage()/2,
                             0
                     );
                     n.setAttribute("ui.label", String.valueOf(graph.getNodes().get(id).getId()));
                     styleSheet += "node#" +
                             String.valueOf(graph.getNodes().get(id).getId()) +
                             " { size: "+ graph.getNodes().get(id).getCoverage() +" ; }";
                     if(graph.getNodes().get(id).getStock()){
                         n.setAttribute("ui.class", "marked");
                     }
                     graph_new.addAttribute("ui.stylesheet", styleSheet);
                     //graph_new.addAttribute("ui.screenshot", IMAGE_PATH);
                     try {
                         pic.writeAll(graph_new, IMAGE_PATH);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     setPixels(this.countPixels());
                 }
             }
         }
        }

        /*for (int i=0; i< graph.getNodes().size();i++){
            for (int j=0; j<graph.getNodes().get(i).getRelations().size();j++){
                graph_new.addEdge(
                        String.valueOf(View.EDGE_COUNTER),
                        String.valueOf(graph.getNodes().get(i).getId()),
                        String.valueOf(graph.getNodes().get(i).getRelations().get(j))
                );
                View.EDGE_COUNTER++;
            }
        }*/

        return graph_new;
    }

}
