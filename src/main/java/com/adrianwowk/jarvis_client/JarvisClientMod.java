package com.adrianwowk.jarvis_client;

import com.adrianwowk.jarvis_client.client.gui.GuiMods;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SuppressWarnings("deprecation")
@Mod(modid = JarvisClientMod.MODID, version = JarvisClientMod.VERSION)
public class JarvisClientMod
{
    public static KeyBinding[] keyBindings;

    public static final String MODID = "jarvis_client";
    public static final String VERSION = "1.0";

    public JarvisClientMod(){
        initKeyBinds();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        FontRenderer fRender = Minecraft.getMinecraft().fontRendererObj;
        fRender.drawStringWithShadow(EnumChatFormatting.GREEN + "FPS: " + EnumChatFormatting.WHITE + Minecraft.getDebugFPS(), 5, 5, 0);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(InputEvent.KeyInputEvent event)
    {
        KeyBinding[] keyBindings = JarvisClientMod.keyBindings;

        if (keyBindings[0].isPressed())
            Minecraft.getMinecraft().displayGuiScreen(new GuiMods());
    }

    private void initKeyBinds(){
        keyBindings = new KeyBinding[1];
        keyBindings[0] = new KeyBinding("Open Client Menu" , Keyboard.KEY_RSHIFT, "Jarvis Client");

        for (int i = 0; i < keyBindings.length; ++i)
            ClientRegistry.registerKeyBinding(keyBindings[i]);
    }
}
