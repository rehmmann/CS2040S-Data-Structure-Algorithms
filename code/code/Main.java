import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    static int MysteryFunction(int argA, int argB) {

        int c = 1;
        int d = argA;
        int e = argB;
        while (e > 0) {
            if (2 * (e / 2) != e) {
                c = c * d;
            }
            d = d * d;
            e = e / 2;
        }
        return c;
    }

    public static void main(String args[]) {
        ShiftRegister r = new ShiftRegister(4, 1);
        int[] seed = {1,0,0,0,1,1,0};
        r.setSeed(seed);
        int a = r.shift();//0
        int b = r.generate(4);//8

        System.out.println(a);
    }
}