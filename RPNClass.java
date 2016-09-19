package week09;
import java.util.*;

/** This is the support class for RPNApp.
 * Within this class are all the methods neccessary to evaluate the
 given expression if the expression is viable. if not, an error will
 * be returned describing issue. This class has methods to deal with the
 *4 conventional mathmatical operands. As well as other special operators.
 * @author Cassie Stylianou and Bayley Millar.
 */


public class RPNClass{
    /**used to create a int array - final value can be changed in push method.
     * if array is too small to accomodate everything.
     */
    public static final int dEFAULT_SIZE = 10;
    /**creates an array call stack using the final variable declared above. */
    private int[] stack = new int[dEFAULT_SIZE];
    /**A variable which is used throughout class to act as an index. */
    private int stackPoint = 0;
    /**is used as the value which is pushed onto the stack after a calculation. */
    private int answer = 0;
    /**acts as a storage variable for when a calculation requires one.*/
    private int num1 = 0;
    /**used to hold the places of the values that comme out of pop(). */
    private int num2 = 0;
    /**variable will be changed if a o operator is used.*/
    private String peekVar = "";

    
    /**This method takes in the String input and then splits it into an String[].
     *@param input is the input expression that is recieved in RPNApp.
     */
    public void splitExpression(String input){
        String toCalc[]=input.split(" ");
        String array[];
        for(int index = 0; index < toCalc.length; index++){
            if(toCalc[index].equals("(")){
                int num1 = pop();
                int num2 = index + 1;
                for(int i = 0; i < num1; i++){
                    index = num2;
                    while(!toCalc[index].equals(")")){
                        if(index == toCalc.length-1){
                            throw new RuntimeException("Error: unmatched parentheses");
                        }
                        calculations(toCalc[index]);
                        index++;
                    }
                }
            }else{
                calculations(toCalc[index]);
            }
        }
    }

    /**This method is used to push/add values onto the top of the array stack.
     *@param number is the value getting pushed into the stack.
     */
    public void push(int number){
        if(stackPoint >= stack.length){
            stack = java.util.Arrays.copyOf(stack, 2*stack.length);
        }
        stack[stackPoint]=number;
        stackPoint++;
    }
  
    /**method used to see what value is on top of the stack.
     * @return returns the value on top of the stack.
     */
    public int peek(){
        answer = stack[stackPoint-1];
        return answer;
        
    }

    /**method is used to check if stack is empty.
     * @return returns if the stack is empty.
     */
    public boolean isEmpty(){
        return stackPoint <= 0;
    }
        
    /**method is used to pull the top value off the stack.
     * @return returns the value on top of the stack.
     */
    public int pop(){
        stackPoint--;
        if(stackPoint < 0){
            throw new RuntimeException("Error: too few operands");
        }
        return stack[stackPoint];
    }




    /**method is used to determine the operators from the integers
     * and if it is a operator to then complete the operation.
     * @param value which the value to be determined.
     */
    public void calculations(String value){
       
        switch(value){

        case "+":
            answer = pop()+pop();
            push(answer);
            break;
        case "-":
            answer = -pop() + pop();
            push(answer);
            break;
        case "*":
            answer = pop()*pop();
            push(answer);
            break;
        case "/":
            doDivision();
            break;
        case "%":
            answer = pop()%pop();
            push(answer);
            break;
                
        case "+!":
            if(isEmpty()){
                throw new RuntimeException("Error: too few operands");
               
            } else{
                    
                while(stackPoint>0){
                    answer += pop();
                } push(answer);
            }
            break;
        case "-!":
            if(isEmpty()){
                throw new RuntimeException("Error: too few operands");
               
            } else{
                while(stackPoint>0){
                    answer -= pop();
                } push(answer);
            }
            break;
        case "*!":
            if(isEmpty()){
                throw new RuntimeException("Error: too few operands");
                
            } else{
                answer = pop();
                while(stackPoint>0){
                    answer *= pop();
                } push(answer);
            }
            break;
        case "/!":
            if(isEmpty()){
                throw new RuntimeException("Error: too few operands");
            } else{
                answer = pop();
                while(stackPoint>0){
                    answer /= pop();
                } push(answer);
            }
            break;
        case "%!":
            doRemainder();
            break;
        case "o":
            if(stackPoint <= 0){
                throw new RuntimeException("Error: too few operands");
            }
            peekVar += peek() + " ";
            break;
        case "d":
            toDuplicate();
            break;
        case "c":
            doCopy();
            break;
        case "r":
            doRoll();
            break;
        case ")":
            throw new RuntimeException("Error: unmatched parentheses");
        default:
            try{
                push(Integer.parseInt(value));
            }catch(NumberFormatException e){
                throw new RuntimeException("Error: bad token " + "'"+ value+"'");
            }break;
        }
        
    }

    

    
    /**method used to complete division and is called in calculation.*/         
    public void doDivision(){
        num1 = pop();
        num2 = pop();
        if(num2<=0 || num1<=0){
            throw new RuntimeException("Error: division by 0");
        }else{
            answer = num1/num2;
            push(answer);
        }  
    }
    /**method used to complete remainder and is called in calculation.*/
    public void doRemainder(){
        num1 = pop();
        num2= pop();
        answer = num1%num2;
        push(answer);
    }
    /**method used to complete operator 'c'.Copies the top number of the stack and
     * pushes the second number onto the stack teh same number of times the top number
     *holds.
     */
    public void doCopy(){
        if(stack.length <2){
            throw new RuntimeException("Error: too few operands");
        }
        int copynumber = pop();
        int number = pop();
        if(copynumber<0){
            throw new RuntimeException("Error: negative copy");
        } else {

            while(copynumber!= 0){
                push(number);
                copynumber--;
            }
        }
    }
    /**method used to complete operator 'd'.
     * Repeats the top number from the stack. 
     */
    public void toDuplicate(){
        answer = pop();
        if(isEmpty()){
            throw new RuntimeException("Error: too few operands.");
        }else{
        push(answer);
        push(answer);
        }
    }
    /**method used to complete operator 'r'. Takes the topnumber from the stack
     *and moves the second number down the stack topnumber-1 positions.
     */
    public void doRoll(){
        int topnumber;

        if(stackPoint <= 0){
            throw new RuntimeException("Error: too few operands");
        }else if(stack.length < 2){
            throw new RuntimeException("Error: negatve copy");
        }else{

            int rollnumber = stack[stackPoint-2];
            topnumber=pop();
            
            if(topnumber<0){
                throw new RuntimeException("Error: negatve copy");
            }
            
            int toHold;
            for(int index = stackPoint-topnumber; index<stackPoint; index++){
                toHold = stack[index];
                stack[index]=rollnumber;
                rollnumber = toHold;
            }
        }
    }
    /** method that cleans the stackPoint and makes it equal to zero.
     * Important for dealing with multiple lines. 
     */
    public void cleanUp(){
        stackPoint = 0;
    }
            
 
   
    /**method creates a result array to then be processed into
       the final result we want.The result is printed on the screen using System.out
    */
    public void finalResult(){
        int [] result = new int[stackPoint];
        for(int index = 0; index<stackPoint;index++){
            result[index] = stack[index];
        }
        System.out.println(peekVar + Arrays.toString(result));
        stackPoint = 0;    
    }

}  //end class



