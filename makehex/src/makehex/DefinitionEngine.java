package makehex;

import java.util.HashMap;

/**
 * Implementation of Definitions in Chapter 10. Immutable.
 * Keys are mapped to lower case, the values keep their case.
 * 
 * @author bengt
 */
public class DefinitionEngine {
    
    private HashMap<Character, String> map;
    
    private DefinitionEngine() {
        map = new HashMap<Character, String>();
    }   
        
    public DefinitionEngine(String str) {
        this();
        str = str.trim();
        str = str.replaceAll("[{}]", "");
        
        String s[] = str.split(",");
        for (int i = 0; i < s.length; i++) {
            String[] kv = s[i].trim().split("=");
            map.put(kv[0].toLowerCase().charAt(0), kv[1].trim());
        }
    }
    
    private void define(char name, String value) {
        map.put(Character.toLowerCase(name), value.trim());
    }
    
    private void remove(char name) {
        map.remove(Character.toLowerCase(name));
    }
    
    /**
     * 
     * @param ch Input name
     * @return expansion, or null.
     */
    public String expand(char ch) {
        return map.get(Character.toLowerCase(ch));
    }
    
    /**
     * 
     * @param ch Input name
     * @return expansion, or input character (as String)
     */
    public String macroExpand(char ch) {
        return map.containsKey(Character.toLowerCase(ch)) ? expand(ch) : "" + ch;
    }
    
    @Override
    public String toString() {
        return map.toString();
    }
    
    /**
     * Just for testing purposes.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DefinitionEngine de = new DefinitionEngine("{F=Junk that will be ignored, D = Graham Dixon  , F=John Fine}");
        System.out.println(de);
        System.out.println(">"+ de.expand('D') + "<");
        System.out.println(de.expand('d'));
        System.out.println(de.expand('F'));
        System.out.println(de.expand('E'));
        de.define('E', "blah");
        System.out.println(de.expand('E'));
        de.remove('F');
        System.out.println(de.expand('F'));
        System.out.println(de.macroExpand('F'));
        de.remove('Z');
    }
}
