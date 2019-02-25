class StackBase 
{
   final private int MAXSIZE = 100;
   private int[]stackArray;
   private int top = -1;
   
   StackBase(){
      stackArray = new int[MAXSIZE];
   }
      
   public void push(int newValue) {
      stackArray[++top] = newValue;
   }
   
   public int pop(){
      return stackArray[top--];
   }
   
   public boolean isEmpty(){
      return top == -1;
   }
   
   public int sizeOf(){
      return top + 1;
   }
      
}