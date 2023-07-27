/**
 * Advanced Programming - Assignment 2.2
 * JobScheduler Framework: this class implements the frozen spots of the framework and the template method.
 * It provides the main method of the system, that requires the name of the concrete Job Scheduler class as parameter.
 * i.e. javac * && java JobScheduler AnagramsCounterScheduler
 */

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

public abstract class JobScheduler<K,V>{

    /**
     * Template method
     * It is the framework's main method:
     * it instantiates an object of the concrete JobScheduler passed as argument and start the pipeline
     * @param args: the concrete JobScheduler class name;
     */
    final public static void main(String[] args) {
        JobScheduler js = null;
        Class c = null;

        if (args.length != 1) {
            System.out.println("Usage: specify the class name of the concrete JobScheduler to instantiate it.");
            return;
        }

        // instantiate the concrete JobScheduler, if legal
        try {
            c = Class.forName(args[0]);
            if (!JobScheduler.class.isAssignableFrom(c))
                throw new IllegalArgumentException(c.getName() + " doesn't extend JobScheduler.");
            js = (JobScheduler) c.getDeclaredConstructor().newInstance();
        } catch (IllegalArgumentException iae){
            System.out.println(iae.getMessage());
            return;
        } catch( ClassNotFoundException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            System.out.println("An error occurs during the instantiation of "+args[0]);
            e.printStackTrace();
            return;
        }

        System.out.println(c.getName()+" in execution.");
        js.output(js.collect(js.compute(js.emit())));

    }

    /**
     * Hot Spot: primitive operation that must be overriden by subclasses
     * @return a stream of jobs to pass to the compute phase
     */
    abstract protected Stream<AJob<K,V>> emit();

    /**
     * Frozen Spot: concrete operation
     * executes the jobs received by invoking execute on them
     * @param jobs: a stream of jobs resulting from emit
     * @return a stream of <key, value> pairs obtained by concatenating the output of the jobs
     */
    private Stream<Pair<K,V>> compute (Stream<AJob<K,V>> jobs){
        return jobs.flatMap(AJob::execute);
    }

    /**
     * Frozen Spot: concrete operation
     * groups all the pairs with the same keys in a single pair,
     * having the same key and the list of all values;
     * @param pairs: stream of <key, value> pairs resulting from compute
     * @return a stream of <key, list<value> > pairs: each key is associated to many values
     */
    private Stream<Pair<K,List<V>>> collect (Stream<Pair<K,V>> pairs){
        return pairs
                .collect(
                    Collectors.groupingBy(
                        Pair::getKey,
                        Collectors.mapping(
                            Pair::getValue,
                            Collectors.toList()
                        )
                    )
                )
                .entrySet()
                .stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue() ) );
    }

    /**
     * Hot Spot: primitive operation that must be overriden by subclasses
     * prints the result of the collect phase in a suitable format
     * @param stream: a stream of pairs resulting from collect
     */
    abstract protected void output(Stream<Pair<K,List<V>>> stream);

}
