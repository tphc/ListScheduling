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
        final List<Double> kList = Arrays.asList(0.0001, 100.0, 1000000.0);

        for (Double K : kList) {
            kResultSets.add(getTWT(K, getJobData(), SchedulingAlgorithm.LPT));
            kResultSets.add(getTWT(K, getJobData(), SchedulingAlgorithm.ATC));
        }

        // Sortierung nach TWT absteigend
        kResultSets
                .stream()
                .sorted((kRes1, kRes2) -> Double.compare(kRes2.getTWT(),
                        kRes1.getTWT()))
                .forEach(kRes -> System.out.println(kRes));
    }

    /**
     * Gibt für ein K Wert und eine Map von Jobs das TWT zurück.
     *
     * @param K
     * @param jobList
     * @return
     */
    public static KResultSet getTWT(double K, final List<Job> jobList, SchedulingAlgorithm scheduler) {

        List<Job> scheduledList = new ArrayList<>();
        switch (scheduler) {
            case ATC:
                scheduledList = atc(K, jobList);
                break;
            case LPT:
                scheduledList = lpt(jobList);
                break;
        }
        // TWT berechnen
        double TWT = scheduledList.stream().mapToDouble(job -> (job.getWj() * (Math.max((job.getTimeFinished() - job.getDj()), 0.0)))).sum();

        System.out.println("\n_____________\n");
        System.out.println("TWT: " + TWT + "\n");

        return new KResultSet(K, TWT, scheduler);
    }

    /**
     * ATC Algorithmus
     *
     * @param K           Vorausschauparameter
     * @param pendingJobs Liste mit den Jobs.
     * @return Nach Priorität sortierte Liste
     */
    private static List<Job> atc(double K, final List<Job> pendingJobs) {
        final List<Job> scheduledList = new ArrayList<>();
        int t = 0;

        while (!pendingJobs.isEmpty()) {
            double sumTime = pendingJobs.stream().mapToDouble(o -> o.getPj()).sum();
            double avgTime = sumTime / pendingJobs.size();

            final int t2 = t;
            // ATC Formel für alle Jobs anwenden
            pendingJobs.forEach(job -> job.setPriority((job.getWj() / job.getPj()) * Math.pow(Math.E, -(Math.max((job.getDj() - job.getPj() - t2), 0) / (K * avgTime)))));
            // Job mit höchster Priorität
            Job highestPrioJob = Collections.max(pendingJobs, Comparator.comparing(job -> job.getPriority()));

            // Job wird bearbeitet
            // Laufzeit hochsetzen
            t += highestPrioJob.getPj();
            // Fertigstellungstermin
            highestPrioJob.setTimeFinished(t);
            System.out.println(highestPrioJob);
            // Ergebnisliste
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
    private static List<Job> lpt(final List<Job> pendingJobs) {
        final List<Job> scheduledList = new ArrayList<>();
        int t = 0;
        while (!pendingJobs.isEmpty()) {
            Job highestPrioJob = Collections.max(pendingJobs, Comparator.comparing(job -> job.getDj()));
            // Job wird bearbeitet
            // Zeit hochsetzen
            t += highestPrioJob.getPj();
            // Fertigstellungstermin
            highestPrioJob.setTimeFinished(t);
            System.out.println(highestPrioJob);
            // Ergebnisliste
            scheduledList.add(highestPrioJob);
            // Job löschen
            pendingJobs.remove(highestPrioJob);
        }
        return scheduledList;
    }
}