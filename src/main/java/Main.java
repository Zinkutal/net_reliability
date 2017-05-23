import com.algorythm.Accurate;
import com.draw.TestRandomWalk;
import com.draw.View;
import com.graph.Generate;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class Main {

    public static void main(String[] args) throws Exception {
        Generate gen = new Generate();
        View view = new View();
        //view.graph(gen.getGraphInput());
        Graph graph = view.graph(gen.getGraphInput());

        Accurate accurate = new Accurate();
        accurate.graph_init(gen.getGraphInput());
        //accurate.getCover(gen.getGraphInput());


        //TestRandomWalk testRandomWalk = new TestRandomWalk();
        //testRandomWalk.TestRandomWalk(graph);
    }
}
