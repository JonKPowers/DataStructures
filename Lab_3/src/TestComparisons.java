class Test
{
   public static void main( String[] args ){
      String output = "Comparing isMultiChar == true with isMultiChar == true: ";
      output += Boolean.compare(true, true);
      System.out.println(output);
      

      output = "Comparing isMultiChar == false with isMultiChar == false: ";
      output += Boolean.compare(false, false);
      System.out.println(output);
      
      output = "Comparing isMultiChar == true with isMultiChar == false: ";
      output += Boolean.compare(true, false);
      System.out.println(output);

      output = "Comparing isMultiChar == false with isMultiChar == true: ";
      output += Boolean.compare(false, true);
      System.out.println(output);

      output = "Comparing 1 with 3: ";
      output += Integer.compare(1, 3);
      System.out.println(output);

      output = "Comparing 3 with 1: ";
      output += Integer.compare(3, 1);
      System.out.println(output);

      output = "Comparing 1 with 1: ";
      output += Integer.compare(1, 1);
      System.out.println(output);

      output = "Comparing A with B: ";
      output += "A".compareTo("B");
      System.out.println(output);

      output = "Comparing B with A: ";
      output += "B".compareTo("A");
      System.out.println(output);

      output = "Comparing A with A: ";
      output += "A".compareTo("A");
      System.out.println(output);

      output = "Comparing ABC with BCD: ";
      output += "ABC".compareTo("BCD");
      System.out.println(output);

      output = "Comparing DEF with BCD: ";
      output += "DEF".compareTo("BCD");
      System.out.println(output);

      output = "Comparing BCD with BCE: ";
      output += "BCD".compareTo("BCE");
      System.out.println(output);

      output = "Comparing BCE with BCD: ";
      output += "BCE".compareTo("BCD");
      System.out.println(output);
      
      output = "Comparing ABC with ABC: ";
      output += "ABC".compareTo("ABC");
      System.out.println(output);

}   
   
}
