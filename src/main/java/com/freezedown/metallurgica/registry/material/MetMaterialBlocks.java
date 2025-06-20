package com.freezedown.metallurgica.registry.material;

import com.freezedown.metallurgica.foundation.block.MaterialBlock;
import com.freezedown.metallurgica.foundation.item.registry.Material;
import com.freezedown.metallurgica.foundation.item.registry.flags.FlagKey;
import com.freezedown.metallurgica.foundation.item.registry.flags.base.BlockFlag;
import com.freezedown.metallurgica.foundation.registrate.MetallurgicaRegistrate;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.tterrag.registrate.util.entry.BlockEntry;

public class MetMaterialBlocks {
    public static ImmutableTable.Builder<FlagKey<?>, Material, BlockEntry<? extends MaterialBlock>> MATERIAL_BLOCKS_BUILDER = ImmutableTable
            .builder();

    public static Table<FlagKey<?>, Material, BlockEntry<? extends MaterialBlock>> MATERIAL_BLOCKS;

    public static void generateMaterialBlocks(MetallurgicaRegistrate registrate) {
        for (Material material : MetMaterials.registeredMaterials.values()) {
            for (FlagKey<?> flagKey : FlagKey.getAllFlags()) {
                var flag = material.getFlag(flagKey);
                if (!material.noRegister(flagKey)) {
                    if (flag instanceof BlockFlag blockFlag) {
                        registerMaterialBlock(blockFlag, material, flagKey, registrate);
                    }
                }
            }
        }
    }

//
    private static void registerMaterialBlock(BlockFlag flag, Material material, FlagKey<?> flagKey, MetallurgicaRegistrate registrate) {
        MATERIAL_BLOCKS_BUILDER.put(flagKey, material, flag.registerBlock(material, flag, registrate));
    }
}
