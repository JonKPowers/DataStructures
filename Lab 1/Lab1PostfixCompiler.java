class Lab1PostfixCompiler 
{

   public static void main ( String[] args ) throws EmptyStackException
   {
      CharStack stack = new CharStack();

      String inputFile;
      String[] fileLines;
      
      // One input file must be provided as the program's single argument.
      if (args.length != 1){
         System.out.println("Incorrect number of arguments (" + args.length + 
                            ") provided. Usage: Lab1PostfixCompiler <input_file.txt>.");
      }
      inputFile = args[0];
      
      // Get an array containing each line of the inputFile. If getLinesFromFile() returns a
      // zero-length array, either the file was empty or there was some sort of issue opening
      // or reading the file. In either case, we don't have any usable data from the inputFile,
      // so print a message and end the program.
      fileLines = InputFileHandler.getLinesFromFile(inputFile);
      if(fileLines.length == 0){
         System.out.println("Unable to get data from " + inputFile + ". Please check input file and try again.");
         System.exit(0);
      }
      
      System.out.println(fileLines[1]);
      
      String converted = convertPostfix("ABC+++");
      System.out.println(converted);

   }

   
   public static String convertPostfix (String input) {
      /**
      ** convertPostfix() is the workhorse method that converts the postfix string
      ** into machine instructions. It utilizes several stacks to track its position in
      ** the postfix string and the operands to be used. The stacks also serve as an 
      ** error-checking mechanism to verify that there are not too many (or too few) 
      ** operands or operators. A helper class, VarGenerator, provides temporary variable
      ** names and stores an operation history.
      **
      ** @param input The postfix expression to be converted to machine instructions.
      ** @return String "-1" if an error is encountered; otherwise the machine instructions, one per line, derived from the input postfix expression.
      */
      
      String a, b;
      String register = null;
      VarGenerator tempVars = new VarGenerator();
      StringStack operandStack = new StringStack();
      StringStack inputStack = new StringStack(input, "reverse");
      String outputString = "";
      
      while(!inputStack.isEmpty()){
         String item = "";
         try {
            item = inputStack.pop();
         } catch (EmptyStackException except) {
            System.out.println("This should never happen, but the compiler made me put this in.");
         }
         // If the next item is an operand, add it to the operand stack.
         if(!isOperator(item)){
            operandStack.push(item);
         // If it's an operator, try to get two operands and perform the operation
         } else {
            try {
               String loadInstruction, operationInstruction = null;
               
               // We need two operands to perform an operation
               b = operandStack.pop();
               a = operandStack.pop();
               
               // If the contents of the register don't match a, put a into the register.
               // THIS MIGHT BE UNNECESSARY--MAYBE JUST ALWAYS PUT IT IN THE REGISTER?
               if(register == null || !register.equals(a)){
                  register = a;
                  loadInstruction = "LD\t" + a + "\n";
                  outputString += loadInstruction;
               } else {
                  loadInstruction = "";
               }
               
               // Get the operation instruction and perform the operation
               // NEED SPECIAL HANDLING FOR EXPONENTS;
               String instruction = getInstruction(item);
               operationInstruction = instruction + "\t" + b +"\n";
               outputString += operationInstruction;
               
               // Store the operation results and push back onto the stack
               String varName = tempVars.getNewVar(loadInstruction, operationInstruction);
               outputString += "ST\t" + varName + "\n";
               operandStack.push(varName);            
            } catch(EmptyStackException except) {
               System.out.println("The input string contained too few operands for the expression");
               // NEED SOME SORT OF MESSAGE TO BE WRITTEN TO THE OUTPUT FILE.
               // ERROR HANDLING FOR NOT ENOUGH OPERANDS!            
            }
         }
      }
      
      if(inputStack.getLength() != 1){
         // There should be exactly one item left on the operand stack, which is the final result.
         System.out.println("There were too many operatands in this expression.");
         //ERROR HANDLING FOR TOO MANY OPERANDS!
      }
      return outputString; 
   }
   
   public static String getInstruction(String operator){
      String instruction;
      switch(operator){
         case "+":   instruction = "AD";
                     break;
         case "-":   instruction = "SB";
                     break;
         case "*":   instruction = "ML";
                     break;
         case "/":   instruction = "DV";
                     break;
         default:    instruction = "ERROR!";
      }
      return instruction;    
   }
   
   public static boolean isOperator(String item){
      return item.equals("+") || item.equals("-") || item.equals("*") || item.equals("/") || item.equals("$");
   }
}