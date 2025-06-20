package com.freezedown.metallurgica.foundation.item.registry.flags.item;

import com.freezedown.metallurgica.foundation.item.MaterialItem;
import com.freezedown.metallurgica.foundation.item.SequencedAssemblyMaterialItem;
import com.freezedown.metallurgica.foundation.item.registry.Material;
import com.freezedown.metallurgica.foundation.item.registry.flags.FlagKey;
import com.freezedown.metallurgica.foundation.item.registry.flags.base.ItemFlag;
import com.freezedown.metallurgica.foundation.item.registry.flags.base.MaterialFlags;
import com.freezedown.metallurgica.foundation.registrate.MetallurgicaRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import org.jetbrains.annotations.NotNull;

public class SemiPressedSheetFlag extends ItemFlag {

    public SemiPressedSheetFlag() {
        super("semi_pressed_%s_sheet");
    }

    @Override
    public ItemEntry<? extends MaterialItem> registerItem(@NotNull Material material, ItemFlag flag, @NotNull MetallurgicaRegistrate registrate) {
        return registrate
                .item(flag.getIdPattern().formatted(material.getName()), (p) -> new SequencedAssemblyMaterialItem(p, material, flag))
                .setData(ProviderType.LANG, NonNullBiConsumer.noop())
                .setData(ProviderType.ITEM_MODEL, NonNullBiConsumer.noop())
                .register();
    }

    @Override
    public FlagKey<?> getKey() {
        return FlagKey.SEMI_PRESSED_SHEET;
    }

    @Override
    public void verifyFlag(MaterialFlags flags) {
        flags.ensureSet(FlagKey.SHEET, true);
    }
}
