package org.main;

import org.osbot.rs07.Bot;
import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;
import org.woodcuttingpaint.WoodcuttingPaint;

public class Woodcutter extends Script {
    private static Bot bot;
    private final Area treeArea = new Area(3174, 3475, 3179, 3470);
    private final int[] treeIDs = {1276, 1278};

    public Woodcutter(Bot bot) {
        Woodcutter.bot = bot;
    }

    public static void main(String[] args) {
        Bot bot = new Bot(12, "arg2", 143, "arg3");
        Woodcutter woodcutter = new Woodcutter(bot);
        woodcutter.onStart();
    }


    @Override
    public void onStart() {
        if (bot == null) {
            System.out.println("Error: Something is wrong");
        }

        if (bot.getPainters() == null) {
            System.out.println("Error: Something is wrong");
        }

        WoodcuttingPaint paint = new WoodcuttingPaint(this);
        bot.getPainters().add(paint);
    }

    @Override
    public int onLoop() throws InterruptedException {
        // Check if we have an axe equipped or in inventory
        String axeName = "Iron axe";
        if (inventory.contains(axeName) || equipment.contains(axeName)) {
            // Check if we are in the tree area
            if (treeArea.contains(myPlayer())) {
                // Find the nearest tree
                Entity tree = objects.closest(treeIDs);

                // If a tree is found, cut it down
                if (tree != null && tree.interact("Chop down")) {
                    // Wait until the tree has been cut down
                    new ConditionalSleep(5000) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return !tree.exists();
                        }
                    }.sleep();
                }
            } else {
                // If not in tree area, walk to the tree area
                getWalking().webWalk(treeArea);
            }
        } else {
            // If no axe, go to the bank to get one
            Bank closestBank = (Bank) bank.closest();
            if (closestBank != null) {
                Position bankPosition = closestBank.myPosition();
                getWalking().webWalk(bankPosition);
                closestBank.open();
                closestBank.withdraw(axeName, 1);
                closestBank.close();
            }
        }
        return random(3000, 5000);
    }

    @Override
    public void onExit() {
        log("Stopping Woodcutter");
    }
}
