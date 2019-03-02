class Lab1PostfixCompiler 
{

   public static void main ( String[] args ) throws EmptyStackException
   {
      /**
      ** main() is the primary method that implements the Postfix Compiler for Lab 1 in the
      ** JHU Data Structures course. Usage is 'java Lab1PostfixCompiler inputfile.txt outputfile.txt [optimize]'
      ** If "optimize" is provided as a third argument when running the program, the compiler will
      ** implement some optimizations to reduce the number of machine instructions required to evaluate
      ** the input expression. Input handling is provided by the InputFileHandler class, and the compilation
      ** of expressions into machine instructions is provided by the Compiler class. Processing of
      ** the postfix string is handled by the convertPostfix() method within this class. Output is sent to a
      ** file specified on the command line, which will be overwritten if it already exists.
      **
      ** @param String1 The input file containing postfix strings to compile, one per line.
      ** @param String2 The output file that the compiled instructions will be written to along with other meta data.
      ** @param String3 Optional. The word "optimize" (without quotes) to enable optimizations in the instruction compiler.
      **
      ** @return None Nothing is returned.
      **
      **/

      String inputFile, outputFile;
      boolean optimize = false;     // Complier optimization is disabled by default.
      StringStack[] fileLines;
      
      // Parse arguments passed from the command line.
      // There must be at least two (input and output files)
      if (args.length < 2){
         System.out.println("Incorrect number of arguments (" + args.length + 
                            ") provided. Usage: Lab1PostfixCompiler <input_file.txt> <output_file.txt> [optimize]");
         return;
      }
      
      inputFile = args[0];
      outputFile = args[1];
      if(args.length == 3) {     // Enable optimization is selected by user
         optimize = args[2].toLowerCase().equals("optimize");
      }
      
      // Get an array containing each line of the inputFile. If getLinesFromFile() returns a
      // zero-length array, either the file was empty or there was some sort of issue opening
      // or reading the file. In either case, we don't have any usable data from the inputFile,
      // so print a message and end the program.
      fileLines = InputFileHandler.getLinesFromFileAsStacks(inputFile);
      if(fileLines.length == 0){
         System.out.println("Unable to get data from " + inputFile + ". Please check input file and try again.");
         System.exit(0);
      }
      
      String testString = "CB+AB+0$+";
      StringStack testStack = new StringStack(testString, true);
      
      System.out.println("Input expression: " + getInputString(testStack.copy()));
      System.out.println("Optimizations: " + (optimize ? "Enabled" : "Disabled"));      
      
      String converted = convertPostfix(testStack, optimize);
      System.out.println("Compiled instructions:\n" + converted);

   }

   
   public static String convertPostfix (StringStack inputStack, boolean optimize) {
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
      String item = null;
      Compiler compiler = new Compiler(optimize);
      StringStack operandStack = new StringStack();
      
      while(!inputStack.isEmpty()){
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
               // We need two operands to perform an operation
               b = operandStack.pop();
               a = operandStack.pop();

               operandStack.push(compiler.processOperation(a, b, item));
                       
            } catch(EmptyStackException except) {
               System.out.println("The input string contained too few operands for the expression");
               return "Expression exception: The input contained too few operands for the expression";           
            }
         }
      }
      
      if(operandStack.getLength() > 1){
         // There should be exactly one item left on the operand stack, which is the final result.
         System.out.println("There were too many operands in this expression.");
         return "Expression exception: The input contained too many operands for the expression.";
         
      }

      return compiler.getInstructionString();
   } 
   
   public static boolean isOperator(String item){
      return item.equals("+") || item.equals("-") || item.equals("*") || item.equals("/") || item.equals("$");
   }
   
   public static String getInputString(StringStack inputStack){
      String inputString = "";
      while(!inputStack.isEmpty()){
         try {
            inputString += inputStack.pop();
         } catch (EmptyStackException except) {
            System.out.println("This should never happen, but the compiler made me put this in.");
         }
      }
      return inputString;
   }

}