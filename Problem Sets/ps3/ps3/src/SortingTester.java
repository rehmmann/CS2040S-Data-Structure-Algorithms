public class SortingTester {

    public static KeyValuePair[] createArray(int n){//Makes an array of size n with elements arranged randomly
        KeyValuePair[] myArr = new KeyValuePair[n];
        for(int i = 0;i < n;i++){
            myArr[i] = new KeyValuePair((int) (Math.random() * n ), i);
        }
        return myArr;
    }


    public static boolean checkSort(ISort sorter, int size) {
        //TODO: implement this
        KeyValuePair[] randomArr = createArray(size);
//        KeyValuePair[] randomArr = SortTestExample.descendingArray(size);
        sorter.sort(randomArr);
        for(int i = 0;i < size-1;i++){
            if(randomArr[i].getKey() > randomArr[i+1].getKey()){
                return false;
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        KeyValuePair[] randomArr = createArray(size);
        sorter.sort(randomArr);
        for(int i = 0;i < randomArr.length-1;i++){
            KeyValuePair current = randomArr[i];
            KeyValuePair next = randomArr[i+1];
            if(current.getKey() == next.getKey() && current.getValue() >= next.getValue()){//same key but small value first return false
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        //TODO: implement this
        ISort sortingObject = new SorterC();
//        System.out.println(isStable(new SorterA(),100000));
//        System.out.println(isStable(new SorterB(),100000));
        System.out.println(checkSort(new SorterC(),8000));//returns false
//        System.out.println(isStable(new SorterD(),100000));
//        System.out.println(isStable(new SorterE(),100000));
//        System.out.println(isStable(new SorterF(),10000));
    }
}
