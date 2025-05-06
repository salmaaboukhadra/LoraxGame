package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor {
    private static char last = ' ';
    private static stopWatchX sw = new stopWatchX(100);

    public static void processKey(char key) {
        if (key == last && !sw.isTimeUp()) return;
        last = key;
        sw.resetWatch();

        switch (key) {
            case '%': // ESC key
                System.exit(0); 
                break;
            case 'w':
                Main.currentDirection = "up";
                Main.isMoving = Main.movePlayer(0, -5);
                break;
            case 'a':
                Main.currentDirection = "left";
                Main.isMoving = Main.movePlayer(-5, 0);
                break;
            case 's':
                Main.currentDirection = "down";
                Main.isMoving = Main.movePlayer(0, 5);
                break;
            case 'd':
                Main.currentDirection = "right";
                Main.isMoving = Main.movePlayer(5, 0);
                break;
            case '$': // Space bar for examine
                Main.examineItems();
                break;
            default:
                Main.isMoving = false;
                break;
        }
    }
}
