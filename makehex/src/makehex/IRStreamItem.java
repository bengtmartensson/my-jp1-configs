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

/**
 * This class ...
 *
 * @author Bengt Martensson
 */
public abstract class IRStreamItem {
    
    //abstract boolean stringOk(String s);
    
    protected Protocol environment;
    
    public IRStreamItem(Protocol env) {
        environment = env;
    }
    
    public IRStreamItem() {
    //    environment = new Protocol();
    }
    
    //public static void x() {
    //    System.out.println("dsfkdsf");
    //}
    
    public static IRStreamItem irStreamItemFactory(String s) {
        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }
}
