class EmptyStackException extends Exception
{
   private String message;
   
   EmptyStackException(){
      this.message = "Cannot pop or peek from an empty stack";
   }
   EmptyStackException(String message){
      this.message = message;
   }
   
   public String toString(){
      return message;
   }
   
   public String getMessage(){
      return this.toString();
   }

}