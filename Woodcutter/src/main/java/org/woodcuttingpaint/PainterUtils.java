package org.woodcuttingpaint;

import java.awt.*;

public class PainterUtils {

    public static void drawString(Graphics2D g, String str, Font font, Color color, int x, int y) {
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(str, x + 1, y + 1);
        g.setColor(color);
        g.drawString(str, x, y);
    }
}
