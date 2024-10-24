public class Process {

    private int id;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int startTime;

    public Process() {
    }

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.startTime = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
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
