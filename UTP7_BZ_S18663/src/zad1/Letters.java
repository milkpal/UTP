package zad1;

import java.util.ArrayList;
import java.util.List;

public class Letters {

    String letters;
    int index=0;
    boolean stop=false;

    public Letters(String letters){
        this.letters=letters;
    }

    List<Thread> getThreads(){
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<letters.length();i++){
            index=i;
            char letter=letters.charAt(i);
            threads.add(new Thread(()->{
                while(true){
                    if(stop) Thread.currentThread().interrupt();
                    //System.out.println(Thread.currentThread().isInterrupted());
                    if(Thread.currentThread().isInterrupted()) return;
                    System.out.print(letter);
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        break;
                    }
                }
            },"Thread "+letter));
        }

        return threads;
    }

    public void stopThreads(){
        stop=true;
    }
}
