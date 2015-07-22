
/**
 *
 * @author Pranjal
 */
public class Exp_rv {

    private double s = 1111.0;
    private final double k = 16807.0;
    private final double m = 2147483647;

    public double uni_rv() {
        double uni;
        s = (k * s) % m;
        uni = s / m;
        return uni;
    }

    public double exp_rv(Double lambda) {
        double exp;
        exp = ((-1) / lambda) * (Math.log(uni_rv()));
        return exp;
    }
}
