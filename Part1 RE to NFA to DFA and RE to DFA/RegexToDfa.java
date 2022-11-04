
package complier_project_part1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class RegexToDfa {

    private static Set<Integer>[] followPos;
    private static Node root;
    private static Set<State> DStates;
    private static Set<String> input; //set of characters is used in input regex

    
    private static HashMap<Integer, String> symbNum;

    public static void main(String[] args) {
        initialize();
    }

    public static void initialize() {
        Scanner in = new Scanner(System.in);
        //allocating
        DStates = new HashSet<>();
        input = new HashSet<String>();

        String regex = getRegex(in);
        getSymbols(regex);

        
        SyntaxTree st = new SyntaxTree(regex);
        root = st.getRoot(); //root of the syntax tree
        followPos = st.getFollowPos(); //the followpos of the syntax tree

        State q0 = createDFA();
        DfaTraversal dfat = new DfaTraversal(q0, input);
        
        String str = getStr(in);
        boolean acc = false;
        for (char c : str.toCharArray()) {
            if (dfat.setCharacter(c)) {
                acc = dfat.traverse();
            } else {
                System.out.println("WRONG CHARACTER!");
                System.exit(0);
            }
        }
        if (acc) {
            System.out.println((char) 27 + "[32m" + "this string is acceptable by the regex!");
        } else {
            System.out.println((char) 27 + "[31m" + "this string is not acceptable by the regex!");
        }
        in.close();
    }

    private static String getRegex(Scanner in) {
        System.out.print("Enter a RE: ");
        String regex = in.nextLine();
        System.out.println("Augemnted RE :"+ regex+"#");
        return regex+"#";
    }

    private static void getSymbols(String regex) {
       
        Set<Character> op = new HashSet<>();
        Character[] ch = {'(', ')', '*', '|', '&', '.', '\\', '[', ']', '+'};
        op.addAll(Arrays.asList(ch));

        input = new HashSet<>();
        symbNum = new HashMap<>();
        int num = 1;
        for (int i = 0; i < regex.length(); i++) {
            char charAt = regex.charAt(i);
          
            if (op.contains(charAt)) {
                if (i - 1 >= 0 && regex.charAt(i - 1) == '\\') {
                    input.add("\\" + charAt);
                    symbNum.put(num++, "\\" + charAt);
                }
            } else {
                input.add("" + charAt);
                symbNum.put(num++, "" + charAt);
            }
        }
    }

    private static State createDFA() {
        int id = 0;
        Set<Integer> firstpos_n0 = root.getFirstPos();

        State q0 = new State(id++);
        q0.addAllToName(firstpos_n0);
        if (q0.getName().contains(followPos.length)) {
            q0.setAccept();
        }
        DStates.clear();
        DStates.add(q0);

        while (true) {
            boolean exit = true;
            State s = null;
            for (State state : DStates) {
                if (!state.getIsMarked()) {
                    exit = false;
                    s = state;
                }
            }
            if (exit) {
                break;
            }

            if (s.getIsMarked()) {
                continue;
            }
            s.setIsMarked(true); //mark the state
            Set<Integer> name = s.getName();
            for (String a : input) {
                Set<Integer> U = new HashSet<>();
                for (int p : name) {
                    if (symbNum.get(p).equals(a)) {
                        U.addAll(followPos[p - 1]);
                    }
                }
                boolean flag = false;
                State tmp = null;
                for (State state : DStates) {
                    if (state.getName().equals(U)) {
                        tmp = state;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    State q = new State(id++);
                    q.addAllToName(U);
                    if (U.contains(followPos.length)) {
                        q.setAccept();
                    }
                    DStates.add(q);
                    tmp = q;
                }
                s.addMove(a, tmp);
            }
        }
        getFollowFirstLastPos();
        printDFA(DStates);
        trans(DStates);
        return q0;
    }

    private static String getStr(Scanner in) {
        System.out.println();
        System.out.println("-----Testing-----\n");
        System.out.print("Enter a string: ");
        String str;
        str = in.nextLine();
        return str;
    }
    private static void printDFA(Set<State> DStates){
        String [] s=new String[]{"","A","B","C","D","E","F","G","H"};
        System.out.println("-----DFA states-----\n");
        int i=0;
        for (State state : DStates) {
            System.out.println(s[i]+""+state.getName());
            i++;
        }    
        System.out.println();

    }
    private static void trans(Set<State> DStates){
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("-----Transition-----\n");
        System.out.print("Enter an input for transition: ");
        String inpt = in.nextLine();
        for (State state : DStates){
            System.out.println(state.getNextStateBySymbol(inpt).getName());
        }    
    }
    private static void getFollowFirstLastPos(){
        int count=1;
        System.out.println();
        System.out.println("-----postion table-----\n");
        for(Set<Integer> i:followPos){
            System.out.println("  Pos"+count+" | "+i);
            System.out.println("  -----|-----------");
            count++;
        }
        System.out.println();

    }

}