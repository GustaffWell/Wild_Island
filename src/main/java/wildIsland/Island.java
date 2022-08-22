package wildIsland;

import wildIsland.animalsAndPlants.*;

import java.util.ArrayList;

public class Island {

    public static final int length = 5;                                 //  Размеры острова
    public static final  int width = 5;
    public static ArrayList<ArrayList<Thread>>[][] field;               //  Двумерный массив, хранящий список списков Трэдов =)
    public static Info info = new Info();
    public static Thread threadInfo = new Thread(info);
    public static void createField() {
        field = new ArrayList[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                field[i][j] = new ArrayList<>();
                field[i][j].add(new ArrayList<>()); // индекс (0) для растений
                field[i][j].add(new ArrayList<>()); // индекс (1) для лошадей
                field[i][j].add(new ArrayList<>()); // индекс (2) для оленей
                field[i][j].add(new ArrayList<>()); // индекс (3) для кроликов
                field[i][j].add(new ArrayList<>()); // индекс (4) для гусениц
                field[i][j].add(new ArrayList<>()); // индекс (5) для мышей
                field[i][j].add(new ArrayList<>()); // индекс (6) для коз
                field[i][j].add(new ArrayList<>()); // индекс (7) для овец
                field[i][j].add(new ArrayList<>()); // индекс (8) для кабанов
                field[i][j].add(new ArrayList<>()); // индекс (9) для буйволов
                field[i][j].add(new ArrayList<>()); // индекс (10) для уток
                field[i][j].add(new ArrayList<>()); // индекс (11) для волков
                field[i][j].add(new ArrayList<>()); // индекс (12) для удавов
                field[i][j].add(new ArrayList<>()); // индекс (13) для лис
                field[i][j].add(new ArrayList<>()); // индекс (14) для медведей
                field[i][j].add(new ArrayList<>()); // индекс (15) для орлов
            }
        }
    }
    public static void createOrganics() {           //  Создание существ спец методами из их классов
        Plant.createStartPlants();
        Horse.createStartHorses();
        Deer.createStartDeer();
        Rabbit.createStartRabbits();
        Caterpillar.createStartCaterpillars();
        Mouse.createStartMouses();
        Goat.createStartGoats();
        Sheep.createStartSheep();
        Boar.createStartBoars();
        Buffalo.createStartBuffaloes();
        Duck.createStartDucks();
        Wolf.createStartWolves();
        Boa.createStartBoas();
        Fox.createStartFoxes();
        Bear.createStartBears();
        Eagle.createStartEagles();
    }
    public static void start() {
        System.out.println("Создаем мир...");
        Island.createField();               // создается остров
        System.out.println("Заполняем существами...");
        Island.createOrganics();            // заполняется существами
        System.out.println("Старт!");
        threadInfo.start();                 // старт вывода статистики
    }

    public synchronized static void stop() {
        System.out.println("Симуляция остановлена! Экосистема в критической ситуации!");
        System.exit(0);
    }
}

