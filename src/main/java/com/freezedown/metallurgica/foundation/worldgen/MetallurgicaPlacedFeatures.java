package com.freezedown.metallurgica.foundation.worldgen;

import com.simibubi.create.Create;
import com.simibubi.create.infrastructure.worldgen.AllConfiguredFeatures;
import com.simibubi.create.infrastructure.worldgen.ConfigPlacementFilter;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static net.minecraft.data.worldgen.placement.PlacementUtils.register;

public class MetallurgicaPlacedFeatures {
    public static final ResourceKey<PlacedFeature>
            MAGNETITE_CONDUIT = key("magnetite_conduit");

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Create.asResource(name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> featureLookup = ctx.lookup(Registries.CONFIGURED_FEATURE);
        //Holder<ConfiguredFeature<?, ?>> magnetiteConduit = featureLookup.getOrThrow(MetallurgicaConfiguredFeatures.MAGNETITE_CONDUIT);

        //register(ctx, MAGNETITE_CONDUIT, magnetiteConduit, placement(CountPlacement.of(1), -63, 70));
    }

    private static List<PlacementModifier> placement(PlacementModifier frequency, int minHeight, int maxHeight) {
        return List.of(
                frequency,
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                ConfigPlacementFilter.INSTANCE
        );
    }
}
