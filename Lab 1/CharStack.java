class CharStack
{
   /**
   ** The CharStack class provides a stack implementation for the char primitive 
   ** datatypes, providing standard methods to manipulate the stack. The stack
   ** is implemented using an array, but the array will automatically increase
   ** its size to prevent overflows. The overflow protection cannot be overridden.
   **
   ** @author Jon Powers
   ** @version 0.1
   **/
   
   private char[] stack;
   private int top;
   private final int INITIALSIZE = 20;
   
   // Constructors
   CharStack(){
      stack = new char[INITIALSIZE];
      top = -1;
   }
   
   CharStack(int stackSize){
      stack = new char[stackSize];
      top = -1;
   }
   
   public boolean isEmpty(){
      /**
      ** isEmpty() checks whether the CharStack is empty.
      **
      ** @return bool True if the stack is empty; otherwise false.
      **
      **/
      return top == -1;
   }
   
   private boolean isFull(){
      /**
      ** isFull() checks whether the array containing the stack is full. Used as part
      ** of the internal overflow-protection implementation.
      **
      ** @return bool True if the curreny array is full; otherwise false.
      **
      **/
      return top == (stack.length - 1);
   }
   
   public int getLength() {
      /**
      ** getLength() determines the current length of the stack.
      **
      ** @return int Returns the length of the current stack.
      **
      **/
      return top + 1;
   }
   
   public void push(char newChar){
      /**
      ** push() adds an element to the top of the stack. If for any reason, the method encounters
      ** a problem assigning the new element to the correct position in the array, it will print
      ** an error message and not modify the stack array. While this stack implementation
      ** is designed to avoid overflows, and thus out of bounds exceptions, you can never
      ** be too careful.
      **s
      ** @param newChar The character that is to be added to the stack.
      ** @return None Nothing is returned.
      **
      **/
      // If the array holding the stack is full, make it longer.
      if(isFull()){
         stretchStack();
      }
      // Add the element to the stack
      try{
         stack[++top] = newChar;
      } catch(ArrayIndexOutOfBoundsException except) {
         System.out.println("Something terrible happened: " + except.getMessage());
      }
   }
   
   public char pop()throws EmptyStackError{
      /**
      ** pop() removes the top element on the stack and returns it.
      **
      ** @return char The character that was removed from the top of the stack.
      ** @throws EmptyStackError On an attempt to call pop() on an empty stack.
      **
      **/
      if (this.isEmpty()){
         System.out.println("ERROR: Cannot pop from empty stack.");
         throw new EmptyStackError();
      }
      return stack[top--];
   }
   
   public char peek()throws EmptyStackError{
      /**
      ** peek() returns the top element on the stack without removing it.
      **
      ** @return char The character that was removed from the top of the stack.
      ** @throws EmptyStackError On an attempt to call peek() on an empty stack.
      **
      **/
      if(this.isEmpty()){
         System.out.println("ERROR: Cannot peek on an empty stack.");
         throw new EmptyStackError();
      }
      return stack[top - 1];
      
   }
   
   private void stretchStack(){
      /**
      ** stretchStack() increases the size of the array that holds the stack elements 
      ** as part of the automatic overflow protection implemented here. It will
      ** double the length of the current stack array in place. This method is intended to be used
      ** before adding a new element to the stack if isFull() returns true so that the stack
      ** doesn't overflow.
      **
      ** @return None Nothing is returned.
      **/
      
      char[] newStack = new char[stack.length * 2];
      
      // Copy the contents of the full array to the new, larger array
      for(int i=0; i<stack.length; i++){
         newStack[i] = stack[i];
      } 
      stack = newStack;
   }
   
   public String viewStack() {
      /**
      ** viewStack() provides a String representation of the contents of the stack, allowing
      ** the user to view the entire contents of the stack.
      **
      ** @return String A String containing all elements of the stack, with the top aligned to the left.
      **
      **/
      String stackString = "";
      for(int i=0; i<=top; i++){
         stackString += stack[i];
      }
      return stackString;
   }
   
}
