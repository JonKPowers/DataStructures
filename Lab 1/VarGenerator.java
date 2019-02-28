import java.util.HashMap;

class VarGenerator
{
   private int varNumber;
   private HashMap<Integer, String> history = new HashMap<Integer, String>();
   
   VarGenerator(){
      varNumber = 1;
   }
   
   public String getNewVar(String loadInstruction, String operationInstruction){
      history.put(varNumber, loadInstruction + operationInstruction);
      return "TEMP" + varNumber++;
   }
}