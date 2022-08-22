package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Sheep extends Herbivore {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public Sheep (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 70;
        this.speed = 3;
        this.hanger = 15;
        this.sex = random.nextInt(2);
    }

    public Sheep (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 70;
        this.speed = 3;
        this.hanger = 15;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(7);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(3 * Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.stop();
            }
        }
        while(!current.isInterrupted()) {
            if (age % 3 == 0 && random.nextInt(10) == 1) {
                this.multiply();
            }
            if (age >= 8) {
                if (random.nextInt(100) <= (1 + 0.02 * age)) {
                    list.remove(current);
                    current.stop();
                }
            }
            if (random.nextInt(2) == 0) {
                this.eat();
                this.eat();
            } else {
                this.move();
            }

            if ((age > 8) && (this.hanger >= 15)) {
                list.remove(current);
                current.stop();
            }

            try {
                Thread.sleep(Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.stop();
            }
            age++;
            if (hanger < 15) {
                hanger++;
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(7);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < 140) {
                    Sheep sheep = (Sheep) list.get(i);
                    if (sheep.sex != this.sex) {
                        if (random.nextInt(2) == 0) {
                            Sheep sheep1 = new Sheep(this.i1, this.j1, true);
                            list.add(sheep1);
                            sheep1.start();
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void createStartSheep () {
        int n = Info.startSheepCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(7);
            int threadCount = list.size();

            if (threadCount < 140) {
                Sheep sheep = new Sheep(i, j);
                list.add(sheep);
                sheep.start();
                n--;
            } else {
                return;
            }
        }
    }
}
