package trabalho_grupo;

import java.util.*;
import javafx.util.Pair;

public class Testar {

    public static void main(String[] args) {
        ArrayList<Pair<Integer,ArrayList<Integer>>> postinglist = new ArrayList<>();
        
        ArrayList<Integer> pos1 = new ArrayList<>(Arrays.asList(7, 18, 33, 72, 86, 231));
        Pair<Integer,ArrayList<Integer>> p1 = new Pair<>(1, pos1);
        postinglist.add(p1);

        ArrayList<Integer> pos2 = new ArrayList<>(Arrays.asList(3, 149));
        Pair<Integer,ArrayList<Integer>> p2 = new Pair<>(2, pos2);
        postinglist.add(p2);

        ArrayList<Integer> pos4 = new ArrayList<>(Arrays.asList(17, 191, 291, 430, 434));
        Pair<Integer,ArrayList<Integer>> p4 = new Pair<>(4, pos4);
        postinglist.add(p4);
        
        ArrayList<Integer> pos5 = new ArrayList<>(Arrays.asList(363, 367));
        Pair<Integer,ArrayList<Integer>> p5 = new Pair<>(5, pos5);
        postinglist.add(p5);
        
        Dicionario dic = new Dicionario("be", 993427, postinglist);
        
        ArrayList<Pair<Integer,ArrayList<Integer>>> postinglist1 = new ArrayList<>();

        ArrayList<Integer> pos = new ArrayList<>(Arrays.asList(17, 191, 291));
        Pair<Integer,ArrayList<Integer>> p = new Pair<>(1, pos);
        postinglist1.add(p);

        dic.add("to", 23, postinglist1);
        
        System.out.println(dic.toString());
        System.out.println("----------\n");
        System.out.println(dic.toJSON());
    }
}
