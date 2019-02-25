class StackTest
{

   public static void main( String[] args ){
      StackBase stack = new StackBase();
      for(int i=0; i<10; i++){
         stack.push(i);
      }   
      System.out.println("Stack contents:");
      for(int i=0; i<10; i++) {
         System.out.println(stack.pop());
      }
      
      variableIntStack varStack = new variableIntStack();
      int[] testValue = {9, 1, 1};
      int[] longTestValue = {5, 4, 3, 2, 1};
      varStack.push(testValue);
      varStack.push(longTestValue);
      int[] returnValue = varStack.pop();
      for(int item : returnValue){
         System.out.println("Value: " + item);
      }      
      System.out.println("Again!");
      for(int item: varStack.pop()){
         System.out.println("More value: " + item);
      }
   }

}