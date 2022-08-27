package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Boa extends Predator{
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public static int cageMaxCount = 30;

    public Boa(int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 15;
        this.speed = 1;
        this.hanger = 3;
        this.sex = random.nextInt(2);
    }

    public Boa(int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 15;
        this.speed = 1;
        this.hanger = 3;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(12);
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
            if (age % 2 == 0 && random.nextInt(10) == 1) {
                this.multiply();
            }
            if (age >= 10) {
                if (random.nextInt(100) <= (1 + 0.01 * age)) {
                    list.remove(current);
                    current.interrupt();
                }
            }

            this.eat();
            this.move();

            if ((age > 10) && (this.hanger >= 3)) {
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
            if (hanger < 3) {
                hanger += 0.5;
            }
        }
    }

    @Override
    public void eat() {
        AtomicInteger fate = new AtomicInteger(random.nextInt(4));
        switch (fate.get()) {
            case 0:
                if (random.nextInt(100) < 15) {
                    eatFox();
                }
                break;
            case 1:
                if (random.nextInt(100) < 20) {
                    eatRabbit();
                }
                break;
            case 2:
                if (random.nextInt(100) < 40) {
                    eatMouse();
                }
                break;
            case 3:
                if (random.nextInt(100) < 10) {
                    eatDuck();
                }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(12);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < cageMaxCount) {
                    Boa boa = (Boa) list.get(i);
                    if (boa.sex != this.sex) {
                        Boa boa1 = new Boa(this.i1, this.j1, true);
                        list.add(boa1);
                        boa1.start();
                        break;
                    }
                }
            }
        }
    }


    public static void createStartBoas() {
        int n = Info.startBoasCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(12);
            int threadCount = list.size();

            if (threadCount < cageMaxCount) {
                Boa boa = new Boa(i, j);
                list.add(boa);
                boa.start();
                n--;
            } else {
                return;
            }
        }
    }
}
