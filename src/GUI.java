import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {

    private final Queue<Process> processes = new PriorityQueue<>(Comparator.comparing(Process::getArrivalTime));

    public GUI() {
        this.setSize(900,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        initializeComponent();
        this.setVisible(true);
    }

    public void initializeComponent() {

        JLabel lb_id = new JLabel("ID:");
        JLabel lb_arrivalTime = new JLabel("Arrival Time:");
        JLabel lb_burstTime = new JLabel("Burst Time:");

        JTextField tf_id = new JTextField();
        JTextField tf_arrivalTime = new JTextField();
        JTextField tf_burstTime = new JTextField();
        JButton btn_add  = new JButton("Thêm");
        JButton btn_execute = new JButton("SJF");
        String[] headers = {"ID","Arrival Time","Burst Time", "Waiting Time", "Start Time", "Completion Time", "Turnaround Time"};
        DefaultTableModel model = new DefaultTableModel(headers,0);
        JTable table = new JTable(model);
        JPanel panel = new JPanel(new GridLayout(1,1));
        panel.setBounds(50,115, 800,300);
        panel.add(new JScrollPane(table));
        JButton btn_delete = new JButton("Xoá");

        lb_id.setBounds(10,10,120,25);
        tf_id.setBounds(130,10,200,25);

        btn_add.setBounds(340,10,90,25);
        btn_add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    if(Double.parseDouble(tf_burstTime.getText()) < 0 || Double.parseDouble(tf_arrivalTime.getText()) < 0){
                        throw new RuntimeException("Number is invalid");
                    }
                    processes.add(new Process(Integer.parseInt(tf_id.getText()), Double.parseDouble(tf_arrivalTime.getText()), Double.parseDouble(tf_burstTime.getText())));
                    String[] row = {tf_id.getText(),tf_arrivalTime.getText(),tf_burstTime.getText(), "0", "0", "0", "0"};
                    model.addRow(row);


                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "các trường phải là số nguyên");
                }catch (RuntimeException ex){
                    JOptionPane.showMessageDialog(null, "các trường phải là số nguyên dương");
                }

            }

        });

        lb_arrivalTime.setBounds(10,45,120,25);
        tf_arrivalTime.setBounds(130,45,200,25);

        btn_execute.setBounds(340,45,90,25);
        btn_execute.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                List<Process> processList = executeSJF(processes);
                model.setRowCount(0);
                for (Process p : processList) {
                    String[] row = {
                            String.valueOf(p.getId()),
                            String.valueOf(p.getArrivalTime()),
                            String.valueOf(p.getBurstTime()),
                            String.valueOf(p.getWaitingTime()),
                            String.valueOf(p.getStartTime()),
                            String.valueOf(p.getCompletionTime()),
                            String.valueOf(p.getTurnaroundTime())
                    };
                    model.addRow(row);
                }

            }

        });

        lb_burstTime.setBounds(10,80,120,25);
        tf_burstTime.setBounds(130,80,200,25);

        btn_delete.setBounds(340,80,90,25);
        btn_delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                processes.clear();
                model.setRowCount(0);

            }

        });


        this.add(lb_id);
        this.add(tf_id);
        this.add(lb_arrivalTime);
        this.add(tf_arrivalTime);
        this.add(lb_burstTime);
        this.add(tf_burstTime);
        this.add(btn_add);
        this.add(panel);
        this.add(btn_execute);
        this.add(btn_delete);

    }

    public List<Process> executeSJF(Queue<Process> processes){

        Queue<Process> readyQueue = new PriorityQueue<>(Comparator.comparing(Process::getBurstTime));
        double currentTime = 0;
        List<Process> processList = new ArrayList<>();
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {

            while (!processes.isEmpty() && processes.peek().getArrivalTime() <= currentTime) {
                readyQueue.add(processes.poll());
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.setStartTime(currentTime);
                currentTime += currentProcess.getBurstTime();
                currentProcess.setCompletionTime();
                currentProcess.setTurnaroundTime();
                currentProcess.setWaitingTime();
                processList.add(currentProcess);
            } else {
                currentTime = Math.ceil(currentTime) + 1;
            }
        }
        return processList;
    }

}
