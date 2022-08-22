package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Boar extends Herbivore {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public Boar (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 400;
        this.speed = 2;
        this.hanger = 50;
        this.sex = random.nextInt(2);
    }

    public Boar (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 400;
        this.speed = 2;
        this.hanger = 50;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(8);
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
            if (random.nextInt(2) == 1) {
                this.multiply();
            }
            if (age >= 5) {
                if (random.nextInt(100) <= (1 + 0.1 * age)) {
                    list.remove(current);
                    current.stop();
                }
            }
            if (random.nextInt(2) == 0) {
                this.eat();
            } else {
                this.eatMouse();
                this.eatCaterpillar();
                this.move();
            }

            if ((age > 5) && (this.hanger >= 50)) {
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
            if (hanger < 50) {
                hanger += 1;
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(8);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < 50) {
                    Boar boar = (Boar) list.get(i);
                    if (boar.sex != this.sex) {
                        Boar boar1 = new Boar(this.i1, this.j1, true);
                        list.add(boar1);
                        boar1.start();
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
                    this.hanger -= 0.01;
                }
            }
        }
    }

    public synchronized void eatMouse() {
        synchronized (Island.field) {
            if (random.nextInt(2) != 1) {
                if (this.hanger > 0 && Island.field[this.i1][this.j1].get(5).size() > 0) {
                    Thread thread = Island.field[this.i1][this.j1].get(5).get(0);
                    Island.field[this.i1][this.j1].get(5).remove(thread);
                    this.hanger -= 0.05;
                }
            }
        }
    }

    public static void createStartBoars () {
        int n = Info.startBoarsCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(8);
            int threadCount = list.size();

            if (threadCount < 50) {
                Boar boar = new Boar(i, j);
                list.add(boar);
                boar.start();
                n--;
            } else {
                return;
            }
        }
    }
}

