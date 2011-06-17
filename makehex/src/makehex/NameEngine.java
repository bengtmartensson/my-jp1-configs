package makehex;

import java.util.HashMap;

/**
 * Implements Chapter 13. 
 * @author bengt
 */
public class NameEngine {

    private HashMap<Character, Integer>map;
    
    @Override
    public String toString() {
        return map.toString();
    }
    
    public NameEngine() {
        map = new HashMap<Character, Integer>();
    }
    
    public void assign(char name, int value) {
        map.put(Character.toLowerCase(name), value);
    }
    
    public void assign(String str) {
        String[] s = str.split("=");
        assign(s[0].trim().charAt(0), Integer.parseInt(s[1].trim()));
    }
    
    public double evaluate(char c) throws IllegalArgumentException {
        if (map.containsKey(Character.toLowerCase(c)))
            return map.get(Character.toLowerCase(c));
        else
            throw new IllegalArgumentException("No name `" + c + "' defined.");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NameEngine ne = new NameEngine();
        ne.assign("X  =   42");
        System.out.println(ne);
        System.out.println(ne.evaluate('x'));
        ne.assign("x  =   4242");
        System.out.println(ne.evaluate('x'));
        ne.assign('x', 424);
        System.out.println(ne.evaluate('x'));
        try {
            System.out.println(ne.evaluate('z'));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
