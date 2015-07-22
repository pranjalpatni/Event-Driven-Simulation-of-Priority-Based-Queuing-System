
import java.util.TreeMap;

/**
 *
 * @author Pranjal
 */
public class QueuingSystem {

    private String hARR, lARR, hDEP, lDEP;

    public void queuingSystem(double PH, double PL, double r11L, double r12L, double r2d, double r21, double mu1, double mu2, double lambda) {
        /*Declaring Variables for finding metrices*/
        double clock = 0.0;              
        double prev_clock;
        double lambdaL = PL * lambda;
        double lambdaH = PH * lambda;
        int h1ARR, h2ARR, l1ARR, l2ARR;
        int exit = 0;
        int NH1 = 0;
        int NH2 = 0;
        int NL1 = 0;
        int NL2 = 0;
        int NArr1 = 0;
        int NArr2 = 0;
        int NArrL1 = 0;
        int NArrL2 = 0;
        int NArrH1 = 0;
        int NArrH2 = 0;
        int NDepL1 = 0;
        int NDepH1 = 0;
        int NDep = 0;
        double EL1 = 0.0;
        double EL2 = 0.0;
        double EH1 = 0.0;
        double EH2 = 0.0;
        TreeMap<Double, String> EList = new TreeMap<>();
        Exp_rv rand = new Exp_rv();
        Event currentEvent;
        EList.put(rand.exp_rv(lambdaH), "ARR2H0");
        EList.put(rand.exp_rv(lambdaL), "ARR1L0");
        while (exit != 1) {                 //Break condition : when NDepartures = 500000
            currentEvent = returnEvent(EList);
            prev_clock = clock;
            clock = currentEvent.value;
            EL1 = EL1 + NL1 * (clock - prev_clock);         //Updating area under the curve for each priority in each queue
            EL2 = EL2 + NL2 * (clock - prev_clock);
            EH1 = EH1 + NH1 * (clock - prev_clock);
            EH2 = EH2 + NH2 * (clock - prev_clock);
            switch (currentEvent.type) {
                case "ARR1L":                               //Queue 1, Priority Low, Arrival event
                    if (currentEvent.transfer == 0) {
                        NArr1++;
                        NArrL1++;
                        NL1++;
                        EList.put(clock + rand.exp_rv(lambdaL), "ARR1L0");
                    } else {
                        NArr1++;
                        NArrL1++;
                        NL1++;
                    }
                    if ((NL1 + NH1) <= 1) {
                        EList.put(clock + rand.exp_rv(mu1), "DEP1L");
                    }
                    break;
                case "ARR2L":                               //Queue 2, Priority Low, Arrival event
                    NArr2++;
                    NArrL2++;
                    NL2++;
                    if ((NH2 + NL2) <= 1) {
                        EList.put(clock + rand.exp_rv(mu2), "DEP2L");
                    }
                    break;
                case "ARR1H":                               //Queue 1, Priority High, Arrival event
                    NArr1++;
                    NArrH1++;
                    NH1++;
                    if ((NL1 + NH1) <= 1) {
                        EList.put(clock + rand.exp_rv(mu1), "DEP1H");
                    }
                    break;
                case "ARR2H":                                   //Queue 2, Priority High, Arrival event
                    NArr2++;
                    NArrH2++;
                    NH2++;
                    if (currentEvent.transfer != 1) {
                        EList.put(clock + rand.exp_rv(lambdaH), "ARR2H0");
                    }
                    if ((NH2 + NL2) <= 1) {
                        EList.put(clock + rand.exp_rv(mu2), "DEP2H");
                    }
                    break;
                case "DEP1L":                               //Queue 1, Priority Low, Departure event
                    currentEvent.transfer = 1;
                    if (rand.uni_rv() < r12L) {
                        NL1--;
                        NDepL1++;
                        EList.put(currentEvent.value, "ARR2L1");
                    } else {
                        NL1--;
                        NDepL1++;
                        EList.put(currentEvent.value, "ARR1L1");
                    }
                    if (NH1 > 0) {
                        EList.put(clock + rand.exp_rv(mu1), "DEP1H");
                    } else if (NL1 > 0) {
                        EList.put(clock + rand.exp_rv(mu1), "DEP1L");
                    }
                    break;
                case "DEP2L":                               //Queue 2, Priority Low, Departure event
                    NL2--;
                    if (rand.uni_rv() < r21) {
                        currentEvent.transfer = 1;
                        EList.put(currentEvent.value, "ARR1L1");
                    } else {
                        NDep++;
                    }
                    if (NH2 > 0) {
                        EList.put(clock + rand.exp_rv(mu2), "DEP2H");
                    } else if (NL2 > 0) {
                        EList.put(clock + rand.exp_rv(mu2), "DEP2L");
                    }
                    break;
                case "DEP1H":                               //Queue 1, Priority High, Departure event
                    currentEvent.transfer = 1;
                    NH1--;
                    NDepH1++;
                    EList.put(currentEvent.value, "ARR2H1");
                    if (NH1 > 0) {
                        EList.put(clock + rand.exp_rv(mu1), "DEP1H");
                    } else if (NL1 > 0) {
                        EList.put(clock + rand.exp_rv(mu1), "DEP1L");
                    }
                    break;
                case "DEP2H":                               //Queue 2, Priority High, Departure event
                    NH2--;
                    if (rand.uni_rv() < r21) {
                        currentEvent.transfer = 1;
                        EList.put(currentEvent.value, "ARR1H1");
                    } else {
                        NDep++;
                    }
                    if (NH2 > 0) {
                        EList.put(clock + rand.exp_rv(mu2), "DEP2H");
                    } else if (NL2 > 0) {
                        EList.put(clock + rand.exp_rv(mu2), "DEP2L");
                    }
                    break;
            }
            if (NDep == 500000) {
                exit = 1;
            }
        }
        analysis(EL1, EL2, EH1, EH2, NL1, NL2, NH1, NH2, NArrL1, NArrL2, NArrH1, NArrH2, clock);
    }
/*This method returns the next event*/
    private Event returnEvent(TreeMap<Double, String> EList) {
        Event eventRet = new Event();
        double key;
        String type;
        key = EList.firstKey();
        type = EList.get(key);
        if (type.charAt(0) == 'A') {
            if (type.contains("0")) {
                eventRet.transfer = 0;
            } else {
                eventRet.transfer = 1;
            }
        }
        eventRet.type = type.substring(0, 5);
        eventRet.value = key;
        EList.remove(key);
        return eventRet;
    }
/*For calculating the metrices*/
    private void analysis(double EL1, double EL2, double EH1, double EH2, int NL1, int NL2, int NH1, int NH2, int NArrL1, int NArrL2, int NArrH1, int NArrH2, double clock) {
        double ENL1, ENL2, ENH1, ENH2;
        double YL1, YL2, YH1, YH2;
        double ETL1, ETH1;
        ENL1 = EL1 / clock;
        ENL2 = EL2 / clock;
        ENH1 = EH1 / clock;
        ENH2 = EH2 / clock;
        YL1 = NArrL1 / clock;
        YL2 = NArrL2 / clock;
        YH1 = NArrH1 / clock;
        YH2 = NArrH2 / clock;
        ETL1 = EL1 / NArrL1;
        ETH1 = EH1 / NArrH1;
        System.out.println("Expected number of customers:");
        System.out.println("Expected number of low priority customers in queue 1 (ENL1): " + ENL1);
        System.out.println("Expected number of low priority customers in queue 2 (ENL2): " + ENL2);
        System.out.println("Expected number of high priority customers in queue 1 (ENH1): " + ENH1);
        System.out.println("Expected number of high priority customers in queue 2 (ENH2): " + ENH2);
        System.out.println();
        System.out.println("Throughput:");
        System.out.println("Throughput of low priority customer, queue 1 (YL1): " + YL1);
        System.out.println("Throughput of low priority customer, queue 2 (YL2): " + YL2);
        System.out.println("Throughput of high priority customer, queue 1 (YH1): " + YH1);
        System.out.println("Throughput of high priority customer, queue 2 (YH2): " + YH2);
        System.out.println();
        System.out.println("Expected time (queue 1):");
        System.out.println("Expected time spent by low priority customer, queue 1 (ETL1): " + ETL1);
        System.out.println("Expected time spent by high priority customer, queue 1 (ETH1): " + ETH1);
    }
}
