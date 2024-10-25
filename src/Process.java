public class Process {

    private int id;
    private double arrivalTime;
    private double burstTime;
    private double waitingTime;
    private double startTime;
    private double turnaroundTime;
    private double completionTime;

    public Process() {
    }

    public Process(int id, double arrivalTime, double burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.startTime = 0;
        this.turnaroundTime = 0;
        this.completionTime = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime() {
        this.waitingTime = this.turnaroundTime - burstTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime() {
        this.turnaroundTime = this.completionTime - this.arrivalTime;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime() {
        this.completionTime = this.startTime + this.burstTime;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", waitingTime=" + waitingTime +
                '}';
    }
}
