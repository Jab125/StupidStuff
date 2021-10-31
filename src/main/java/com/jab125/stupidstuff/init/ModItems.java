package com.jab125.stupidstuff.init;

import com.jab125.stupidstuff.StupidStuff;
import com.jab125.stupidstuff.item.StupidItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item STUPID_ITEM = new StupidItem(new FabricItemSettings().group(ItemGroup.FOOD));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(StupidStuff.MODID, "stupid_item"), STUPID_ITEM);
   }
}
