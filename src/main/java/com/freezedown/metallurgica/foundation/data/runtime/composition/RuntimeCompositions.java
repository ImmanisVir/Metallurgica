package com.freezedown.metallurgica.foundation.data.runtime.composition;

import com.freezedown.metallurgica.foundation.block.MaterialBlock;
import com.freezedown.metallurgica.foundation.data.custom.composition.FinishedComposition;
import com.freezedown.metallurgica.foundation.data.custom.composition.tooltip.CompositionBuilder;
import com.freezedown.metallurgica.foundation.data.custom.composition.tooltip.MaterialCompositionBuilder;
import com.freezedown.metallurgica.foundation.item.MaterialItem;
import com.freezedown.metallurgica.foundation.item.registry.Material;
import com.freezedown.metallurgica.foundation.item.registry.flags.FlagKey;
import com.freezedown.metallurgica.foundation.item.registry.flags.base.BlockFlag;
import com.freezedown.metallurgica.foundation.item.registry.flags.base.ItemFlag;
import com.freezedown.metallurgica.foundation.material.MaterialHelper;
import com.freezedown.metallurgica.infastructure.element.data.ElementData;
import com.freezedown.metallurgica.infastructure.element.data.SubComposition;
import com.freezedown.metallurgica.registry.material.MetMaterials;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Consumer;

public class RuntimeCompositions {
    public static void compositionAddition(Consumer<FinishedComposition> originalConsumer) {
        for (Material material : MetMaterials.registeredMaterials.values()) {
            createComposition(originalConsumer, material, ElementData.createFromList(material.getComposition()));
        }
    }

    protected static void createComposition(Consumer<FinishedComposition> pFinishedCompositionConsumer, Material material, List<SubComposition> subCompositions) {
        MaterialCompositionBuilder.create(material, subCompositions).save(pFinishedCompositionConsumer);
    }
}
