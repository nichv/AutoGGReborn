/*
 * Decompiled with CFR 0.152.
 */
package pw._2pi.autogg.gg;

import pw._2pi.autogg.gg.AutoGG;

public class GGThread
implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(AutoGG.getInstance().getDelay() * 1000);
            AutoGG.getInstance().getMinecraft().thePlayer.sendChatMessage("/achat " + AutoGG.getInstance().getMessage());
            Thread.sleep(2000L);
            AutoGG.getInstance().setRunning(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

