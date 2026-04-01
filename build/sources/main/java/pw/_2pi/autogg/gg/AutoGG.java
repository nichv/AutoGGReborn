/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.command.ICommand
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.network.FMLNetworkEvent$ClientConnectedToServerEvent
 *  net.minecraftforge.fml.common.network.FMLNetworkEvent$ClientDisconnectionFromServerEvent
 */
package pw._2pi.autogg.gg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import pw._2pi.autogg.gg.GGCommand;
import pw._2pi.autogg.gg.GGListener;
import pw._2pi.autogg.util.AutoGGThreadFactory;
import pw._2pi.autogg.util.ConfigUtil;
import pw._2pi.autogg.util.GetTriggers;

@Mod(modid="autogg", version="2.0.4", clientSideOnly=true, acceptedMinecraftVersions="[1.8.9]")
public class AutoGG {
    public static final String MODID = "autogg";
    public static final String VERSION = "2.0.4";
    public static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool(new AutoGGThreadFactory());
    private static AutoGG instance;
    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean onHypixel = false;
    private boolean toggle = true;
    private int delay = 1;
    private List<String> triggers;
    private Boolean running = false;
    public static String GGMessage;

    public AutoGG() {
        GGMessage = "gg";
    }

    public static AutoGG getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        instance = this;
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)new GGListener());
        ClientCommandHandler.instance.registerCommand((ICommand)new GGCommand());
        THREAD_POOL.submit(new GetTriggers());
        this.delay = ConfigUtil.getConfigDelay();
    }

    @SubscribeEvent
    public void playerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.onHypixel = !this.mc.isSingleplayer() && event.manager.getRemoteAddress().toString().toLowerCase().contains("hypixel.net");
    }

    @SubscribeEvent
    public void playerLoggedOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.onHypixel = false;
    }

    public boolean isHypixel() {
        return this.onHypixel;
    }

    public List getTriggers() {
        return this.triggers;
    }

    public void setTriggers(ArrayList triggers) {
        this.triggers = triggers;
    }

    public boolean isToggled() {
        return this.toggle;
    }

    public void setToggled() {
        this.toggle = !this.toggle;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Minecraft getMinecraft() {
        return this.mc;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getMessage() {
        return GGMessage;
    }

    public void setMessage(String message) {
        GGMessage = message;
    }
}

