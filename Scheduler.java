import java.util.ArrayList;

public class Scheduler {

    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;

    /**
     * @throws IllegalArgumentException when the given array is empty
     */
    public Scheduler(Assignment[] assignmentArray) throws IllegalArgumentException {
            // if json file is empty, throw IllegalArgumentException
            if (assignmentArray.length == 0) {
                throw new IllegalArgumentException();
            }
            // initialize all the properties
            this.assignmentArray = assignmentArray;
            C = new Integer[assignmentArray.length];
            max = new Double[assignmentArray.length];
            solutionDynamic = new ArrayList<Assignment>();
            calculateC();
            calculateMax(assignmentArray.length - 1);
            findSolutionDynamic(assignmentArray.length - 1);

        // Should be instantiated with an Assignment array
        // All the properties of this class should be initialized here
    }

    /**
     * @param index of the {@link Assignment}
     * @return Returns the index of the last compatible {@link Assignment},
     * returns -1 if there are no compatible assignments
     */
    private int binarySearch(int index) {
        int l = 0;  //left boundary
        int r = index;  //right boundary
        int max = -1;

        // while left <= right boundary
        while(l <= r){
            int mid = (l+r) /2;  // middle point
            if(assignmentArray[mid].getFinishTime().compareTo(assignmentArray[index].getStartTime()) <= 0){
                max = Math.max(max,mid);
                // update left boundary
                l = mid +1;
            }
            else {
                // update right boundary
                r = mid -1;
            }
        }
        return max;
    }

    /**
     * {@link #C} must be filled after calling this method
     */
    private void calculateC() {
        // calculate C
        for (int i = 0; i < assignmentArray.length; i++) {
           C[i] = binarySearch(i);
        }
    }


    /**
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleDynamic() {
        ArrayList<Assignment> reversed = new ArrayList<>();
        // reverse the solution Dynamic
        for (Assignment a: solutionDynamic) {
            reversed.add(0,a);
        }
        return reversed;
    }

    /**
     * {@link #solutionDynamic} must be filled after calling this method
     */
    private void findSolutionDynamic(int i) {
        // if index is out of bound
        if( i < 0 || i >= assignmentArray.length){
            return;
        }

        System.out.println(String.format("findSolutionDynamic(%s)",i));
        // if max[i] is greater than max[i-1]
        if( i == 0 ){
            // add the assignment at the beginning of the list
            solutionDynamic.add(solutionDynamic.size(),assignmentArray[i]);
            System.out.println(String.format("Adding %s to the dynamic schedule",assignmentArray[i].toString()));
            return;
        }
        if(max[i].compareTo(max[i-1]) > 0) {
            // add the assignment at the beginning of the list
            solutionDynamic.add(solutionDynamic.size(),assignmentArray[i]);
            System.out.println(String.format("Adding %s to the dynamic schedule",assignmentArray[i].toString()));
            // recursive call here
            findSolutionDynamic(C[i]);
        }
        else{
            // recursive call here
            findSolutionDynamic(i-1);
        }
    }

    /**
     * {@link #max} must be filled after calling this method
     */
    private Double calculateMax(int i) {
        // if i is out of bound
        if (i < 0 || i >= assignmentArray.length) {
            return (double) 0;
        }
        else if (i == 0) {
            System.out.println("calculateMax(0): Zero");
            max[i] = assignmentArray[i].getWeight();
        }
        else {
            // if max[i] has not been calculated
            if (max[i] == null) {
                System.out.println(String.format("calculateMax(%s): Prepare", i));
                // include the current assignment and recur for compatible assignment
                double incl = assignmentArray[i].getWeight() + calculateMax(C[i]);
                // exclude the current assignment and recur for compatible assignment
                double excl = calculateMax(i - 1);
                // decide whether max[i] to be included or not
                max[i] = Math.max(incl, excl);

            } else {
                System.out.println(String.format("calculateMax(%s): Present", i));
            }
        }
        return max[i];
    }

    /**
     * {@link #solutionGreedy} must be filled after calling this method
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleGreedy() {
        // create greedy arraylist
        ArrayList<Assignment> greedy = new ArrayList<Assignment>();
        for(int i = 0; i < assignmentArray.length; i++ ){
            // selected = the index of first compatible assignment
            int selected = i;
            // add the compatible assignment
            greedy.add(greedy.size(),assignmentArray[selected]);
            System.out.println("Adding " + assignmentArray[selected].toString() + " to the greedy schedule");
            // find the first compatible assignment
            for (int next = selected +1; next < assignmentArray.length; next++) {
                if(assignmentArray[selected].getFinishTime().compareTo(assignmentArray[next].getStartTime()) <= 0){
                    break;
                }
                i = next;
            }

        }
        return greedy;
    }
}

