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

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * This class ...
 *
 * @author Bengt Martensson
 */
public class Protocol {

    private String name;
    
    private GeneralSpec generalSpec;
    
    private NameEngine nameEngine;
    
    private DefinitionEngine definitionEngine;
    
    private ArrayDeque<BitSpec> bitSpecStack;
    
    private ArrayDeque<IRStream> irStreamStack;
    
    private HashMap<Character, InputVariable>inputvars;
    
    public GeneralSpec getGeneralSpec() {
        return generalSpec;
    }
    
    // FIXME
    private String form;
    
    // FIXME
    public double timeElapsed() {
        return 123;
    }
    
    // Essentially for testing
    public Protocol() {
        this("unnamed",
                "{38.4k,564}<1,-1|1,-3>(16,-8,D:8,S:8,F:8,~F:8,1,-78,(16,-4,1,-173)*){Z=blOrk, W=bliScher}",
                "d=0..255,s=0..255:255-D,f=0..255");
    }
    
    public Protocol(String name, String definition, String variables) {
        this.name = name;
        this.form = definition;
        String[] tmp = definition.split("}");
        generalSpec = new GeneralSpec(tmp[0].substring(1));
        String str = tmp[1];
        tmp = str.split(">");
        String bitspecstr = tmp[0].substring(1);
        bitSpecStack = new ArrayDeque<BitSpec>();
        BitSpec bs = new BitSpec(bitspecstr);
        bitSpecStack.push(bs);
        tmp = tmp[1].split("\\{");
        String irstr = tmp[0];
        IRStream irStream = new IRStream(irstr);
        irStreamStack = new ArrayDeque<IRStream>();
        irStreamStack.push(irStream);
        definitionEngine = new DefinitionEngine(tmp[1]);
        inputvars = new HashMap<Character, InputVariable>();
        String[] vardef = variables.split(",");
        for (int i = 0; i < vardef.length; i++) {
            String[] v = vardef[i].split("=");
            char varname = v[0].trim().charAt(0);
            String[] q = v[1].split(":");
            String defa = q.length > 1 ? q[1].trim() : null;
            String[] minmax = q[0].split("\\.\\.");
            int min = Integer.parseInt(minmax[0].trim());
            int max = Integer.parseInt(minmax[1].trim());
            InputVariable var = new InputVariable(varname, min, max, defa);
            inputvars.put(varname, var);
        }
        this.nameEngine = new NameEngine();
        
    }
    
    @Override
    public String toString() {
        return name + ": "// + form 
                + generalSpec + bitSpecStack.peek() + this.irStreamStack.peek() + definitionEngine + inputvars.toString();
    }
    
    /*private class InputVariablesEngine {
        private HashMap<Character, Integer>map = new HashMap<Character, Integer>();
        
        public void assign(char name, int value) {
            map.put(name, value);
        }
    }*/
    
    public boolean compute(InputVariableSet actualVars) {
        // TODO: implement defaults
        System.out.println(actualVars);
        for (Character ch : inputvars.keySet()) {
            if (actualVars.getVariable(ch) == null) {
                System.err.println("Input variable " + ch + " not assigned");
                return false;
            }
            if (!inputvars.get(ch).isOK(actualVars.getVariable(ch))) {
                System.err.println(ch + " has invalid value (" + actualVars.getVariable(ch) + ")");
                return false;
            }
        }
        
        for (Character ch : actualVars.getKeys()) {
            nameEngine.assign(ch, actualVars.getVariable(ch));
            if (!inputvars.containsKey(ch))
                System.out.println("Warning: Variable `" + ch + "' appearently useless.");
        }
        System.out.println(nameEngine);
        return true;
    }
    
    public boolean compute(int function) {
        InputVariableSet ivs = new InputVariableSet(function);
        return compute(ivs);
    }
    
    public boolean compute(int device, int function) {
        InputVariableSet ivs = new InputVariableSet(device, function);
        return compute(ivs);
    }

    public boolean compute(int device, int subdevice, int function) {
        InputVariableSet ivs = new InputVariableSet(device, subdevice, function);
        return compute(ivs);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Protocol nec1 = new Protocol("nec1",
                "{38.4k,564}<1,-1|1,-3>(16,-8,D:8,S:8,F:8,~F:8,1,-78,(16,-4,1,-173)*){Z=blOrk, W=bliScher}",
                "d=0..255,s=0..255:255-D,f=0..255");
        System.out.println(nec1);
        nec1.compute(12);
        nec1.compute(12,34);
        nec1.compute(12,34,56);
        nec1.compute(12,34,568);
        nec1.compute(1299,340,-568);
        InputVariableSet ivs = new InputVariableSet(12,34,45);
        ivs.assign('Z', 42);
        nec1.compute(ivs);
    }
}
