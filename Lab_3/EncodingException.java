class EncodingException extends RuntimeException
{
   private String message;

   EncodingException(String errorMessage){
      this.message = message;      
   }
   
   public String toString(){
    return this.message;
   }
   
   public String getMessage(){
      return this.toString();
   }

}