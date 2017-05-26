import com.algorythm.Accurate;
import com.graph.Generate;
import com.graph.ReadTXT;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {

        boolean k = new ReadTXT().getTXT();

        Generate gen = null;
        try {
            gen = new Generate();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Accurate accurate = new Accurate();
        accurate.graph_init(gen != null ? gen.getGraphInput() : null);

        //TestRandomWalk testRandomWalk = new TestRandomWalk();
        //testRandomWalk.TestRandomWalk(graph);
    }
}
