class PostfixConverter
{
   private boolean optimize;
   private StringStack machineInstructions = new StringStack();
   private String register = null;
   private VarGenerator varGen = new VarGenerator();
   
   // Constructors. By default, optimizations are disabled. They can be enabled by passing true as an argument
   // to the constructor or by passing the String "optimize" to the constructor.
   PostfixConverter(){
      this.optimize = false;
   }
   
   PostfixConverter(boolean optimize){
      this.optimize = optimize;
   }
   
   PostfixConverter(String optimize){
      this.optimize = optimize.equals("optimize");
   }
   
   // Public methods
   public String getInstructionString(){
     /**
     ** getInstructions() returns the machine instructions generated as of 
     ** the time of the call.
     **
     ** @return String The machine instructions produced by the PostfixConverter.
     **/
     
      String InstructionString = "";
      StringStack reversingStack = new StringStack(machineInstructions.getLength());
      
      // Reverse the machineInstruction stack to get the instructions in the right
      // order, then concatenate them into a string to return.
      try {
         while(!machineInstructions.isEmpty()){
            reversingStack.push(machineInstructions.pop());
         }      
         while(!reversingStack.isEmpty()){
            InstructionString += reversingStack.pop();
         }
      } catch (EmptyStackException except) {
         System.out.println("This should never happen, but the compiler made me put it in." + except);
      }
     return InstructionString;
   }
   
   public String processOperation(String a, String b, String instruction){
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
         throw new ArithmeticException("Division by zero");
      }
      
      // Apply the appropriate operations based on whether optimizations is enabled
      if(!this.optimize){
         simpleOperation(a, b, instruction);
      } else {
         if(a.equals("0") || b.equals("0")){
            zeroOperation(a, b, instruction);
         } else {
            simpleOperation(a, b, instruction);
         }
         // TO DO: One operations and commutative operations.
         
      }
      return storeRegister();
   }  
    
   public void simpleOperation(String a, String b, String instruction){
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

      this.machineInstructions.push("LD\t" + a + "\n");
      this.register = a;
      this.machineInstructions.push(getInstructionCode(instruction) + "\t" + b + "\n");
   }
   
   public void zeroOperation(String a, String b, String instruction){
      /**
      ** zeroOperation() provides some optimized instructions for operations that have
      ** the value zero (as a String) as one of its operands. The instructions generated
      ** are appended to the PostfixConverter's private machineInstructions instance variable.
      **
      ** @return None Nothing is returned.
      **/
      switch(instruction){
         case "+":
            if(a.equals("0")){
               //put b directly into register; this covers 0 + 0 as well.
               this.machineInstructions.push("LD\t" + b + "\n");
               this.register = b;
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
            this.machineInstructions.push("LD\t0\n");
            this.register = "0";
            break;
         case "/":
            if(b.equals("0")){
               // Division by zero error should throw an error. This has to be caught by
               // the caller and the output string should reflect the exception.
               throw new ArithmeticException("Division by zero");               
            } else if(a.equals("0")){
               // Zero dived by any value (other than zero) will be zero.
               this.machineInstructions.push("LD\t0\n"); 
               this.register = "0";
            }
            break;            
      }
   }
   
   private String storeRegister(){
      String tempVar = varGen.getNewVar();
      this.machineInstructions.push("ST\t" + tempVar + "\n");
      return tempVar;
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
         this.machineInstructions.push("LD\t" + value + "\n");
         this.register = value;
      } else if (!this.register.equals(value)) {
         this.machineInstructions.push("LD\t" + value + "\n");
      } 
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

}