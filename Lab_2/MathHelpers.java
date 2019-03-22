class MathHelpers
{

   public static long exponentiate(int base, int exponent){
      /**
      ** exponentiate() evaluates simple integer exponents. It is used
      ** as an alternative to java.lang.Math.pow(), which evaluates
      ** floating point values and will not provide precision when
      ** dealing with large results. 
      **
      ** @param base The base component of the exponent
      ** @param exponent The exponentiation component of the exponent
      **
      ** @return long The result of raising a to the bth power.
      ** @throws ArithmeticException Thrown on unhandled special cases.
      **/
      
      if(base == 0 || exponent < 1){throw new ArithmeticException();}
      
      long result = base;
      for(int i=1; i < exponent; i++){
         result *= base;
      }
      return result;
   }

}