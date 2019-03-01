class PostfixConverter
{
   private boolean optimize;
   private StringStack machineInstructions = new StringStack();
   private String register = null;
   private VarGenerator varGen = new VarGenerator();
   
   // Constructors
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
   
   public void processOperation(String a, String b, String instruction){
      if(!this.optimize){simpleOperation(a, b, instruction);}
      else {
         if(a.equals("0") || b.equals("0")){
            zeroOperation(a, b, instruction);
         } else {
            simpleOperation(a, b, instruction);
         }
         // TO DO: One operations and commutative operations.
         
      }
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

      if(register != null){
         this.storeRegister();
      }
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
            } else if(b.equals("0")){
               // a stays or goes into register
               if(this.register == null){
                  this.machineInstructions.push("LD\t" + a + "\n");
               }               
            }
            break;
         case "-":
            if(a.equals("0")){ // Proceed as normal
               this.simpleOperation(a, b, instruction);
            } else if(b.equals("0")){
               // a stays in register, so do nothing and continue
            }
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
      this.machineInstructions.push("ST\t" + tempVar);
      return tempVar;
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