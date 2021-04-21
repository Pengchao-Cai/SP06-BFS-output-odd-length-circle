package pxc190029;
import idsa.Graph;
import idsa.Graph.*;
import idsa.BFSOO;

import java.util.Scanner;
import java.io.File;
import java.util.*;
import java.util.List;


public class OddLengthCycle_Su {
    // do not assume that g is connected
    BFSOO bfsoo;
    List<Vertex> oddCycleList;
    Graph g;

    public List<Vertex> oddCycle(Graph g) {
        this.bfsoo = new BFSOO(g); //read into BFSOO
        this.oddCycleList = new LinkedList<>();
        this.g = g;
        for(Vertex v : g){
            if(!bfsoo.getSeen(v)){
                if(findOddCycleList(v)) return oddCycleList;
            }
        }
        System.out.println("this graph is bipartite");
        return null;
    }
    public boolean findOddCycleList(Vertex v){
        bfsoo.bfs(v);
        boolean isFind = false;
        Vertex verFrom = null;
        Vertex verTo = null;
        for(Vertex ver : this.g){
            if(bfsoo.getDistance(ver) == bfsoo.INFINITY){
                continue;
            }
            Iterable<Edge> edges = this.g.incident(ver);
            for(Edge edge : edges){
                if(bfsoo.getDistance(edge.fromVertex()) == bfsoo.getDistance(edge.toVertex())){
                    System.out.println("找到了");
                    System.out.println("from" + edge.fromVertex());
                    System.out.println("to" + edge.toVertex());
                    verFrom = edge.fromVertex();
                    verTo = edge.toVertex();
                    isFind = true;
                    break;
                }
            }
            if(isFind) break;
        }
        List<Vertex> listofVerFrom = new LinkedList<>();
        List<Vertex> listofVerTo = new LinkedList<>();
        if(isFind){
            while(verFrom.getName() != verTo.getName()){
                listofVerFrom.add(verFrom);
                listofVerTo.add(verTo);
                verFrom = bfsoo.getParent(verFrom);
                verTo = bfsoo.getParent(verTo);
            }
            oddCycleList.add(verFrom);
            oddCycleList.addAll(listofVerFrom);
            Collections.reverse(listofVerTo);
            oddCycleList.addAll(listofVerTo);
            oddCycleList.add(verFrom);
        }
        return isFind;
    }
    public static void main(String[] args) throws Exception{
//        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 -7   6 7 -1   7 6 -1 1";
        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 2 -7   6 7 -1   7 6 -1 1";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        // Read graph from input
        Graph g = Graph.readGraph(in,false); // read graph
        OddLengthCycle_Su odd = new OddLengthCycle_Su();
        List<Vertex> res = odd.oddCycle(g);
        System.out.println(res);
    }
}
