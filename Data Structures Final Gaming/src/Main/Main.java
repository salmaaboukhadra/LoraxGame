package Main;
import java.awt.Color;
import java.util.ArrayList;

import logic.Control;
import timer.stopWatchX;
import Data.Vector2D;
import Data.spriteInfo;
import Data.boundingBox;

public class Main {
    public static Color c = new Color(147, 112, 219);
    public static stopWatchX timer = new stopWatchX(150); // Animation timer

    // Player variables
    public static spriteInfo player;
    public static String currentDirection = "down";
    public static int currentFrame = 1;
    public static boolean isMoving = false;

    // Required data structures for project
    public static ArrayList<boundingBox> collisionBoxes = new ArrayList<>();
    public static ArrayList<spriteInfo> items = new ArrayList<>();

    // Dialog and examination
    public static stopWatchX dialogTimer = new stopWatchX(4000);
    public static boolean isExamining = false;
    public static String currentItemDescription = "";
    public static String hoverText = "";
    public static boolean showHoverText = false;

    public static void main(String[] args) {
        start();
        Control ctrl = new Control();
        ctrl.gameLoop();
    }

    public static void start() {
        // Initialize player at center of screen, facing down
        player = new spriteInfo(new Vector2D(720, 450), "16");

        // Create interactive items and their collision boxes (100x100 items)
        spriteInfo item1 = new spriteInfo(new Vector2D(300, 300), "item1");
        items.add(item1);
        collisionBoxes.add(new boundingBox(300, 400, 300, 400));

        spriteInfo item2 = new spriteInfo(new Vector2D(1100, 600), "item2");
        items.add(item2);
        collisionBoxes.add(new boundingBox(1100, 1200, 600, 700));
    }

    public static void update(Control ctrl) {
        // Draw the background (with 55px border)
        ctrl.addSpriteToFrontBuffer(0, 0, "background");

        // Draw items
        for (spriteInfo item : items) {
            ctrl.addSpriteToFrontBuffer(
                item.getCoords().getX(),
                item.getCoords().getY(),
                item.getTag()
            );
        }

        // Check item proximity
        checkItemProximity();

        // Update animation frame if player is moving
        if (isMoving && timer.isTimeUp()) {
            timer.resetWatch();
            currentFrame++;
            if (currentFrame > 5) currentFrame = 1;
        }

        // Map direction + frame to correct image number (1-20)
        int spriteNumber = 16; // default: down
        if (currentDirection.equals("right")) spriteNumber = currentFrame;
        else if (currentDirection.equals("left")) spriteNumber = 5 + currentFrame;
        else if (currentDirection.equals("up")) spriteNumber = 10 + currentFrame;
        else if (currentDirection.equals("down")) spriteNumber = 15 + currentFrame;

        // Draw player
        ctrl.addSpriteToFrontBuffer(
            player.getCoords().getX(),
            player.getCoords().getY(),
            String.valueOf(spriteNumber)
        );

        // Display item hover text above player
        if (showHoverText) {
            ctrl.drawString(
                player.getCoords().getX() + 20,
                player.getCoords().getY() - 20,
                hoverText,
                Color.WHITE
            );
        }

        // Display item description if examining
        if (isExamining) {
            if (dialogTimer.isTimeUp()) {
                isExamining = false;
            } else {
                ctrl.drawString(100, 500, currentItemDescription, Color.WHITE);
            }
        }
    }

    public static boolean movePlayer(int dx, int dy) {
        int oldX = player.getCoords().getX();
        int oldY = player.getCoords().getY();

        player.getCoords().adjustX(dx);
        player.getCoords().adjustY(dy);

        int x = player.getCoords().getX();
        int y = player.getCoords().getY();
        int width = 128, height = 128;

        // 55-pixel border on all sides
        int minX = 55;
        int maxX = 1440 - 55 - width; // 1257
        int minY = 55;
        int maxY = 900 - 55 - height; // 717

        if (x < minX) player.getCoords().setX(minX);
        if (x > maxX) player.getCoords().setX(maxX);
        if (y < minY) player.getCoords().setY(minY);
        if (y > maxY) player.getCoords().setY(maxY);

        // Check for item collisions
        boundingBox playerBox = new boundingBox(
            x + 32, x + 96, y + 32, y + 96
        );

        for (boundingBox box : collisionBoxes) {
            if (playerBox.isColliding(box)) {
                player.getCoords().setX(oldX);
                player.getCoords().setY(oldY);
                return false;
            }
        }
        return true;
    }

    public static void checkItemProximity() {
        hoverText = "";
        showHoverText = false;
        
        // Create a detection box for proximity check
        boundingBox proximityBox;
        int boxSize = 50;
        int px = player.getCoords().getX();
        int py = player.getCoords().getY();
        
        if (currentDirection.equals("up")) {
            proximityBox = new boundingBox(px + 32, px + 96, py - boxSize, py + 32);
        } else if (currentDirection.equals("down")) {
            proximityBox = new boundingBox(px + 32, px + 96, py + 96, py + 96 + boxSize);
        } else if (currentDirection.equals("left")) {
            proximityBox = new boundingBox(px - boxSize, px + 32, py + 32, py + 96);
        } else { // right
            proximityBox = new boundingBox(px + 96, px + 96 + boxSize, py + 32, py + 96);
        }
        
        // Check if near any item and facing it
        for (int i = 0; i < items.size(); i++) {
            boundingBox itemBox = new boundingBox(
                items.get(i).getCoords().getX(),
                items.get(i).getCoords().getX() + 100,
                items.get(i).getCoords().getY(),
                items.get(i).getCoords().getY() + 100
            );
            
            if (proximityBox.isColliding(itemBox)) {
                if (i == 0) hoverText = "Press SPACE to examine artifact";
                else hoverText = "Press SPACE to examine relic";
                showHoverText = true;
                break;
            }
        }
    }

    public static void examineItems() {
        // Create a detection box in front of player
        boundingBox examBox;
        int boxSize = 30;
        int px = player.getCoords().getX();
        int py = player.getCoords().getY();

        if (currentDirection.equals("up")) {
            examBox = new boundingBox(px + 32, px + 96, py - boxSize, py + 32);
        } else if (currentDirection.equals("down")) {
            examBox = new boundingBox(px + 32, px + 96, py + 96, py + 96 + boxSize);
        } else if (currentDirection.equals("left")) {
            examBox = new boundingBox(px - boxSize, px + 32, py + 32, py + 96);
        } else { // right
            examBox = new boundingBox(px + 96, px + 96 + boxSize, py + 32, py + 96);
        }

        // Check if examining any item
        for (int i = 0; i < items.size(); i++) {
            boundingBox itemBox = new boundingBox(
                items.get(i).getCoords().getX(),
                items.get(i).getCoords().getX() + 100,
                items.get(i).getCoords().getY(),
                items.get(i).getCoords().getY() + 100
            );
            if (examBox.isColliding(itemBox)) {
                if (i == 0) currentItemDescription = "I am the Lorax I speak for the Trees.";
                else currentItemDescription = "Unless someone cares a whole awful lot nothing is going to change.";
                isExamining = true;
                dialogTimer.resetWatch();
                break;
            }
        }
    }
}
