package sample;

public class SimpleBean {

    public float sum(Integer factorA, Integer factorB) {
        if (factorA == null) factorA = 0;
        if (factorB == null) factorB = 0;
        return factorA + factorB;
    }

}
