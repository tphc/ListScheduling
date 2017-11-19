public class Job {
    private final double j;

    private final double wj;

    private final double dj;

    private final double pj;

    private Double priority = 0.0;

    private double timeFinished;


    public Job(double j, double wj, double dj, double pj) {
        this.j = j;
        this.wj = wj;
        this.dj = dj;
        this.pj = pj;
    }

    @Override
    public String toString() {
        return "Job{" +
                "j=" + j +
                ", wj=" + wj +
                ", dj=" + dj +
                ", pj=" + pj +
                ", priority=" + priority +
                ", timeFinished=" + timeFinished +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (Double.compare(job.j, j) != 0) return false;
        if (Double.compare(job.wj, wj) != 0) return false;
        if (Double.compare(job.dj, dj) != 0) return false;
        if (Double.compare(job.pj, pj) != 0) return false;
        return priority != null ? priority.equals(job.priority) : job.priority == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(j);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(wj);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dj);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pj);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        return result;
    }

    public double getJ() {
        return j;
    }

    public double getWj() {
        return wj;
    }

    public double getDj() {
        return dj;
    }

    public double getPj() {
        return pj;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public double getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(double timeFinished) {
        this.timeFinished = timeFinished;
    }
}