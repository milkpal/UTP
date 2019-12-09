package zad2;

public class StringTask implements Runnable {

    public enum TaskState {
        CREATED,RUNNING, ABORTED, READY
    }

    String toCopy,result="";
    int howMany;
    volatile TaskState currentState;
    Thread thread;

    StringTask(String toCopy, int howMany){
        currentState= TaskState.CREATED;
        this.toCopy=toCopy;
        this.howMany=howMany;
    }


    @Override
    public void run() {
        currentState= TaskState.RUNNING;
        while(howMany!=0 && currentState!= TaskState.ABORTED&&!Thread.currentThread().isInterrupted()){
            result+=toCopy;
            howMany--;
        }
        if(currentState!= TaskState.ABORTED)
        currentState= TaskState.READY;
    }

    public String getResult(){
        return result;
    }

    public TaskState getState(){
        return currentState;
    }

    public void start(){
        thread = new Thread(this,toCopy);
        thread.start();
    }

    public void abort(){
        currentState= TaskState.ABORTED;
        thread.interrupt();

    }

    public boolean isDone(){
        if(currentState== TaskState.READY||currentState== TaskState.ABORTED) return true;
        return false;
    }
}
