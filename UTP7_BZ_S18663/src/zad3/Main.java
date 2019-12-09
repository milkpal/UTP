/**
 *
 *  @author Bubrowska Zuzanna S18663
 *
 */

package zad3;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Main {

  public static void main(String[] args) {

    MyJFrame frame = new MyJFrame();
    DefaultListModel<Task> taskDefaultListModel = new DefaultListModel<>();
    JList<Task> taskJList = new JList<>(taskDefaultListModel);
    taskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JPanel buttonPanel = new JPanel();
    JPanel statusPanel = new JPanel();
    JPanel tasksPanel = new JPanel();
    JTextArea output = new JTextArea();
    Button newThread = new Button("New");
    Button showThread = new Button ("Show");
    Button stopThread = new Button ("Stop");
    JScrollPane scrollPane = new JScrollPane();

    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setViewportView(taskJList);

    showThread.setEnabled(false);
    stopThread.setEnabled(false);

    newThread.addActionListener(e->{
      try{
        new Task(5,taskDefaultListModel).startTask();
      }catch(Exception e1){
        e1.printStackTrace();
      }
    });

    showThread.addActionListener(e->{
      int idx = taskJList.getSelectedIndex();
      if(idx>-1){
        JOptionPane.showMessageDialog(frame,taskDefaultListModel.get(idx)+" "+taskDefaultListModel.get(idx).getStatus());
      }
    });
    stopThread.addActionListener(e->{
      int idx = taskJList.getSelectedIndex();
      if(idx>-1){
        taskDefaultListModel.get(idx).stopTask();
      }
    });

    taskJList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        int selected= ((JList<String>)e.getSource()).getSelectedIndex();
        if (selected>-1){

          showThread.setEnabled(true);
          stopThread.setEnabled(true);

          output.setText(taskDefaultListModel.get(selected).mssg);
          taskDefaultListModel.get(selected).setPropertyChange(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
              output.setText((String)evt.getNewValue());
            }
          });
          if(taskDefaultListModel.get(selected).result.isDone()) {
            stopThread.setEnabled(false);
            buttonPanel.repaint();
          }
        }
      }
    });

    buttonPanel.add(newThread);
    buttonPanel.add(showThread);
    buttonPanel.add(stopThread);

    tasksPanel.setLayout(new BoxLayout(tasksPanel,BoxLayout.Y_AXIS));
    tasksPanel.add(scrollPane);

    statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.Y_AXIS));
    statusPanel.add(output);

    frame.setLayout(new BorderLayout());
    frame.add(buttonPanel,BorderLayout.NORTH);
    frame.add(tasksPanel,BorderLayout.CENTER);
    frame.add(statusPanel,BorderLayout.SOUTH);
    frame.pack();
    frame.setVisible(true);

  }
}
