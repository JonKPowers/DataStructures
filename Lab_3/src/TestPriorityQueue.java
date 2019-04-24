class TestPriorityQueue
{

   public static void main( String[] args ) {
   
      // Test that basic lowest frequency nodes are prioritized
      // And that push/pop operations work as expected.
      PriorityQueue queue = new PriorityQueue(5);
      HuffmanNode tempNode;
      
      queue.push(new HuffmanNode("A", 1));
      queue.push(new HuffmanNode("B", 4));
      queue.push(new HuffmanNode("C", 9));
      
      tempNode = queue.pop();
      assert tempNode.getFrequency() == 1;
      tempNode = queue.pop();
      assert tempNode.getFrequency() == 4;
      tempNode = queue.pop();
      assert tempNode.getFrequency() == 9;
      assert queue.isEmpty();
      
      // Test that single-character nodes are prioritized over multi-chars
      queue.push(new HuffmanNode("A", 1));
      queue.push(new HuffmanNode("ABC", 1));
      queue.push(new HuffmanNode("CBA", 5));
      queue.push(new HuffmanNode("C", 5));
      
      tempNode = queue.pop();
      assert tempNode.getChars().equals("A");
      tempNode = queue.pop();
      assert tempNode.getChars().equals("ABC");
      tempNode = queue.pop();
      assert tempNode.getChars().equals("C");
      tempNode = queue.pop();
      assert tempNode.getChars().equals("CBA");
      assert queue.isEmpty();
      
      // Test that same-value multi-chars are prioritized alphabetically
      queue.push(new HuffmanNode("DEF", 1));
      queue.push(new HuffmanNode("EFGH", 1));
      queue.push(new HuffmanNode("JON", 1));
      queue.push(new HuffmanNode("LOKI", 1));
      
      tempNode = queue.pop();
      assert tempNode.getChars().equals("DEF");
      tempNode = queue.pop();
      assert tempNode.getChars().equals("EFGH");
      tempNode = queue.pop();
      assert tempNode.getChars().equals("JON");
      tempNode = queue.pop();
      assert tempNode.getChars().equals("LOKI");
      assert queue.isEmpty();
   
   }

}