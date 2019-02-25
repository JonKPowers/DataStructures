class EmptyStackError extends Exception
{
   private String message;
   
   EmptyStackError(){
      this.message = "Cannot pop or peek from an empty stack";
   }
   EmptyStackError(String message){
      this.message = message;
   }
   
   public String toString(){
      return message;
   }
   
   public String getMessage(){
      return this.toString();
   }

}