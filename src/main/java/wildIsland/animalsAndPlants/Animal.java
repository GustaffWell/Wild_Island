package wildIsland.animalsAndPlants;

/*
Класс определяет общие характеристики для всех существ, кроме растений: Координаты, масса
скорость, уровень голода, ребенок или нет, пол.
Содержит уже описанный метод перемещения, зависящий от скорости существа и рандома.
Обязывает научить есть.
 */

import wildIsland.Island;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Organic {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public int i1;
    public int j1;
    public double mass;
    public int speed;
    public double hanger;
    public boolean isChild = false;
    public int sex;

    public abstract void eat();

    public void move() {
        int randomSpeed = random.nextInt(0, this.speed);

        for (int i = 0; i < randomSpeed; i++) {

            int fate = random.nextInt(0, 4);

            if ((fate == 0) && (this.i1 + 1 < Island.width) && (this.j1 + 1 < Island.length)) {
                this.i1 += 1;
                this.j1 += 1;
            } else if ((fate == 1) && (this.i1 + 1 < Island.width) && (this.j1 - 1 >= 0)) {
                this.i1 += 1;
                this.j1 -= 1;
            } else if ((fate == 2) && (this.i1 - 1 >= 0) && (this.j1 + 1 < Island.length)) {
                this.i1 -= 1;
                this.j1 += 1;
            } else if ((fate == 3) && (this.i1 - 1 >= 0) && (this.j1 - 1 >= 0)) {
                this.i1 -= 1;
                this.j1 -= 1;
            }
        }
    }
}
