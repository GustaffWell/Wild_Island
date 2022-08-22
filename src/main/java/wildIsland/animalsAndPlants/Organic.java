package wildIsland.animalsAndPlants;
/*
Общий предок для всех существ. Обязует научить их размножаться
и делает Трэдами. Т.е. по факту каждое существо- отдельный поток.
 */

public abstract class Organic extends Thread{

    public abstract void multiply();
}
