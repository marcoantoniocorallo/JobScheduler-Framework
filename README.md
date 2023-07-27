# JobScheduler-Framework
_Advanced Programming_ assignment that requires to implement a simple software framework providing the functionalities of a job scheduler, but ignoring the aspects of parallelism and distribution. More precisely, the framework includes an **emitter** of jobs, a **compute** phase executing the jobs, a **collect** stage grouping them, and an **output** action printing the results in a suitable format. 
As a proof of concept, a simple working instance of the framework has been implemented as well.

The framework is generic and implements the concept of **Inversion of Control** through the *Template Method* design pattern.
The framework code is into the class `JobScheduler`, that provides the template method (`main`), two frozen spots (`collect`, `compute`) and two hot spots to redefine in concrete classes (`emit`, `output`).
The jobs to compute must be instances of the abstract class `AJob.java`, containing the abstract method `execute()`.

As proof of concept, a concrete class that use this framework has been provided in the file `AnagramsCounterScheduler`, that implements the hot spots. This implementation schedules and computes `AnagramsCounter` jobs.

#### To use the framework, just pass the concrete class name as argument.
