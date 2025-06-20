package com.freezedown.metallurgica.registry.material;

import com.freezedown.metallurgica.Metallurgica;
import com.freezedown.metallurgica.foundation.item.registry.Material;
import com.freezedown.metallurgica.foundation.item.registry.flags.block.StorageBlockFlag;
import com.freezedown.metallurgica.foundation.item.registry.flags.fluid.MoltenFlag;
import com.freezedown.metallurgica.foundation.item.registry.flags.item.*;
import com.freezedown.metallurgica.registry.misc.MetallurgicaElements;

import static com.freezedown.metallurgica.foundation.item.registry.flags.FlagKey.*;
import static com.freezedown.metallurgica.registry.material.MetMaterials.*;

public class NonMetalMaterials {
    public static void register(){
        SILICON = new Material.Builder(Metallurgica.asResource("silicon"))
                .element(MetallurgicaElements.SILICON)
                .addFlags(
                        new IngotFlag(),
                        new StorageBlockFlag().useColumnModel(),
                        new MoltenFlag(1414.0),
                        new DustFlag()
                ).buildAndRegister();

        GRAPHITE = new Material.Builder(Metallurgica.asResource("graphite"))
                .element(MetallurgicaElements.CARBON)
                .addFlags(
                        new MineralFlag(),
                        new MoltenFlag(3652.0),
                        new DustFlag()
        ).buildAndRegister();

        COKE_COAL = new Material.Builder(Metallurgica.asResource("coke_coal"))
                .element(MetallurgicaElements.CARBON)
                .noRegister(GEM,DUST,STORAGE_BLOCK)
                .addFlags(
                        new StorageBlockFlag("tfmg"),
                        new GemFlag("tfmg"),
                        new DustFlag("tfmg", false)
        ).buildAndRegister();

        COAL = new Material.Builder(Metallurgica.asResource("coal"))
                .element(MetallurgicaElements.CARBON)
                .noRegister(GEM,DUST,STORAGE_BLOCK)
                .addFlags(
                        new GemFlag("minecraft"),
                        new StorageBlockFlag("minecraft"),
                        new DustFlag("minecraft", false)
        ).buildAndRegister();

        DIAMOND = new Material.Builder(Metallurgica.asResource("diamond"))
                .element(MetallurgicaElements.CARBON)
                .noRegister(GEM,STORAGE_BLOCK)
                .addFlags(
                        new GemFlag("minecraft"),
                        new StorageBlockFlag("minecraft"),
                        new NuggetFlag(true),
                        new DustFlag()
        ).buildAndRegister();

        CHARCOAL = new Material.Builder(Metallurgica.asResource("charcoal"))
                .element(MetallurgicaElements.CARBON)
                .noRegister(GEM)
                .addFlags(
                        new GemFlag("minecraft"),
                        new DustFlag()
        ).buildAndRegister();

        SULFUR = new Material.Builder(Metallurgica.asResource("sulfur"))
                .element(MetallurgicaElements.SULFUR)
                .noRegister(DUST)
                .addFlags(
                        new DustFlag("tfmg", false)
                ).buildAndRegister();
    }
}
