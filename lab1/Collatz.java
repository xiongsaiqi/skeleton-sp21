/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {

    /** DO it and when n get even it returns n/2, when n get odd, it returns 3n + 1 */
    public static int nextNumber(int n) {
        return (n % 2 == 0) ? n / 2 : 3 * n + 1;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

