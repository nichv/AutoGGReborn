/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package pw._2pi.autogg.gg;

import java.io.FileInputStream;
import java.util.List;
import java.util.Random;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GGListener {
    private static String unformattedMessage;
    private Random r = new Random();

@SubscribeEvent
public void onChat(ClientChatReceivedEvent event) {

    List<String> triggers = AutoGG.getInstance().getTriggers();
    if (triggers == null || triggers.isEmpty()) return;

    if (!AutoGG.getInstance().isHypixel()
            || !AutoGG.getInstance().isToggled()
            || AutoGG.getInstance().isRunning()) {
        return;
    }

    String msg = event.message.getUnformattedText();
    msg = EnumChatFormatting.getTextWithoutFormattingCodes(msg);

    if (msg == null || msg.isEmpty()) return;

    String cleanMsg = msg.trim();

    // ❌ Ignore actual player chat ONLY
    if (cleanMsg.matches("^\\[[^\\]]+\\] \\w+: .*") || cleanMsg.matches("^\\w+: .*")) {
        return;
    }

    // DEBUG (optional)
    System.out.println("CHECKING: " + cleanMsg);

    boolean matched = triggers.stream().anyMatch(trigger ->
            cleanMsg.toLowerCase().contains(trigger.toLowerCase())
    );

    if (!matched) return;

    System.out.println("TRIGGERED BY: " + cleanMsg);

    AutoGG.getInstance().setRunning(true);
    AutoGG.THREAD_POOL.submit(new GGThread());
}
}

