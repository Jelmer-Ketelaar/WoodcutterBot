package org.woodcuttingpaint;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.canvas.paint.Painter;
import org.osbot.rs07.script.Script;

import java.awt.*;

public class WoodcuttingPaint implements Painter {

    private final Color color = new Color(255, 255, 255, 150);
    private final Font font = new Font("Verdana", Font.BOLD, 12);
    private final long startTime = System.currentTimeMillis();
    private int logsChopped = 0;
    private final Script script;

    public WoodcuttingPaint(Script script) {
        this.script = script;
    }

    @Override
    public void onPaint(Graphics2D g) {
        PainterUtils.drawString(g, "Runtime: " + formatTime(System.currentTimeMillis() - startTime), font, color, 10, 35);
        PainterUtils.drawString(g, "Logs chopped: " + logsChopped, font, color, 10, 50);
        PainterUtils.drawString(g, "Woodcutting XP (p/h): " + script.getExperienceTracker().getGainedXP(Skill.WOODCUTTING)
                + " (" + script.getExperienceTracker().getGainedXPPerHour(Skill.WOODCUTTING) + ")", font, color, 10, 65);
    }

    public void incrementLogsChopped() {
        logsChopped++;
    }

    private String formatTime(long time) {
        long s = time / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
