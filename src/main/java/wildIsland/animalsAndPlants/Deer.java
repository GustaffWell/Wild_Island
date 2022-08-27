package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Deer extends Herbivore {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public static int cageMaxCount = 20;

    public Deer (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 300;
        this.speed = 4;
        this.hanger = 50;
        this.sex = random.nextInt(2);
    }

    public Deer (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 300;
        this.speed = 4;
        this.hanger = 50;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> deerList = Island.field[this.i1][this.j1].get(2);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(4 * Info.iterationTime);
            } catch (InterruptedException e) {
                deerList.remove(current);
                current.interrupt();
            }
        }
        while(!current.isInterrupted()) {
            if (age % 4 == 0 && random.nextInt(10) == 1) {
                this.multiply();
            }
            if (age >= 20) {
                if (random.nextInt(1, 100) <= (1 + 0.2 * age)) {
                    deerList.remove(current);
                    current.interrupt();
                }
            }
            if (random.nextInt(2) == 0) {
                    this.eat();
                    this.eat();
            } else {
                this.move();
            }

            if ((age > 10) && (this.hanger >= 50)) {
                deerList.remove(current);
                current.interrupt();
            }

            try {
                Thread.sleep(Info.iterationTime);
            } catch (InterruptedException e) {
                deerList.remove(current);
                current.interrupt();
            }
            age++;
            if (hanger < 50) {
                hanger++;
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> deerList = Island.field[this.i1][this.j1].get(2);

            AtomicInteger cageCount = new AtomicInteger(deerList.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < cageMaxCount) {
                    Deer deer = (Deer) deerList.get(i);
                    if (deer.sex != this.sex) {
                        if (random.nextInt(2) == 0) {
                            Deer deer1 = new Deer(this.i1, this.j1, true);
                            deerList.add(deer1);
                            deer1.start();
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void createStartDeer () {
        int n = Info.startDeerCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> deerList = Island.field[i][j].get(2);
            int threadCount = deerList.size();

            if (threadCount < cageMaxCount) {
                Deer deer = new Deer(i, j);
                deerList.add(deer);
                deer.start();
                n--;
            } else {
                return;
            }
        }
    }
}
