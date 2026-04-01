/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 */
package pw._2pi.autogg.gg;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import pw._2pi.autogg.gg.AutoGG;
import pw._2pi.autogg.util.ConfigUtil;

public class GGCommand
extends CommandBase {
    public String getCommandName() {
        return "autogg";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public boolean canSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public void processCommand(ICommandSender sender, String[] args) {
        String s;
        if (args.length == 0) {
            this.showSyntaxError(sender);
            return;
        }
        switch (s = args[0]) {
            case "toggle": 
            case "t": {
                AutoGG.getInstance().setToggled();
                this.showMessage(EnumChatFormatting.GRAY + "AutoGG: " + (AutoGG.getInstance().isToggled() ? EnumChatFormatting.GREEN + "On" : EnumChatFormatting.RED + "Off"), sender);
                break;
            }
            case "delay": 
            case "d": 
            case "time": {
                if (args.length == 2) {
                    try {
                        int delay = Integer.parseInt(args[1]);
                        if (delay < 0 || delay > 5) {
                            throw new NumberFormatException("Invalid integer");
                        }
                        AutoGG.getInstance().setDelay(delay);
                        ConfigUtil.setConfigDelay();
                        this.showMessage(EnumChatFormatting.GRAY + "AutoGG delay set to " + EnumChatFormatting.GREEN + AutoGG.getInstance().getDelay() + "s", sender);
                    }
                    catch (NumberFormatException e) {
                        this.showError("Please use an integer between 1 and 5 seconds.", sender);
                    }
                    break;
                }
                this.showMessage(EnumChatFormatting.GRAY + "AutoGG Delay: " + EnumChatFormatting.GREEN + AutoGG.getInstance().getDelay() + "s", sender);
                break;
            }
            case "change": {
                if (args.length >= 1) {
                    try {
                        String message = new String();
                        for (int i = 1; i < args.length; ++i) {
                            message = message + args[i];
                            if (args.length == i) continue;
                            message = message + ' ';
                        }
                        AutoGG.getInstance().setMessage(message);
                        ConfigUtil.setConfigChangeable();
                    }
                    catch (Exception e) {
                        this.showSyntaxError(sender);
                    }
                }
                this.showMessage(EnumChatFormatting.GRAY + "The AutoGG Message is now: " + EnumChatFormatting.GREEN + AutoGG.getInstance().getMessage(), sender);
                break;
            }
            default: {
                this.showSyntaxError(sender);
            }
        }
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/autogg <toggle, delay [seconds], change [message]>";
    }

    private void showMessage(String message, ICommandSender sender) {
        sender.addChatMessage((IChatComponent)new ChatComponentText(message));
    }

    private void showSyntaxError(ICommandSender sender) {
        this.showMessage(EnumChatFormatting.RED + "Usage: " + this.getCommandUsage(sender), sender);
    }

    private void showError(String error, ICommandSender sender) {
        this.showMessage(EnumChatFormatting.RED + "Error: " + error, sender);
    }
}

