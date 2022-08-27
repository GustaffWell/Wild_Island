package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Duck extends Herbivore {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public static int cageMaxCount = 200;

    public Duck (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 1;
        this.speed = 4;
        this.hanger = 0.15;
        this.sex = random.nextInt(2);
    }

    public Duck (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 1;
        this.speed = 4;
        this.hanger = 0.15;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(10);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(2 * Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.interrupt();
            }
        }
        while(!current.isInterrupted()) {
            if (random.nextInt(2) == 1) {
                this.multiply();
            }
            if (age >= 2) {
                if (random.nextInt(100) <= (1 + 0.2 * age)) {
                    list.remove(current);
                    current.interrupt();
                }
            }
            if (random.nextInt(2) == 0) {
                this.eat();
            } else {
                this.eatCaterpillar();
                this.move();
            }

            if ((age > 3) && (this.hanger >= 0.15)) {
                list.remove(current);
                current.interrupt();
            }

            try {
                Thread.sleep(Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.interrupt();
            }
            age++;
            if (hanger < 0.15) {
                hanger += 0.05;
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(10);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < cageMaxCount) {
                    Duck duck = (Duck) list.get(i);
                    if (duck.sex != this.sex) {
                        Duck duck1 = new Duck(this.i1, this.j1, true);
                        list.add(duck1);
                        duck1.start();
                        break;
                    }
                }
            }
        }
    }

    public synchronized void eatCaterpillar() {
        synchronized (Island.field) {
            if (random.nextInt(10) != 9) {
                if (this.hanger > 0 && Island.field[this.i1][this.j1].get(4).size() > 0) {
                    Thread thread = Island.field[this.i1][this.j1].get(4).get(0);
                    Island.field[this.i1][this.j1].get(4).remove(thread);
                    thread.interrupt();
                    this.hanger -= 0.01;
                }
            }
        }
    }

    public static void createStartDucks () {
        int n = Info.startDucksCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(10);
            int threadCount = list.size();

            if (threadCount < cageMaxCount) {
                Duck duck = new Duck(i, j);
                list.add(duck);
                duck.start();
                n--;
            } else {
                return;
            }
        }
    }
}
