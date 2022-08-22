package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Horse extends Herbivore {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public Horse (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 400;
        this.speed = 4;
        this.hanger = 60;
        this.sex = random.nextInt(2);
    }

    public Horse (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 400;
        this.speed = 4;
        this.hanger = 60;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> horsesList = Island.field[this.i1][this.j1].get(1);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(5 * Info.iterationTime);
            } catch (InterruptedException e) {
                horsesList.remove(current);
                current.stop();
            }
        }
        while(!current.isInterrupted()) {
            if (age % 5 == 0 && random.nextInt(10) == 1) {
                this.multiply();
            }
            if (age >= 20) {
                if (random.nextInt(1, 100) <= (1 + 0.1 * age)) {
                    horsesList.remove(current);
                    current.stop();
                }
            }
            if (random.nextInt(2) == 0) {
                    this.eat();
                    this.eat();
                    this.eat();
            } else {
                this.move();
            }

            if ((age > 10) && (this.hanger >= 60)) {
                horsesList.remove(current);
                current.stop();
            }

            try {
                Thread.sleep(Info.iterationTime);
            } catch (InterruptedException e) {
                horsesList.remove(current);
                current.stop();
            }
            age++;
            if (hanger < 60) {
                hanger++;
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> horsesList = Island.field[this.i1][this.j1].get(1);

            AtomicInteger cageCount = new AtomicInteger(horsesList.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < 20) {
                    Horse horse = (Horse) horsesList.get(i);
                    if (horse.sex != this.sex) {
                        if (random.nextInt(2) == 0) {
                            Horse horse1 = new Horse(this.i1, this.j1, true);
                            horsesList.add(horse1);
                            horse1.start();
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void createStartHorses () {
        int n = Info.startHorsesCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> horsesList = Island.field[i][j].get(1);
            int threadCount = horsesList.size();

            if (threadCount < 20) {
                Horse horse = new Horse(i, j);
                horsesList.add(horse);
                horse.start();
                n--;
            } else {
                return;
            }
        }
    }
}
