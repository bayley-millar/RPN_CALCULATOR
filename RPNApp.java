package week09;
import java.util.*;


/** This is the application class for the RPNAClass.
 * Contains the main method which scans in inupt from keyboard.
 * This app class also applies methods from within the RPNAClass which
 * applies calculations. 
 * @author Cassie Stylianou and Bayley Millar.
 */

public class RPNApp{
    /** Takes the inputed data (System.in) and uses methods from the
     *RPNClass to calculate the given expression.
     * @param args command line is not used.
     */
    public static void main (String[] args){
        String expression;
        RPNClass calc = new RPNClass();
        Scanner scan = new Scanner(System.in);

        while(scan.hasNextLine()){
            try{
                expression = scan.nextLine();
                calc.splitExpression(expression);
                calc.finalResult();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                calc.cleanUp();
            }
        }
    }
}

  
