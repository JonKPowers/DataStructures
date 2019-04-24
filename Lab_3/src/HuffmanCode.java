class HuffmanCode
{
   /**
   ** The HuffmanCode class provides a simple container to hold clear-text
   ** characters as well as their computed HuffmanCode.
   **/

   private String character;
   private String code;
   
   /////////////////////////
   // Constructor
   /////////////////////////

   HuffmanCode(String character, String code){
      this.character = character;
      this.code = code;
   }


   /////////////////////////
   // Public Methods--They do what they say
   /////////////////////////
   
   public String getCharacter(){
      return this.character;
   }
   
   public String getCode(){
      return this.code;
   }
}