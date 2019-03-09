class ExpressionException extends RuntimeException
{
   private String message;
   
   ExpressionException(){
      this.message = "Cannot pop or peek from an empty stack";
   }

   ExpressionException(String message){
      this.message = message;
   }
   
   public String toString(){
      return message;
   }
   
   public String getMessage(){
      return this.toString();
   }

}