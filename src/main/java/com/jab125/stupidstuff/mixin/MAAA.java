package com.jab125.stupidstuff.mixin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.systems.RenderCallStorage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.PackType;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.main.Main;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.util.GlException;
import net.minecraft.client.util.Session;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

@Mixin(Main.class)
public class MAAA {
    private static final Logger LOGGER = LogManager.getLogger();
    @Inject(method = "main", at = @At("HEAD"), cancellable = true)
    private static void inject_main(String[] args, CallbackInfo cir) {
        ///SharedConstants.createGameVersion();
        SharedConstants.setGameVersion(new GameVersion() {
            @Override
            public String getId() {
                return "420.69";
            }

            @Override
            public String getName() {
                return "420.69";
            }

            @Override
            public String getReleaseTarget() {
                return "420.69";
            }

            @Override
            public int getWorldVersion() {
                return 42069;
            }

            @Override
            public int getProtocolVersion() {
                return 42069;
            }

            @Override
            public int getPackVersion(PackType packType) {
                return 42069;
            }

            @Override
            public Date getBuildTime() {
                return new Date();
            }

            @Override
            public boolean isStable() {
                return true;
            }
        });
        OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("demo");
        optionParser.accepts("disableMultiplayer");
        optionParser.accepts("disableChat");
        optionParser.accepts("fullscreen");
        optionParser.accepts("checkGlErrors");
        OptionSpec<String> optionSpec = optionParser.accepts("server").withRequiredArg();
        OptionSpec<Integer> optionSpec2 = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565, new Integer[0]);
        OptionSpec<File> optionSpec3 = optionParser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]);
        OptionSpec<File> optionSpec4 = optionParser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        OptionSpec<File> optionSpec5 = optionParser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        OptionSpec<String> optionSpec6 = optionParser.accepts("proxyHost").withRequiredArg();
        OptionSpec<Integer> optionSpec7 = optionParser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        OptionSpec<String> optionSpec8 = optionParser.accepts("proxyUser").withRequiredArg();
        OptionSpec<String> optionSpec9 = optionParser.accepts("proxyPass").withRequiredArg();
        OptionSpec<String> optionSpec10 = optionParser.accepts("username").withRequiredArg().defaultsTo("Player" + Util.getMeasuringTimeMs() % 1000L, new String[0]);
        OptionSpec<String> optionSpec11 = optionParser.accepts("uuid").withRequiredArg();
        OptionSpec<String> optionSpec12 = optionParser.accepts("accessToken").withRequiredArg().required();
        OptionSpec<String> optionSpec13 = optionParser.accepts("version").withRequiredArg().required();
        OptionSpec<Integer> optionSpec14 = optionParser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854, new Integer[0]);
        OptionSpec<Integer> optionSpec15 = optionParser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480, new Integer[0]);
        OptionSpec<Integer> optionSpec16 = optionParser.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
        OptionSpec<Integer> optionSpec17 = optionParser.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
        OptionSpec<String> optionSpec18 = optionParser.accepts("userProperties").withRequiredArg().defaultsTo("{}", new String[0]);
        OptionSpec<String> optionSpec19 = optionParser.accepts("profileProperties").withRequiredArg().defaultsTo("{}", new String[0]);
        OptionSpec<String> optionSpec20 = optionParser.accepts("assetIndex").withRequiredArg();
        OptionSpec<String> optionSpec21 = optionParser.accepts("userType").withRequiredArg().defaultsTo("legacy", new String[0]);
        OptionSpec<String> optionSpec22 = optionParser.accepts("versionType").withRequiredArg().defaultsTo("release", new String[0]);
        OptionSpec<String> optionSpec23 = optionParser.nonOptions();
        OptionSet optionSet = optionParser.parse(args);
        List<String> list = optionSet.valuesOf(optionSpec23);
        if (!list.isEmpty()) {
            System.out.println("Completely ignored arguments: " + list);
        }

        String string = (String) getOption(optionSet, optionSpec6);
        Proxy proxy = Proxy.NO_PROXY;
        if (string != null) {
            try {
                proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(string, (Integer) getOption(optionSet, optionSpec7)));
            } catch (Exception var70) {
            }
        }

        final String string2 = (String) getOption(optionSet, optionSpec8);
        final String string3 = (String) getOption(optionSet, optionSpec9);
        if (!proxy.equals(Proxy.NO_PROXY) && isNotNullOrEmpty(string2) && isNotNullOrEmpty(string3)) {
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(string2, string3.toCharArray());
                }
            });
        }

        int i = (Integer) getOption(optionSet, optionSpec14);
        int j = (Integer) getOption(optionSet, optionSpec15);
        OptionalInt optionalInt = toOptional((Integer) getOption(optionSet, optionSpec16));
        OptionalInt optionalInt2 = toOptional((Integer) getOption(optionSet, optionSpec17));
        boolean bl = optionSet.has("fullscreen");
        boolean bl2 = optionSet.has("demo");
        boolean bl3 = optionSet.has("disableMultiplayer");
        boolean bl4 = optionSet.has("disableChat");
        String string4 = (String) getOption(optionSet, optionSpec13);
        Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create();
        PropertyMap propertyMap = (PropertyMap) JsonHelper.deserialize(gson, (String) getOption(optionSet, optionSpec18), PropertyMap.class);
        PropertyMap propertyMap2 = (PropertyMap) JsonHelper.deserialize(gson, (String) getOption(optionSet, optionSpec19), PropertyMap.class);
        String string5 = (String) getOption(optionSet, optionSpec22);
        File file = (File) getOption(optionSet, optionSpec3);
        File file2 = optionSet.has(optionSpec4) ? (File) getOption(optionSet, optionSpec4) : new File(file, "assets/");
        File file3 = optionSet.has(optionSpec5) ? (File) getOption(optionSet, optionSpec5) : new File(file, "resourcepacks/");
        String string6 = optionSet.has(optionSpec11) ? (String) optionSpec11.value(optionSet) : PlayerEntity.getOfflinePlayerUuid((String) optionSpec10.value(optionSet)).toString();
        String string7 = optionSet.has(optionSpec20) ? (String) optionSpec20.value(optionSet) : null;
        String string8 = (String) getOption(optionSet, optionSpec);
        Integer integer = (Integer) getOption(optionSet, optionSpec2);
        CrashReport.initCrashReport();
        Bootstrap.initialize();
        Bootstrap.logMissing();
        Util.startTimerHack();
        Session session = new Session((String) optionSpec10.value(optionSet), string6, (String) optionSpec12.value(optionSet), (String) optionSpec21.value(optionSet));
        RunArgs runArgs = new RunArgs(new RunArgs.Network(session, propertyMap, propertyMap2, proxy), new WindowSettings(i, j, optionalInt, optionalInt2, bl), new RunArgs.Directories(file, file3, file2, string7), new RunArgs.Game(bl2, string4, string5, bl3, bl4), new RunArgs.AutoConnect(string8, integer));
        Thread thread = new Thread("Client Shutdown Thread") {
            public void run() {
                MinecraftClient minecraftClient = MinecraftClient.getInstance();
                if (minecraftClient != null) {
                    IntegratedServer integratedServer = minecraftClient.getServer();
                    if (integratedServer != null) {
                        integratedServer.stop(true);
                    }

                }
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
        Runtime.getRuntime().addShutdownHook(thread);
        new RenderCallStorage();

        final MinecraftClient minecraftClient2;
        try {
            Thread.currentThread().setName("Render thread");
            RenderSystem.initRenderThread();
            RenderSystem.beginInitialization();
            minecraftClient2 = new MinecraftClient(runArgs);
            RenderSystem.finishInitialization();
        } catch (GlException var68) {
            LOGGER.warn("Failed to create window: ", var68);
            return;
        } catch (Throwable var69) {
            CrashReport crashReport = CrashReport.create(var69, "Initializing game");
            crashReport.addElement("Initialization");
            MinecraftClient.addSystemDetailsToCrashReport((MinecraftClient) null, (LanguageManager) null, runArgs.game.version, (GameOptions) null, crashReport);
            MinecraftClient.printCrashReport(crashReport);
            return;
        }

        Thread thread3;
        if (minecraftClient2.shouldRenderAsync()) {
            thread3 = new Thread("Game thread") {
                public void run() {
                    try {
                        RenderSystem.initGameThread(true);
                        minecraftClient2.run();
                    } catch (Throwable var2) {
                        LOGGER.error("Exception in client thread", var2);
                    }

                }
            };
            thread3.start();

            while (true) {
                if (minecraftClient2.isRunning()) {
                    continue;
                }
            }
        } else {
            thread3 = null;

            try {
                RenderSystem.initGameThread(false);
                minecraftClient2.run();
            } catch (Throwable var67) {
                LOGGER.error("Unhandled game exception", var67);
            }
        }

        BufferRenderer.unbindAll();

        try {
            minecraftClient2.scheduleStop();
            if (thread3 != null) {
                thread3.join();
            }
        } catch (InterruptedException var65) {
            LOGGER.error("Exception during client thread shutdown", var65);
        } finally {
            minecraftClient2.stop();
        }
        cir.cancel();
    }

    private static OptionalInt toOptional(@Nullable Integer i) {
        return i != null ? OptionalInt.of(i) : OptionalInt.empty();
    }

    @Nullable
    private static <T> T getOption(OptionSet optionSet, OptionSpec<T> optionSpec) {
        try {
            return optionSet.valueOf(optionSpec);
        } catch (Throwable var5) {
            if (optionSpec instanceof ArgumentAcceptingOptionSpec) {
                ArgumentAcceptingOptionSpec<T> argumentAcceptingOptionSpec = (ArgumentAcceptingOptionSpec) optionSpec;
                List<T> list = argumentAcceptingOptionSpec.defaultValues();
                if (!list.isEmpty()) {
                    return list.get(0);
                }
            }

            throw var5;
        }

    }

    private static boolean isNotNullOrEmpty(@Nullable String s) {
        return s != null && !s.isEmpty();
    }

    static {
        System.setProperty("java.awt.headless", "true");
    }
}
