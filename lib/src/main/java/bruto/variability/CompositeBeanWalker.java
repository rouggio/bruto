package bruto.variability;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CompositeBeanWalker extends ArgumentVariabilityWalker {

    private List<ArgumentVariabilityWalker> walkers;
    private Class targetClass;


    public CompositeBeanWalker(Class clazz) {
        this.walkers = new ArrayList<>();
        this.targetClass = clazz;
    }

    @Override
    public boolean hasMoreElements() {
        for (int i = walkers.size() - 1 ; i >= 0 ; i--) {
            if (walkers.get(i).hasMoreElements()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object nextElement() {
        Object[] permutation = new Object[walkers.size()];

        boolean incremented = false;

        for (int i = walkers.size() - 1 ; i >= 0 ; i--) {
            ArgumentVariabilityWalker aWalker = walkers.get(i);
            if (!incremented && aWalker.hasMoreElements()) {
                permutation[i] = aWalker.nextElement();
                incremented = true;
            } else if (incremented) {
                permutation[i] = aWalker.getCurrentElement();
            } else {
                permutation[i] = aWalker.restart();
            }
        }

        return newTargetInstance(permutation);
    }

    protected void resetLeastSignificants(int index) {
        for (int i = index; i < walkers.size() -1; i++) {
            walkers.get(i).restart();
        }
    }

    @Override
    public Object getCurrentElement() {
        Object[] permutation = new Object[walkers.size()];

        for (int i = walkers.size() - 1 ; i >= 0 ; i--) {
            permutation[i] = walkers.get(i).getCurrentElement();
        }

        return newTargetInstance(permutation);
    }

    private Object newTargetInstance(Object[] permutation) {
        try {
            Object obj = targetClass.newInstance();
            Field[] declaredFields = targetClass.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                Field declaredField = declaredFields[i];
                declaredField.setAccessible(true);
                declaredField.set(obj, permutation[i]);
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Object restart() {
        return nextElement();
    }


    public void addWalker(ArgumentVariabilityWalker walker) {
        walkers.add(walker);
    }
}
