package ex1.trafficexamples;

import ex1.simulation.SimulationSingleton;
import ex1.simulation.SimulationView;

import java.util.concurrent.Semaphore;

/**
 * Main class to create and run a simulation
 */
public class RunTrafficSimulation {


    public class SemaphoreExample {
        static class Worker implements Runnable {
            private final Semaphore semaphore;

            Worker(Semaphore semaphore) {
                this.semaphore = semaphore;
            }

            @Override
            public void run() {
                try {
                    // Acquisizione di una risorsa dal semaforo
                    semaphore.acquire();

                    // Simulazione di un'attività che utilizza la risorsa
                    System.out.println(Thread.currentThread().getName() + " acquisisce la risorsa");
//                    Thread.sleep(2000); // Simulazione di un'attività che richiede del tempo

                    // Rilascio della risorsa
                    System.out.println(Thread.currentThread().getName() + " rilascia la risorsa");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(final String[] args) {

//		final var simulation = new TrafficSimulationSingleRoadTwoCars();
//		 var simulation = new TrafficSimulationSingleRoadSeveralCars();
//		 var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
//		 var simulation = new TrafficSimulationWithCrossRoads();

        // Creazione di un semaforo con una capacità iniziale di 2
//        Semaphore semaphore = new Semaphore(5);

        // Definizione di due thread che acquisiscono risorse dal semaforo
//        Thread thread1 = new Thread(new SemaphoreExample.Worker(semaphore));
//        Thread thread2 = new Thread(new SemaphoreExample.Worker(semaphore));
//        Thread thread3 = new Thread(new SemaphoreExample.Worker(semaphore));
//        Thread thread4 = new Thread(new SemaphoreExample.Worker(semaphore));
//        Thread thread5 = new Thread(new SemaphoreExample.Worker(semaphore));
//
//        // Avvio dei thread
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        thread5.start();

		final SimulationView view = SimulationSingleton.simulationView;
		SimulationSingleton.simulation.addViewListener(view);
		view.setupCommandsSimulation(SimulationSingleton.simulation);
    }
}
