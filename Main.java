
/**
 *
 * @author Pranjal
 */
public class Main {

    public static void main(String[] args) {
        QueuingSystem system = new QueuingSystem();
        for (double i = 1.0; i <= 10.0; i++) {
            System.out.println("\t\t\t\tFor lambda = "+i);
            system.queuingSystem(.25, .75, .2, .8, .5, .5, 25, 40, i);			//Input values
            System.out.println("**************************************************************************************");
        }
    }
}
