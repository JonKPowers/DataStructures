class IntStack
{
   /**
   ** The IntStack class provides a stack implementation for int primitives,
   ** providing standard methods to manipulate the stack. The stack
   ** is implemented using an array, but the array will automatically increase
   ** its size to prevent overflows. The overflow protection cannot be overridden.
   **
   ** @author Jon Powers
   ** @version 0.1
   **/
   
   private int[] stack;
   private int top;
   private final int INITIALSIZE = 8;
   
   // Constructors
   IntStack(){
      /** This constructor provides a stack with an initial size of INITIALSIZE
      **/
      stack = new int[INITIALSIZE];
      top = -1;
   }
   
   IntStack(int stackSize){
      /** This constructor provides a stack with an initial size of stackSize
      **
      ** @param stackSize The initial size of the new stack.
      **/
      stack = new int[stackSize];
      top = -1;
   }
   
   //
   // Public methods
   //
   
   public boolean isEmpty(){
      /**
      ** isEmpty() checks whether the IntStack is empty.
      **
      ** @return bool True if the stack is empty; otherwise false.
      **
      **/
      return top == -1;
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
   
   public void push(int newItem){
      /**
      ** push() adds an element to the top of the stack. If for any reason, the method encounters
      ** a problem assigning the new element to the correct position in the array, it will print
      ** an error message and not modify the stack array. While this stack implementation
      ** is designed to avoid overflows, and thus out of bounds exceptions, you can never
      ** be too careful.
      **s
      ** @param newItem The int that is to be added to the stack.
      ** @return None Nothing is returned.
      **
      **/
      // If the array holding the stack is full, make it longer.
      if(isFull()){
         stretchStack();
      }
      // Add the element to the stack
      try{
         stack[++top] = newItem;
      } catch(ArrayIndexOutOfBoundsException except) {
         System.out.println("Something terrible happened: " + except.getMessage());
      }
   }
   
   public int pop(){
      /**
      ** pop() removes the top element on the stack and returns it.
      **
      ** @return int The item that was removed from the top of the stack.
      ** @throws EmptyStackError On an attempt to call pop() on an empty stack.
      **
      **/
      if (this.isEmpty()){
         throw new EmptyStackException();
      }
      return stack[top--];
   }
   
   public int peek(){
      /**
      ** peek() returns the top element on the stack without removing it.
      **
      ** @return int The item at the top of the stack.
      ** @throws EmptyStackError On an attempt to call peek() on an empty stack.
      **
      **/
      if(this.isEmpty()){
         System.out.println("ERROR: Cannot peek on an empty stack.");
         throw new EmptyStackException();
      }
      return stack[top];
      
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
      for(int i=top; i>=0; i--){
         stackString += stack[i];
      }
      return stackString;
   }
   
   public IntStack copy(){
      /**
      ** copy() makes and returns a copy of the current stack in its current state.
      **
      ** @return IntStack A copy of the current stack.
      **/
      IntStack stackCopy = new IntStack(this.getLength());
      for(int i=0; i<=this.top; i++){
         stackCopy.push(this.stack[i]);
      }
      return stackCopy;
   }
   
   public void reverse(){
      /**
      ** reverse() reverses the contents of the stack in place by swapping
      ** elements at each end until the middle is reached.
      ** @return None Nothing is returned.
      **/
      for(int i=0; i < (top + 1) / 2; i++){
         int temp = stack[i];
         stack[i] = stack[top - i];
         stack[top - i] = temp;
      }
   }
   
   //
   // Private methods
   //
   
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
      
      int[] newStack = new int[stack.length * 2];
      
      // Copy the contents of the full array to the new, larger array
      for(int i=0; i<stack.length; i++){
         newStack[i] = stack[i];
      } 
      stack = newStack;
   } 
}
