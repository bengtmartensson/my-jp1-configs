package makehex;

/**
 * This class implements GeneralSpec as given in Chapter 2 of Dixon: "Specification of IRP Notation", second draft.
 * 
 * This class is immutable; can only be defined by the constructor.
 * 
 * 
 * @author bengt
 */
public class GeneralSpec {
    
    /** Carrier frequency in Hz */
    private double frequency = 0;
    
    /** BitDirection */
    private BitDirection bitDirection = BitDirection.lsb;
    
    /** Timing unit in us */
    private double unit = 1;
    
    /** Timing unit in pulses, if and only if given by the user.*/
    private double unit_pulses = -1;

    @Override
    public String toString() {
        return "Frequency = " + frequency + "Hz, unit = " + unit + "us, " + bitDirection; 
    }
    
    GeneralSpec() {    
    }
    
    GeneralSpec(String str) {
        String[] s = str.replaceAll("[\\]\\[]", "").trim().toLowerCase().split(",");
        for (int i = 0; i < s.length; i++)
            parse(s[i]);
        
        update_unit();
    }
    
    private void update_unit() {
        if (unit_pulses != -1) {
            if (frequency == 0)
                throw new ArithmeticException("Units in p and frequency == 0 do not go together.");
            unit = (int) (((double)unit_pulses)*(1000000.0/frequency));
        }
    }
    
    private void parse(String str) {
        try {
            str = str.trim();
            if (str.isEmpty())
                return;
            if (str.contains("msb"))
                bitDirection = BitDirection.msb;
            else if (str.contains("lsb"))
                bitDirection = BitDirection.lsb;
            else if (str.endsWith("k")) {
                frequency = Double.parseDouble(str.replaceAll("k", "")) * 1000;
                update_unit();
            } else if (str.endsWith("p")) {
                unit_pulses = Integer.parseInt(str.replaceAll("p", ""));
                //update_unit();
            } else if (str.endsWith("u"))
                unit = Integer.parseInt(str.replaceAll("u", ""));
            else
                unit = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    BitDirection getBitDirection() {
        return bitDirection;
    }
    
    public double getFrequency() {
        return frequency;
    }
    
    public double getUnit() {
        return unit;
    }
    
    //public void setBitDirection(BitDirection bd) {
    //    bitDirection = bd;
    //}
    
    //public void setUnit(int unit) {
    //    this.unit = unit;
    //} 
    
    //public void setFrequency(double f) {
    //    frequency = f;
    //}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-e"))
            new GeneralSpec("[0k,,10p]"); // Thows error
        else {
            GeneralSpec gs = new GeneralSpec("[]");
            System.out.println(gs);
            gs = new GeneralSpec("[38.4k,564]");
            System.out.println(gs);
            gs = new GeneralSpec("[564,38.4k]");
            System.out.println(gs);
            gs = new GeneralSpec("[msb, 889u]");
            System.out.println(gs);
            gs = new GeneralSpec("[10p,msb,40k]");
            System.out.println(gs);
            gs = new GeneralSpec("[msb ,40k , 10p ]");
            System.out.println(gs);
            System.out.println(new GeneralSpec("[msb, 123u, 100k, 10p, 1000k]"));
        }
    }
}
