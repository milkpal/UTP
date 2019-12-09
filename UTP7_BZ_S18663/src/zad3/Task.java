package zad3;

import jdk.nashorn.internal.codegen.CompilerConstants;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Task extends Thread implements Callable<Integer> {
   String mssg;
   ExecutorService executorService = Executors.newFixedThreadPool(5);
   PropertyChangeListener pcl;
   int value=0,calculate;
   Future<Integer> result;

   public Task(int calculate, DefaultListModel<Task> model){
       super();
       this.calculate=calculate;
       model.addElement(this);
   }

   public void setPropertyChange(PropertyChangeListener pcl){
       this.pcl=pcl;
   }

   public void updateMessage(String s){
       if(pcl!=null){
           if(mssg!=null)
               this.pcl.propertyChange(new PropertyChangeEvent(this,"mssg",mssg,mssg+=s));
           else
               this.pcl.propertyChange(new PropertyChangeEvent(this,"mssg",mssg,mssg=s));
       }else
           mssg +=s;
   }

    @Override
    public Integer call() throws Exception {

       for(int i=0;i<calculate;i++){
           try{
               Thread.sleep(2000);
           }catch(InterruptedException e) {break;}
           value +=i;
           this.updateMessage("Current value: "+value+"\n");
       }

       this.updateMessage("Task done\n");
        return value;
    }

    public void startTask(){
        Callable<Integer> call = this;
        result = executorService.submit(call);
    }

    public void stopTask(){
       this.updateMessage("Task stopped \n");
       this.result.cancel(true);
    }

    public String getStatus(){
        if(this.result.isCancelled()) {
            return "Status: Interrupted";
        }else if(this.result.isDone()){
            return "Status: Finished.";}

        return "Status: Running";
    }



}
