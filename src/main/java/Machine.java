public class Machine {

	private Boolean isAvailable;

	private java.util.List<Job> jobList;

	private double plannedTime;

	public Machine(Boolean isAvailable, java.util.List<Job> jobList, double plannedTime) {
		this.isAvailable = isAvailable;
		this.jobList = jobList;
		this.plannedTime = plannedTime;
	}

	public Boolean getAvailable() {
		return isAvailable;
	}

	public void setAvailable(Boolean available) {
		isAvailable = available;
	}

	public java.util.List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(java.util.List<Job> jobList) {
		this.jobList = jobList;
	}

	public double getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(double plannedTime) {
		this.plannedTime = plannedTime;
	}

	public void addJob(Job newJob) {
		jobList.add(newJob);
	}

	@Override
	public String toString() {
		return "Machine{" +
				"isAvailable=" + isAvailable +
				", jobList=" + jobList +
				", plannedTime=" + plannedTime +
				'}';
	}
}
