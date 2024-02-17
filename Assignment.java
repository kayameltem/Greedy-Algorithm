public class Assignment implements Comparable {
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;


    public Assignment(String name, String start, int duration, int importance, boolean maellard) {
        // initialize all the properties
        this.name = name;
        this.start = start;
        this.duration = duration;
        this.importance = importance;
        this.maellard = maellard;
    }

    /*
            Getter methods
         */
    public String getName() {
        return name;
    }

    public String getStartTime() {
        return start;
    }

    public int getDuration() {
        return duration;
    }

    public int getImportance() {
        return importance;
    }

    public boolean isMaellard() {
        return maellard;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
            // compare finish times as string
            String[] time;
            time = start.split(":");
            int hour = Integer.parseInt(time[0]) + duration;
            time[0] = String.valueOf(hour);
            if(time[0].length() == 1) {
                time[0] = "0" + time[0];
            }
            String finish = time[0] + ":" +time[1];

            return finish;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {

        double weight = (double) (importance * (maellard ? 1001 : 1) ) / duration;

        return weight;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        o = (Assignment) o;
        return getFinishTime().compareTo(((Assignment) o).getFinishTime());
    }

    /**
     * @return Should return a string in the following form:
     * Assignment{name='Refill vending machines', start='12:00', duration=1, importance=45, maellard=false, finish='13:00', weight=45.0}
     */
    @Override
    public String toString() {
        String s ="Assignment{name='" + name+ "', start='" + start+ "', duration=" + duration+ ", importance=" +
                importance+ ", maellard=" +maellard+ ", finish='" + getFinishTime() + "', weight="+ getWeight()+"}";
        return s;
    }
}
