public class KResultSet {

    private final Double K;

    private final Double TWT;

    private final SchedulingAlgorithm schedulingAlgorithm;

    public KResultSet(Double k, Double TWT, SchedulingAlgorithm schedulingAlgorithm) {
        K = k;
        this.TWT = TWT;
        if (schedulingAlgorithm == null) {
            throw new NullPointerException();
        }
        this.schedulingAlgorithm = schedulingAlgorithm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KResultSet that = (KResultSet) o;

        if (K != null ? !K.equals(that.K) : that.K != null) return false;
        if (TWT != null ? !TWT.equals(that.TWT) : that.TWT != null) return false;
        return schedulingAlgorithm == that.schedulingAlgorithm;
    }

    @Override
    public int hashCode() {
        int result = K != null ? K.hashCode() : 0;
        result = 31 * result + (TWT != null ? TWT.hashCode() : 0);
        result = 31 * result + (schedulingAlgorithm != null ? schedulingAlgorithm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        switch (schedulingAlgorithm) {
            case ATC:
                return "KResultSet{" +
                        "K=" + K +
                        ", TWT=" + TWT +
                        ", Scheduler=" + schedulingAlgorithm +
                        '}';
            case LPT:
                return "KResultSet{" +
                        "TWT=" + TWT +
                        ", Scheduler=" + schedulingAlgorithm +
                        '}';
        }
        return "KResultSet{" +
                "TWT=" + TWT +
                ", Scheduler=" + schedulingAlgorithm +
                '}';
    }

    public Double getK() {
        return K;
    }

    public Double getTWT() {
        return TWT;
    }

    public SchedulingAlgorithm getSchedulingAlgorithm() {
        return schedulingAlgorithm;
    }
}