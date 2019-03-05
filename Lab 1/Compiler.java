class Compiler
{
   private boolean optimize;
   private StringStack machineInstructions = new StringStack();
   private StringStack registerContents = new StringStack();
   private StringStack instructionHistory = new StringStack();
   private String register = null;
   private VarGenerator varGen = new VarGenerator();
   private int lastStoragePosition, lastOperandToOperatorSwitch;
   
   // Constructors. By default, optimizations are disabled. They can be enabled by passing true as an argument
   // to the constructor or by passing the String "optimize" to the constructor.
   Compiler(){
      this.optimize = false;
   }
   
   Compiler(boolean optimize){
      this.optimize = optimize;
   }
   
   Compiler(String optimize){
      this.optimize = optimize.equals("optimize");
   }
   
   ////////////////////////////
   // Public methods
   ////////////////////////////
   
   public String getInstructionString(){
     /**
     ** getInstructions() returns the machine instructions generated as of 
     ** the time of the call.
     **
     ** @return String The machine instructions produced by the PostfixConverter.
     **/
     
      String InstructionString = "";
      StringStack tempCopy = machineInstructions.copy();
      StringStack reversingStack = new StringStack(machineInstructions.getLength());
      
      // Reverse the machineInstruction stack to get the instructions in the right
      // order, then concatenate them into a string to return.

      while(!tempCopy.isEmpty()){
         reversingStack.push(tempCopy.pop());
      }      
      while(!reversingStack.isEmpty()){
         InstructionString += reversingStack.pop();
      }
      return InstructionString;
   }
   
   public String processOperation(String a, String b, String instruction) throws EmptyStackException{
      /** processOperation() is the public interface to the operation processing methods in
      ** PostfixConverter. It chooses the appropriate methods to apply based on whether or
      ** not optimizations are enabled and returns the name of the temporary variable
      ** that the operation's result is stored in.
      **
      ** @param a A String containing the first operand.
      ** @param b A String containing the second operand.
      ** @param instruction A String containing the operator to be applied to the operands.
      **
      ** @return String The name of the temporary variable that the operation's result is stored in.
      **/
      
      // Error checking--No division by zero allowed
      if(b.equals("0") && instruction.equals("/")){
         throw new ArithmeticException("Error in expression: Attempts to divide by zero");
      }
      
      // Apply the appropriate operations based on whether optimizations is enabled
      // No optimization:
      if(!this.optimize){
         simpleOperation(a, b, instruction);
      // Optimizations:
      } else {
         if(a.equals("0") || b.equals("0")){          // Special case of operand(s) == 0
            zeroOperation(a, b, instruction);
         } else if(a.equals("1") || b.equals("1")){   // Special case of operand(s) == 1
            oneOperation(a, b, instruction);
         } else if(instruction.equals("+") || instruction.equals("*")) {   //Special case of commutative operations
            commutativeOperation(a, b, instruction);
         } else { // Nonspecial cases
            simpleOperation(a, b, instruction);
         }
      }
      return storeRegister();
   }
   
   public int getNumOfInstructions(){
      return this.machineInstructions.getLength();
   }
   
   public boolean isOptimized(){
      return this.optimize;
   }
   
   ////////////////////////////
   // Private methods
   ////////////////////////////  
    
   private void simpleOperation(String a, String b, String instruction){
      /**
      ** simpleOperation() converts a simple A-operation-B pattern to machine code.
      ** Operand a is loaded into the register, then the operation is applied to the
      ** value in the register using operand b. The resulting machine code is appended
      ** to the PostfixConverter's private machineInstructions instance variable.
      **
      ** The contents of the register are stored to a temporary variable before
      ** the new operation is performed.
      **
      ** @return None Nothing is returned.
      **/

      setRegister(a);
      if(instruction.equals("$")){
         exponentOperation(a, b, instruction);
      } else {
         addInstruction(getInstructionCode(instruction), b);
      }
   }

   private void exponentOperation(String a, String b, String instruction) throws ArithmeticException {
      if(!isDigit(b)){
         throw new ArithmeticException("Exponent power must be a digit");
      }
      
      // If the exponent component is zero, the result will be 1 for all values other than zero. 
      // As a result, we can precompute the result of exponentiation and place that value directly
      // into the register.
      //
      // 0^0 is undefined, so an ArithmeticException will be thrown if this occurs. 
      if(b.equals("0")){
         if(a.equals("0")){
            throw new ArithmeticException("Zero to the zeroth power is not defined");
         } else {
         rollbackStoreAndLoadOperation(a);
            setRegister("1");
         }
      }
      
      // For all other exponents, we will naively compute the value. The variable a will already
      // be in the register at this point (so we start with a^1 already computed).
      int exponent = getDigit(b);
      for(int i=1; i<exponent; i++){
         addInstruction("ML", a);
      }
   }

   
   private void zeroOperation(String a, String b, String instruction){
      /**
      ** zeroOperation() provides some optimized instructions for operations that have
      ** the value zero (as a String) as one of its operands. The instructions generated
      ** are appended to the PostfixConverter's private machineInstructions instance variable.
      **
      ** @param a A String containing the first operand.
      ** @param b A String containing the second operand.
      ** @param instruction A String containing the operator.
      **
      ** @return None Nothing is returned.
      **/
      switch(instruction){
         case "+":
            if(a.equals("0")){
               //put b directly into register; this covers 0 + 0 as well.
               addInstruction("LD", b);
               setRegister(b);
            } else if(b.equals("0")){
               // a stays or goes into register
               setRegister(a);
            }
            break;
         case "-":
            if(a.equals("0")){ // Proceed as normal
               this.simpleOperation(a, b, instruction);
            } else if(b.equals("0")){
               // a stays in register, so do nothing and continue
               setRegister(a);
            }
            break;
         case "*": // Anything multiplied by zero is zero, so put directly into register
            rollbackStoreOperation(a);
            setRegister("0");
            break;
         case "/":
            if(b.equals("0")){
               // Division by zero error should throw an error. This has to be caught by
               // the caller and the output string should reflect the exception.
               throw new ArithmeticException("Attempts to divide by zero");               
            } else if(a.equals("0")){
               // Zero dived by any value (other than zero) will be zero.
               setRegister("0") ;
            }
            break;
         default:
            simpleOperation(a, b, instruction);            
      }
   }
   
   private void oneOperation(String a, String b, String instruction){
      /**
      ** oneOperation() provides optimized operations for situations where the multiplier is 1
      ** and for division by one. In each case, the non-1 operand is the operation's result.
      ** As a result, we can bypass performing the operation and simply insert the proper value
      ** into the register.
      **
      ** @param a A String containing the first operand.
      ** @param b A String containing the second operand.
      ** @param instruction A String containing the operator.
      **
      ** @return None Nothing is returned.
      **/
      if(instruction.equals("*")){
         if(a.equals("1")){
            setRegister(b);
         } else {
            setRegister(a);
         }
      } else if(instruction.equals("/") && b.equals("1")){
         setRegister(a);
      } else {
         simpleOperation(a, b, instruction);
      }
   }
   
   private void commutativeOperation(String a, String b, String instruction) throws EmptyStackException{
      String lastResult = varGen.getLastVar();
      if (b.equals(lastResult)){
         varGen.cancelLastVar();
         this.machineInstructions.pop();
         addInstruction(getInstructionCode(instruction), a);
      } else {
         simpleOperation(a, b, instruction);
      }
   }
   
   private void addInstruction(String instruction, String operand){
      this.instructionHistory.push(instruction);
      this.machineInstructions.push(instruction + "\t" + operand + "\n");
   }
   
   private void rollbackStoreOperation(String OperandA){
      String lastOp = this.instructionHistory.peek();
      String lastVar = this.varGen.getLastVar();
      if(lastOp.equals("ST") && lastVar.equals(OperandA)){
         this.machineInstructions.pop();
         this.instructionHistory.pop();
         this.varGen.cancelLastVar();
      }
   }
   
   private void rollbackStoreAndLoadOperation(String OperandA){
      String lastOp1 = this.instructionHistory.pop();
      String lastOp2 = this.instructionHistory.peek();
      String lastVar = this.varGen.getLastVar();
      if(lastOp1.equals("LD") && lastOp2.equals("ST") && lastVar.equals(OperandA)){
         this.machineInstructions.pop();  // Remove LD instruction
         this.machineInstructions.pop();  // Remove ST instruction
         this.instructionHistory.pop();   // Remove ST history item (LD was removed and put in lastOp1
         this.varGen.cancelLastVar();     // Rollback the variable counter
      } else {
         this.instructionHistory.push(lastOp1); //Put back the instruction we put into lastOp1
      }
   }
   
   private String storeRegister(){
      if(this.register == "0" || this.register == "1"){
         return this.register;
      } else {
         String tempVar = varGen.getNewVar();
         addInstruction("ST", tempVar);
         this.register = tempVar;
         return tempVar;
      }
   }
   
   private void setRegister(String value){
      /** setRegister() makes sure the PostfixConverter's private register to the provided value.
      ** If the register already contains the desired value, nothing is done; otherwise a LD 
      ** instruction is added to the machineInstructions, and the internal register tracker is updated.
      ** 
      ** @param value The value to set the register to.
      ** @return None Nothing is returned
      **/
      if(this.register == null){
         addInstruction("LD", value);
      } else if (this.optimize && !this.register.equals(value)) {
         addInstruction("LD", value);
      } else if (this.optimize && this.register.equals(value) && value.equals(this.varGen.getLastVar())){
         // Don't load a value that we just stored; and if we just stored it, then it's already in the register.
         // If we're about to use that value again as the first operand, we don't need to waste a temporary
         // variable storing it.
         this.machineInstructions.pop();
         this.varGen.cancelLastVar();
      } else {
         addInstruction("LD", value);
      }
      this.register = value;
   }
   
   private String getInstructionCode(String instruction){
      String instructionCode;
      switch(instruction){
         case "+":   instructionCode = "AD";
                     break;
         case "-":   instructionCode = "SB";
                     break;
         case "*":   instructionCode = "ML";
                     break;
         case "/":   instructionCode = "DV";
                     break;
         default:    instructionCode = "ERROR!";
      }
      return instructionCode; 
   }
   
   private boolean isDigit(String num){
      return num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || 
               num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9");
   }
   
   private int getDigit(String num){
      switch(num){
         case "0": return 0;
         case "1": return 1;
         case "2": return 2;
         case "3": return 3;
         case "4": return 4;
         case "5": return 5;
         case "6": return 6;
         case "7": return 7;
         case "8": return 8;
         case "9": return 9;
         default: return -1;
      }
   }

}