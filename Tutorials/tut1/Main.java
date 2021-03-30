public class Main {

    public static int searchMax(int[] dataArray){
        if(dataArray.length == 1){
            return dataArray[0];
        }
        else {
            return dataArray[1];
        }
    }

    public static void main(String[] args) {
        int[] test1 = {1,3,5,7,9,11,10,8,6,4};

        System.out.println(searchMax(test1));

//        System.out.println(i);
//        System.out.println(j);
//        System.out.println(k);


    }

}
