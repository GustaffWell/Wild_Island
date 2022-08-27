package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Wolf extends Predator {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public static int cageMaxCount = 30;

    public Wolf(int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 50;
        this.speed = 3;
        this.hanger = 8;
        this.sex = random.nextInt(2);
    }

    public Wolf(int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 50;
        this.speed = 3;
        this.hanger = 8;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(11);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(3 * Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.interrupt();
            }
        }
        while(!current.isInterrupted()) {
            if (age % 3 == 0 && random.nextInt(10) == 1) {
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

            if ((age > 10) && (this.hanger >= 8)) {
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
            if (hanger < 8) {
                hanger++;
            }
        }
    }

    @Override
    public void eat() {
        AtomicInteger fate = new AtomicInteger(random.nextInt(9));
        switch (fate.get()) {
            case 0:
                if (random.nextInt(100) < 10) {
                    eatHorse();
                }
                break;
            case 1:
                if (random.nextInt(100) < 15) {
                    eatDeer();
                }
                break;
            case 2:
                if (random.nextInt(100) < 60) {
                    eatRabbit();
                }
                break;
            case 3:
                if (random.nextInt(100) < 80) {
                    eatMouse();
                }
                break;
            case 4:
                if (random.nextInt(100) < 60) {
                    eatGoat();
                }
                break;
            case 5:
                if (random.nextInt(100) < 70) {
                    eatSheep();
                }
                break;
            case 6:
                if (random.nextInt(100) < 15) {
                    eatBoar();
                }
                break;
            case 7:
                if (random.nextInt(100) < 10) {
                    eatBuffalo();
                }
                break;
            case 8:
                if (random.nextInt(100) < 40) {
                    eatDuck();
                }

        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(11);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < cageMaxCount) {
                    Wolf wolf = (Wolf) list.get(i);
                    if (wolf.sex != this.sex) {
                        Wolf wolf1 = new Wolf(this.i1, this.j1, true);
                        list.add(wolf1);
                        wolf1.start();
                        break;
                    }
                }
            }
        }
    }


    public static void createStartWolves() {
        int n = Info.startWolvesCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(11);
            int threadCount = list.size();

            if (threadCount < cageMaxCount) {
                Wolf wolf = new Wolf(i, j);
                list.add(wolf);
                wolf.start();
                n--;
            } else {
                return;
            }
        }
    }
}
