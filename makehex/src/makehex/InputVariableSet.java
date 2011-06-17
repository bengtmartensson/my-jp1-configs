/*
Copyright (C) 2011 Bengt Martensson.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License along with
this program. If not, see http://www.gnu.org/licenses/.
 */
package makehex;

import java.util.HashMap;
import java.util.Set;

/**
 * This class ...
 *
 * @author Bengt Martensson
 */
public class InputVariableSet {
    
    private HashMap<Character, Integer>map;
    
    public InputVariableSet() {
        map = new HashMap<Character, Integer>();
    }
    
    public InputVariableSet(int function) {
        this();
        assign('f', function);
    }

    public InputVariableSet(int device, int function) {
        this();
        assign('d', device);
        assign('f', function);
    }
    
    public InputVariableSet(int device, int subdevice, int function) {
        this(device, function);
        assign('s', subdevice);
    }
    
    public InputVariableSet(String str) {
        this();
        String[] s = str.split(",");
        for (int i = 0; i < s.length; i++) {
            String[] q = s[i].split("=");
            assign(q[0].trim().charAt(0), Integer.parseInt(q[1].trim()));
        }
    }
    
    public Set<Character> getKeys() {
        return this.map.keySet();
    }
    
    public final void assign(char name, int value) {
        map.put(Character.toLowerCase(name), value);
    }
    
    public Integer getVariable(char name) {
        return map.get(Character.toLowerCase(name));
    }
    
    @Override
    public String toString() {
        return map.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(new InputVariableSet(12,34));
        System.out.println(new InputVariableSet(12,34,56));
        InputVariableSet ivs = new InputVariableSet(" a = 24 , b=66 ");
        System.out.println(ivs);
        ivs.assign('Z', 4242);
        System.out.println(ivs);
    }
}
