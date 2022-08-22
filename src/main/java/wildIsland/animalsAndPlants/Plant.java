package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Plant extends Organic {

    private boolean isNewPlant = false;
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public int mass = 1;
    public int i1;
    public int j1;

    public Plant(int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
    }

    private Plant(int i1, int j1, boolean isNewPlant) {
        this.i1 = i1;
        this.j1 = j1;
        this.isNewPlant = isNewPlant;
    }

    @Override
    public void run() {
        ArrayList<Thread> plantsList = Island.field[this.i1][this.j1].get(0);
        Thread current = Thread.currentThread();
        int age = 1;
        if (isNewPlant) {
            try {
                Thread.sleep(3 * Info.iterationTime);
            } catch (InterruptedException e) {
                plantsList.remove(current);
                current.stop();
            }
        }
        while(!current.isInterrupted()) {

            this.multiply();

            if (age >= 10) {
                if (random.nextInt(100) <= (1 + 0.1 * age)) {   // Появляющаяся с возрастом и возрастающая вероятность умереть
                    plantsList.remove(current);
                    current.stop();
                }
            }
            try {
                Thread.sleep(Info.iterationTime);
            } catch (InterruptedException e) {
                plantsList.remove(current);
                current.stop();
            }
            age++;
        }
    }
    @Override
    public void multiply() {
        int i = random.nextInt(0, Island.width);
        int j = random.nextInt(0, Island.length);

        synchronized (Island.field) {
            for (int k = 0; k < 2; k++) {
                ArrayList<Thread> plantsList = Island.field[i][j].get(0);
                AtomicInteger threadCount = new AtomicInteger(plantsList.size());
                if (threadCount.get() < 200) {
                    Plant plant = new Plant(i, j, true);
                    plantsList.add(plant);
                    plant.start();
                }
            }
        }
    }
    public static void createStartPlants () {
        int n = Info.startPlantsCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> plantsList = Island.field[i][j].get(0);
            int threadCount = plantsList.size();

            if (threadCount < 200) {
                Plant plant = new Plant(i, j);
                plantsList.add(plant);
                plant.start();
                n--;
            } else {
                return;
            }
        }
    }
}
