///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////
import java.util.Arrays;
/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
//    ///////////////////////////////////
//    // Create your class variables here
//    ///////////////////////////////////
//    // TODO:
    public int size;
    public int tap;
    public int[] register;

//
//    ///////////////////////////////////
//    // Create your constructor here:
//    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        this.size = size;
        this.tap= tap;
        this.register = new int[size];
    }

    public int[] getRegister(){
        return this.register;
    }
//    ///////////////////////////////////
//    // Create your class methods here:
//    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:Creates the set of bits in the Register
     */
    @Override
    public void setSeed(int[] seed) {
        this.register = seed;
    }
//
//    /**
//     * shift
//     * @return
//     * Description:
//     */
    @Override
    public int shift() {
//        System.out.println(Arrays.toString(this.register));
        int feedback_bit = register[size-1] ^ register[tap];//Most sig bit ^ tap
        int[] tmp = register.clone();
        for (int i = 1; i < register.length; i++) {//i represents the index of the new position
            register[i] = tmp[i - 1];
        }
        register[0] = feedback_bit;
//        System.out.println(Arrays.toString(this.register));
        return feedback_bit;
    }

//
//    /**
//     * generate
//     * @param k
//     * @return
//     * Description:
//     */
    @Override
    public int generate(int k) {
        int[] myArr = new int[k];
        for(int i = 0;i < k;i++){//calls shift() k times & stores bits in myArr
            int temp = this.shift();
            myArr[i] = temp;
        }
        return this.toBinary(myArr);
    }

//    /**
//     * Returns the integer representation for a binary int array.
//     * @param array
//     * @return
//     */
    private int toBinary(int[] array) {//must be private
        int res = 0;
        for(int i = 0; i < array.length; i++){
            res += Math.pow(2,array.length-1-i) * array[i];
        }
        return res;
    }
}
