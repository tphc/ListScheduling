import java.util.*;

public class Main {

	private static List<Job> getJobData() {
		final List<Job> jobList = new ArrayList<>();
		jobList.add(new Job(0, 6, 2, 4));
		jobList.add(new Job(1, 5, 7, 6));
		jobList.add(new Job(2, 5, 5, 6));
		jobList.add(new Job(3, 1, 5, 3));
		jobList.add(new Job(4, 2, 8, 5));
		jobList.add(new Job(5, 6, 6, 4));
		jobList.add(new Job(6, 2, 5, 2));
		jobList.add(new Job(7, 3, 7, 4));
		jobList.add(new Job(8, 4, 8, 5));
		jobList.add(new Job(9, 4, 6, 3));
		jobList.add(new Job(10, 5, 5, 2));
		jobList.add(new Job(11, 3, 2, 4));
		return jobList;
	}

	public static void main(String[] args) {

		final List<KResultSet> kResultSets = new ArrayList<KResultSet>();
		// kList mit den Kappa Werten füllen
		// Aufgabe 1.3: 0.5
		// Aufgabe 1.4a: 0.1 - 10
		// Aufgabe 1.4b: 3.875
		List<Double> kList = Arrays.asList(0.5);

		for (Double K : kList) {
			kResultSets.add(getTWT(K, getJobData(), SchedulingAlgorithm.ATC));
		}

		// Minimales TWT
		final KResultSet minTWT = Collections.min(kResultSets, Comparator.comparing(kRes -> kRes.getTWT()));
	}

	/**
	 * Gibt für einen K Wert und eine Liste von Jobs das TWT zurück.
	 *
	 * @param K
	 * @param jobList
	 * @return
	 */
	public static KResultSet getTWT(double K, final List<Job> jobList, SchedulingAlgorithm scheduler) {

		List<Job> scheduledList = new ArrayList<>();
		switch (scheduler) {
			case ATC:
				scheduledList = atc(K, jobList, Arrays.asList(
						new Machine(false, new java.util.ArrayList<>(), 0.0),
						new Machine(false, new java.util.ArrayList<>(), 0.0),
						new Machine(false, new java.util.ArrayList<>(), 0.0)
				));
				break;
			case LPT:
				scheduledList = lpt(jobList, Arrays.asList(
						new Machine(false, new java.util.ArrayList<>(), 0.0),
						new Machine(false, new java.util.ArrayList<>(), 0.0),
						new Machine(false, new java.util.ArrayList<>(), 0.0)));
				break;
		}

		// TWT berechnen
		double TWT = scheduledList.stream().mapToDouble(job -> (job.getWj() * (Math.max((job.getTimeFinished() - job.getDj()), 0.0)))).sum();
		return new KResultSet(K, TWT, scheduler);
	}

	/**
	 * ATC Algorithmus
	 *
	 * @param K           Vorausschauparameter
	 * @param pendingJobs Liste mit den Jobs.
	 * @return Nach Priorität sortierte Liste
	 */
	private static List<Job> atc(double K, final List<Job> pendingJobs, final List<Machine> machineList) {
		final List<Job> scheduledList = new ArrayList<>();

		while (!pendingJobs.isEmpty()) {
			double sumTime = pendingJobs.stream().mapToDouble(o -> o.getPj()).sum();
			double avgTime = sumTime / pendingJobs.size();

			// Maschine mit der am wenigsten verplanten Zeit
			final Machine actMachine = Collections.min(machineList, Comparator.comparing(machine -> machine.getPlannedTime()));
			// Jobs mit ATC Formel priorisieren
			pendingJobs.forEach(job -> job.setPriority((job.getWj() / job.getPj()) * Math.pow(Math.E, -(Math.max((job.getDj() - job.getPj() - actMachine.getPlannedTime()), 0) / (K * avgTime)))));
			// Job mit höchster Priorität
			Job highestPrioJob = Collections.max(pendingJobs, Comparator.comparing(job -> job.getPriority()));
			// Fertigstellungstermin
			highestPrioJob.setTimeFinished(actMachine.getPlannedTime() + highestPrioJob.getPj());
			// Job wird der Maschine hinzugefügt
			actMachine.addJob(highestPrioJob);
			// Verplante Zeit der Maschine hochsetzen
			actMachine.setPlannedTime(actMachine.getPlannedTime() + highestPrioJob.getPj());
			// Ergebnisliste aktualisierern
			scheduledList.add(highestPrioJob);
			// Job löschen
			pendingJobs.remove(highestPrioJob);
		}
		return scheduledList;
	}

	/**
	 * LPT Algorithmus
	 *
	 * @param pendingJobs Liste mit den Jobs.
	 * @return Nach Priorität sortierte Liste
	 */
	private static List<Job> lpt(final List<Job> pendingJobs, final List<Machine> machineList) {
		final List<Job> scheduledList = new ArrayList<>();
		while (!pendingJobs.isEmpty()) {
			// Maschine mit der am wenigsten verplanten Zeit
			final Machine actMachine = Collections.min(machineList, Comparator.comparing(machine -> machine.getPlannedTime()));
			// Job mit höchster Priorität
			Job highestPrioJob = Collections.max(pendingJobs, Comparator.comparing(job -> job.getPj()));
			// Fertigstellungstermin
			highestPrioJob.setTimeFinished(actMachine.getPlannedTime() + highestPrioJob.getPj());
			// Job wird der Maschine hinzugefügt
			actMachine.addJob(highestPrioJob);
			// Verplante Zeit der Maschine hochsetzen
			actMachine.setPlannedTime(actMachine.getPlannedTime() + highestPrioJob.getPj());
			// Ergebnisliste aktualisierern
			scheduledList.add(highestPrioJob);
			// Job löschen
			pendingJobs.remove(highestPrioJob);
		}
		return scheduledList;
	}
}