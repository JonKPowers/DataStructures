class StringStack
{
   /**
   ** The StringStack class provides a stack implementation for String dat types 
   ** providing standard methods to manipulate the stack. The stack
   ** is implemented using an array, but the array will automatically increase
   ** its size to prevent overflows. The overflow protection cannot be overridden.
   **
   ** @author Jon Powers
   ** @version 0.1
   **/
   
   private String[] stack;
   private int top;
   private final int INITIALSIZE = 8;
   
   // Constructors
   StringStack(){
      /** This constructor provides a stack with an initial size of INITIALSIZE
      **/
      stack = new String[INITIALSIZE];
      top = -1;
   }
   
   StringStack(int stackSize){
      /** This constructor provides a stack with an initial size of stackSize
      **
      ** @param stackSize The initial size of the new stack.
      **/
      stack = new String[stackSize];
      top = -1;
   }
   
   StringStack(String seedString){
      /**
      ** This constructor takes in a String, which is placed onto the stack 
      ** during initialization.
      **
      ** @param seedString The String to be used to initialize the stack
      **/
      stack = new String[seedString.length()];
      String[] seedArray = seedString.split("");
      for(int i=0; i<seedString.length(); i++){
         stack[i] = seedArray[i];
      }
      top = seedString.length() - 1;
   }
   
   StringStack(String seedString, boolean reverseIt){
      /**
      ** This constructor takes in a String, which is placed onto the stack 
      ** during initialization. Optionally, the stack can be initialized with
      ** the string reversed.
      **
      ** @param seedString The String to be used to initialize the stack
      ** @param reverseIt If true, the String will be placed onto the stack in reverse order. 
      **/
      stack = new String[seedString.length()];
      String[] seedArray = seedString.split("");
      if(reverseIt) {
         int j = 0;
         for(int i=seedString.length() - 1; i>-1; i--){
            stack[j++] = seedArray[i];
         }
      } else {
         for(int i=0; i<seedString.length(); i++){
            stack[i] = seedArray[i];
         }
      }
      top = seedString.length() - 1;
   }
   
   public boolean isEmpty(){
      /**
      ** isEmpty() checks whether the StringStack is empty.
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
   
   public void push(String newItem){
      /**
      ** push() adds an element to the top of the stack. If for any reason, the method encounters
      ** a problem assigning the new element to the correct position in the array, it will print
      ** an error message and not modify the stack array. While this stack implementation
      ** is designed to avoid overflows, and thus out of bounds exceptions, you can never
      ** be too careful.
      **s
      ** @param newItem The String that is to be added to the stack.
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
   
   public String pop()throws EmptyStackException{
      /**
      ** pop() removes the top element on the stack and returns it.
      **
      ** @return String The item that was removed from the top of the stack.
      ** @throws EmptyStackError On an attempt to call pop() on an empty stack.
      **
      **/
      if (this.isEmpty()){
         System.out.println("ERROR: Cannot pop from empty stack.");
         throw new EmptyStackException();
      }
      return stack[top--];
   }
   
   public String peek()throws EmptyStackException{
      /**
      ** peek() returns the top element on the stack without removing it.
      **
      ** @return String The item at the top of the stack.
      ** @throws EmptyStackError On an attempt to call peek() on an empty stack.
      **
      **/
      if(this.isEmpty()){
         System.out.println("ERROR: Cannot peek on an empty stack.");
         throw new EmptyStackException();
      }
      return stack[top];
      
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
      
      String[] newStack = new String[stack.length * 2];
      
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
   
   public StringStack copy(){
      /**
      ** copy() makes and returns a copy of the current stack in its current state.
      **
      ** @return StringStack A copy of the current stack.
      **/
      StringStack stackCopy = new StringStack(this.getLength());
      for(int i=0; i<=this.top; i++){
         stackCopy.push(this.stack[i]);
      }
      return stackCopy;
   }
   
}
