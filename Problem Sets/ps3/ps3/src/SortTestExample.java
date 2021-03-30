/**
 * This class is a simple example for how to use the sorting classes.
 * It sorts three numbers, and measures how long it takes.
 */
public class SortTestExample {

    public static KeyValuePair[] bunchOfOnes(int size) {
        KeyValuePair[] myArr = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            myArr[i] = new KeyValuePair(1, i);
        }
        return myArr;
    }

    public static KeyValuePair[] ascendingArray(int size) {
        KeyValuePair[] myArr = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            myArr[i] = new KeyValuePair(i, i);
        }
        return myArr;
    }

    public static KeyValuePair[] descendingArray(int size) {
        KeyValuePair[] myArr = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            myArr[i] = new KeyValuePair(size - i, i);
        }
        return myArr;
    }

    public static void testing(int numberOfArrays, int elements, ISort sorter) {
        ISort sortingObject = sorter;
        KeyValuePair[][] myArr = new KeyValuePair[numberOfArrays][elements];
        double total = 0;
        for (int i = 0; i < numberOfArrays; i++) {
            StopWatch watch1 = new StopWatch();
            myArr[i] = SortingTester.createArray(elements);//Call different methods here to produce diff kind of arrays
            watch1.start();
            sortingObject.sort(myArr[i]);
            watch1.stop();
//            System.out.print(watch1.getTime() + ",");
            total += watch1.getTime();
        }
        System.out.print(total/numberOfArrays);//Average time taken to sort a given number of elements
        System.out.println("");
    }


    public static void main(String[] args) {

        System.out.println("----Sorter A----");
        testing(100, 500, new SorterA());
        testing(100, 1000, new SorterA());
        testing(100, 2000, new SorterA());
        testing(100, 4000, new SorterA());
        testing(100, 8000, new SorterA());
        testing(100, 16000, new SorterA());

        System.out.println("----Sorter B----");
        testing(10, 500, new SorterB());
        testing(10, 1000, new SorterB());
        testing(10, 2000, new SorterB());
        testing(10, 4000, new SorterB());
        testing(10, 8000, new SorterB());
        testing(10, 16000, new SorterB());

        System.out.println("----Sorter D----");
        testing(100, 500, new SorterD());
        testing(100, 1000, new SorterD());
        testing(100, 2000, new SorterD());
        testing(100, 4000, new SorterD());
        testing(100, 8000, new SorterD());
        testing(100, 16000, new SorterD());

        System.out.println("----Sorter E----");
        testing(100, 500, new SorterE());
        testing(100, 1000, new SorterE());
        testing(100, 2000, new SorterE());
        testing(100, 4000, new SorterE());
        testing(100, 8000, new SorterE());
        testing(100, 16000, new SorterE());

       System.out.println("----Sorter F----");
        testing(100, 500, new SorterF());
        testing(100, 1000, new SorterF());
        testing(100, 2000, new SorterF());
        testing(100, 4000, new SorterF());
        testing(100, 8000, new SorterF());
        testing(100, 16000, new SorterF());

//        testing(10, 1000, new SorterB());
//        testing(10, 1000, new SorterD());
//        testing(10, 1000, new SorterE());
//        testing(10, 1000, new SorterF());
        // Do the sorting
        // Create three key value pairs
//        KeyValuePair[] testArray = new KeyValuePair[3];
//        testArray[0] = new KeyValuePair(10, 20);
//        testArray[1] = new KeyValuePair(5, 20);
//        testArray[2] = new KeyValuePair(8, 20);
//        KeyValuePair[] myArr1 = SortingTester.createArray(1000);
//        KeyValuePair[] myArr2 = SortingTester.createArray(3000);
//        KeyValuePair[] myArr3 = SortingTester.createArray(5000);
//        KeyValuePair[] myArr4 = SortingTester.createArray(7000);
//        KeyValuePair[] myArr5 = SortingTester.createArray(9000);
//        // Create a stopwatch
//        StopWatch watch1 = new StopWatch();
//        StopWatch watch2 = new StopWatch();
////        watch1.start();
//        sortingObject.sort(myArr1);
//        watch1.stop();
//        System.out.println("Time: " + watch1.getTime());
//
//        watch1.reset();
//        watch1.start();
//        sortingObject.sort(myArr2);
//        watch1.stop();
//        System.out.println("Time: " + watch1.getTime());
    }
}
