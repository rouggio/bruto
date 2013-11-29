package bruto.variability;

public class DiscreteSetWalker<T> extends ArgumentVariabilityWalker {

    private T[] variants;
    private Integer variantIndex = -1;

    public DiscreteSetWalker(T[] variants) {
        this.variants = variants;
    }

    @Override
    public boolean hasMoreElements() {
        return variantIndex < variants.length - 1;
    }

    @Override
    public T nextElement() {
        return variants[++variantIndex];
    }

    @Override
    public T getCurrentElement() {
        if (variantIndex == -1) variantIndex = 0;
        return variants[variantIndex];
    }

    @Override
    public T restart() {
        variantIndex = -1;
        return nextElement();
    }


}
