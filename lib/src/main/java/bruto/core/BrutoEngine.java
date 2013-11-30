package bruto.core;

import bruto.Result;
import bruto.TestResult;
import bruto.result.BeanExplorationResults;
import bruto.result.MethodExplorationResults;
import bruto.variability.ArgumentVariabilityProvider;
import bruto.variability.ArgumentVariabilityWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class BrutoEngine {

    private static final Logger log = LoggerFactory.getLogger(BrutoEngine.class);


    private ArgumentVariabilityProvider argumentVariabilityProvider;

    public BrutoEngine() {
        argumentVariabilityProvider = new ArgumentVariabilityProvider();
    }

    public MethodExplorationResults exploreMethod(Method method, Object object, Set<TruthFormula> truthFormulas) {
        log.debug("exploring method {} of class {}", method.getName(), object.getClass().getName());
        MethodExplorationResults methodResults = new MethodExplorationResults(method);

        ArgumentVariabilityWalker[] argumentsVariabilityWalkers = buildArgumentsVariabilityWalkers(method);

        Set<TruthFormula> methodTruthFormulas = new HashSet<>();
        for (TruthFormula truthFormula : truthFormulas) {
            if (truthFormula.applicable(method)) {
                methodTruthFormulas.add(truthFormula);
            }
        }
        methodResults.setApplicableFormulas(methodTruthFormulas.size());

        PermutationEnumeration argumentPermutationEnumeration = new PermutationEnumeration(argumentsVariabilityWalkers);
        long permutationsCount = 0;
        while (argumentPermutationEnumeration.hasMoreElements()) {
            ArgumentSet argumentSet = argumentPermutationEnumeration.nextElement();
            permutationsCount++;
            try {
                log.debug("invoking class {} method {}", object.getClass().getName(), method.getName());
                Object invokeResult = method.invoke(object, argumentSet.getArguments());
                inspectOutcome(method, argumentSet, invokeResult, methodTruthFormulas, methodResults);
            } catch (InvocationTargetException ite) {
                inspectOutcome(method, argumentSet, ite.getTargetException(), methodTruthFormulas, methodResults);
            } catch (Throwable t) {
                inspectOutcome(method, argumentSet, t, methodTruthFormulas, methodResults);
            }

        }
        methodResults.setPermutations(permutationsCount);
        return methodResults;
    }

    private ArgumentVariabilityWalker[] buildArgumentsVariabilityWalkers(Method method) {
        ArgumentVariabilityWalker[] argumentsVariabilityWalkers = new ArgumentVariabilityWalker[method.getGenericParameterTypes().length];
        for (int j = 0; j < method.getGenericParameterTypes().length; j++) {
            Type type = method.getGenericParameterTypes()[j];
            log.debug("building arguments variability for method {} - position {} - type {}", method.getName(), j, type);
            argumentsVariabilityWalkers[j] = argumentVariabilityProvider.buildArgumentVariability(type);
        }
        return argumentsVariabilityWalkers;
    }

    // inspects the input, the output or the exception, the formulas and determines whether there were any truth violations
    private void inspectOutcome(Method method, ArgumentSet argumentSet, Object outcome, Set<TruthFormula> truthFormulas, MethodExplorationResults results) {
        boolean formulasMet = false;
        for (TruthFormula truthFormula : truthFormulas) {
            if (truthFormula.applicable(method)) {
                Class genericType = (Class) ((ParameterizedTypeImpl) truthFormula.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                if (genericType.isAssignableFrom(outcome.getClass())) {
                    formulasMet |= true;
                    TestResult result = truthFormula.verify(outcome, argumentSet.getArguments());
                    if (result.getResult().equals(TestResult.Result.FAILURE)) {
                        results.addViolation(new Result(argumentSet, result));
                    } else {
                        results.addSuccess(new Result(argumentSet, result));
                    }
                }
            }
        }
        if (!formulasMet) {
            results.incrementUnparsedExecutions();
        }
    }

    public BeanExplorationResults exploreBean(Object bean, Set<TruthFormula> truthFormulas) {
        log.debug("exploring bean of class {}", bean.getClass().getName());

        BeanExplorationResults results = new BeanExplorationResults(bean.getClass());

        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            results.add(exploreMethod(method, bean, truthFormulas));
        }

        return results;
    }

}
