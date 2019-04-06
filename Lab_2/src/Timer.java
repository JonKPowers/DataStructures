class Timer
{
    private long startTime;
    private long stopTime;
    
    Timer(String startImmediately){    
        if (startImmediately.toLowerCase().equals("go")){
                startTime = System.nanoTime();
            }
    }

    public void start(){
        startTime = System.nanoTime();    
    }

    public long stop(){
        stopTime = System.nanoTime();
        return stopTime - startTime;
    }

    public long getElapsed(){
        return stopTime - startTime;
    }

    public double getElapsedSeconds(){
        return (stopTime - startTime) / (double) 1000000000;
    }

}
