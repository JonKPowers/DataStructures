class Timer
{
    private long startTime;
    private long stopTime;
   private long[] times;
   private int numRuns;
   private int counter = -1;

   Timer(int numRuns){    
      times = new long[numRuns];
      this.numRuns = numRuns;
   }

   public void reset(){
      counter = -1;
   }
   public void start(){
      startTime = System.nanoTime();    
   }

   public void stop(){
      stopTime = System.nanoTime();
      // If we've hit the top index of the times array, keep overwriting
      // the last entry. Not a brilliant solution, but this class isn't
      // intended for extensive use or abuse, so robustness wasn't built in.
      if(counter < numRuns - 1){
         counter++;
      }
      times[counter] = stopTime - startTime;
   }

   public long getElapsed(){
      /** getElapsed() returns the most recent time recorded.
       *
       * @return long Most recent time recorded by the Timer
       **/
       return times[counter];
   }

   public double getElapsedSeconds(){
      return times[counter] / (double) 1000000000;
   }

   public long[] getTimes(){
      /* getTimes() returns an array of all the times recorded
       * by the Timer at the moment.
       *
       * @return long[] An array of the times recorded by the Timer
       **/

      long[] output = new long[counter + 1];
      for(int i=0; i<counter; i++){
         output[i] = times[i];
      }
      return output;
   }

   public double[] getTimesSeconds(){
      /* getTimesSeconds() returns an array of all the times
       * (in seconds) recorded by the Timer at the moment.
       *
       * @return double[] An array of the times (in seconds) recorded by the Timer
       **/
  
      double[] output = new double[counter + 1];
      for(int i=0; i<=counter; i++){
         output[i] = times[i] / (double) 1000000000;
      }
      return output;
   }

   public long getAverageTime(){
      long total = 0;
      for(int i=0; i<=counter; i++){
         total += times[i];
      }
      // Drops any remainder, but that's an imprecision I can live with
      return total / (counter + 1);
   }

   public double getAverageTimeSeconds(){
      return getAverageTime() / (double) 1000000000;
   }

}
