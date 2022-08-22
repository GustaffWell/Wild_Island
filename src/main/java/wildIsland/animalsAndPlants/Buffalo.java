package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Buffalo extends Herbivore {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public Buffalo (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 700;
        this.speed = 3;
        this.hanger = 100;
        this.sex = random.nextInt(2);
    }

    public Buffalo (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 700;
        this.speed = 3;
        this.hanger = 100;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(9);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(5 * Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.stop();
            }
        }
        while(!current.isInterrupted()) {
            if (age % 5 == 0 && random.nextInt(10) == 1) {
                this.multiply();
            }
            if (age >= 15) {
                if (random.nextInt(100) <= (1 + 0.1 * age)) {
                    list.remove(current);
                    current.stop();
                }
            }
            if (random.nextInt(2) == 0) {
                this.eat();
                this.eat();
                this.eat();
                this.eat();
            } else {
                this.move();
            }

            if ((age > 10) && (this.hanger >= 100)) {
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
            if (hanger < 100) {
                hanger++;
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(9);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < 10) {
                    Buffalo buffalo = (Buffalo) list.get(i);
                    if (buffalo.sex != this.sex) {
                        if (random.nextInt(2) == 0) {
                            Buffalo buffalo1 = new Buffalo(this.i1, this.j1, true);
                            list.add(buffalo1);
                            buffalo1.start();
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void createStartBuffaloes () {
        int n = Info.startBuffaloesCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(9);
            int threadCount = list.size();

            if (threadCount < 10) {
                Buffalo buffalo = new Buffalo(i, j);
                list.add(buffalo);
                buffalo.start();
                n--;
            } else {
                return;
            }
        }
    }
}
