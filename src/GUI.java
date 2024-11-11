import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {

    private final Queue<Process> processes = new PriorityQueue<>(Comparator.comparing(Process::getArrivalTime));

    public GUI() {
        this.setSize(900, 600);
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
        JButton btn_add = new JButton("Thêm");
        JButton btn_execute = new JButton("SJF");
        JButton btn_delete = new JButton("Xoá");

        String[] headers = {"ID", "Arrival Time", "Burst Time", "Waiting Time", "Start Time", "Completion Time", "Turnaround Time"};
        DefaultTableModel model = new DefaultTableModel(headers, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        lb_id.setBounds(10, 10, 120, 25);
        tf_id.setBounds(130, 10, 200, 25);

        btn_add.setBounds(340, 10, 90, 25);
        btn_add.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tf_id.getText());
                double arrivalTime = Double.parseDouble(tf_arrivalTime.getText());
                double burstTime = Double.parseDouble(tf_burstTime.getText());
                if (arrivalTime < 0 || burstTime < 0) throw new RuntimeException("Invalid number");

                processes.add(new Process(id, arrivalTime, burstTime));
                model.addRow(new Object[]{id, arrivalTime, burstTime, 0, 0, 0, 0});
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Fields must be valid numbers");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Fields must be positive numbers");
            }
        });

        lb_arrivalTime.setBounds(10, 45, 120, 25);
        tf_arrivalTime.setBounds(130, 45, 200, 25);

        btn_execute.setBounds(340, 45, 90, 25);
        btn_execute.addActionListener(e -> {
            List<Process> processList = executeSJF(processes);
            model.setRowCount(0);
            for (Process p : processList) {
                model.addRow(new Object[]{
                        p.getId(),
                        p.getArrivalTime(),
                        p.getBurstTime(),
                        p.getWaitingTime(),
                        p.getStartTime(),
                        p.getCompletionTime(),
                        p.getTurnaroundTime()
                });
            }
            drawGanttChart(processList);
        });

        lb_burstTime.setBounds(10, 80, 120, 25);
        tf_burstTime.setBounds(130, 80, 200, 25);

        btn_delete.setBounds(340, 80, 90, 25);
        btn_delete.addActionListener(e -> {
            processes.clear();
            model.setRowCount(0);
        });

        scrollPane.setBounds(50, 115, 800, 300);

        this.add(lb_id);
        this.add(tf_id);
        this.add(lb_arrivalTime);
        this.add(tf_arrivalTime);
        this.add(lb_burstTime);
        this.add(tf_burstTime);
        this.add(btn_add);
        this.add(btn_execute);
        this.add(btn_delete);
        this.add(scrollPane);
    }

    public List<Process> executeSJF(Queue<Process> processes) {
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
                currentProcess.setCompletionTime();
                currentProcess.setTurnaroundTime();
                currentProcess.setWaitingTime();
                currentTime = currentProcess.getCompletionTime();
                processList.add(currentProcess);
            } else {
                currentTime++;
            }
        }
        return processList;
    }

    public void drawGanttChart(List<Process> processes) {
        JFrame ganttFrame = new JFrame("Gantt Chart");
        ganttFrame.setSize(800, 200);
        ganttFrame.setLocationRelativeTo(null);

        JPanel ganttPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = 20;
                int y = 50;
                int height = 30;

                for (Process process : processes) {
                    int width = (int) (process.getBurstTime() * 50);
                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                    g.drawString("P" + process.getId(), x + width / 2 - 5, y + height / 2 + 5);
                    g.drawString(String.valueOf(process.getStartTime()), x - 5, y + height + 20);
                    x += width;
                }
                g.drawString(String.valueOf(processes.get(processes.size() - 1).getCompletionTime()), x - 5, y + height + 20);
            }
        };

        ganttPanel.setPreferredSize(new Dimension(800, 100));
        ganttFrame.add(ganttPanel);
        ganttFrame.setVisible(true);
    }

}
