package com.freezedown.metallurgica.compat.jei.category.composition;

import com.freezedown.metallurgica.foundation.data.custom.composition.tooltip.CompositionManager;
import com.freezedown.metallurgica.foundation.data.custom.composition.tooltip.MaterialCompositionManager;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElementCompositionCategory extends CreateRecipeCategory<ElementCompositionRecipe> {
    public static final RecipeType<ElementCompositionRecipe> ITEM_COMPOSITION = RecipeType.create("metallurgica", "element_composition", ElementCompositionRecipe.class);
    private final IDrawable icon;
    private final IDrawable slotBackground;

    public static final List<ElementCompositionRecipe> COMPOSITIONS = new ArrayList<>();

    static {
        CompositionManager.compositions.forEach((item, composition) -> {
            COMPOSITIONS.add(ElementCompositionRecipe.create(composition.compositions(), composition.item(), null));
        });
        MaterialCompositionManager.materialCompositions.forEach((material, composition) -> {
            COMPOSITIONS.add(ElementCompositionRecipe.create(composition.compositions(), null, material));
        });
    }

    public ElementCompositionCategory(Info<ElementCompositionRecipe> info) {
        super(info);
        this.icon = asDrawable(AllGuiTextures.JEI_QUESTION_MARK);
        this.slotBackground = asDrawable(AllGuiTextures.JEI_SLOT);
    }

    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ITEM_COMPOSITION, COMPOSITIONS);
    }

    @Override
    public RecipeType<ElementCompositionRecipe> getRecipeType() {
        return ITEM_COMPOSITION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("metallurgica.jei.category.element_composition");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    public void draw(ElementCompositionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        int xPos = 0;
        int yPos = slotBackground.getHeight() + 4;
        Minecraft minecraft = Minecraft.getInstance();

        for(MutableComponent element : recipe.createElementLine()) {
            int yOff = yPos += 10;
            MutableComponent descriptionLine = element.copy();
            graphics.drawString(Minecraft.getInstance().font, Language.getInstance().getVisualOrder(descriptionLine), xPos, yOff, 0xFFFFFF);
            Objects.requireNonNull(minecraft.font);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ElementCompositionRecipe recipe, IFocusGroup iFocusGroup) {
        int xPos = 72;
        if (recipe.getItem() != null) {
            if (recipe.getItem() instanceof BucketItem) {
                int xOff = 18 * 4;
                IRecipeSlotBuilder fluidSlotBuilder = builder.addSlot(RecipeIngredientRole.INPUT, xPos - 9, 1).setBackground(slotBackground, -1, -1);
                IIngredientAcceptor<?> fluidOutputSlotBuilder = builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT);
                IRecipeSlotBuilder inputSlotBuilder2 = builder.addSlot(RecipeIngredientRole.INPUT, xPos + 9, 1).setBackground(slotBackground, -1, -1);
                addBucketAndFluid(recipe.getItem(), inputSlotBuilder2, fluidSlotBuilder);
                addOutput(recipe.getItem(), fluidOutputSlotBuilder);
            } else {
                IRecipeSlotBuilder inputSlotBuilder = builder.addSlot(RecipeIngredientRole.INPUT, xPos, 1).setBackground(slotBackground, -1, -1);
                IIngredientAcceptor<?> outputSlotBuilder = builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT);
                addIngredient(recipe.getItem(), inputSlotBuilder);
                addOutput(recipe.getItem(), outputSlotBuilder);
            }
        } else {
            IRecipeSlotBuilder inputSlotBuilder = builder.addSlot(RecipeIngredientRole.INPUT, xPos, 1).setBackground(slotBackground, -1, -1);
            IIngredientAcceptor<?> outputSlotBuilder = builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT);
            addListedIngredient(recipe.getAllMaterialItems(), inputSlotBuilder);
            addListedOutput(recipe.getAllMaterialItems(), outputSlotBuilder);
        }


    }

    private static <T> void addIngredient(Item item, IIngredientAcceptor<?> slotBuilder) {
        slotBuilder.addItemStack(item.getDefaultInstance());
    }

    private static <T> void addListedIngredient(List<ItemStack> stacks, IIngredientAcceptor<?> slotBuilder) {
        slotBuilder.addItemStacks(stacks);
    }

    private static <T> void addBucketAndFluid(Item item, IIngredientAcceptor<?> slotBuilder, IIngredientAcceptor<?> fluidSlotBuilder) {
        if (item instanceof BucketItem bucketItem) {
            slotBuilder.addItemStack(bucketItem.getDefaultInstance());
            fluidSlotBuilder.addFluidStack(bucketItem.getFluid(), 1000);
        } else {
            throw new IllegalArgumentException("Item must be a BucketItem");
        }
    }

    private static <T> void addOutput(Item item, IIngredientAcceptor<?> slotBuilder) {
        if (item instanceof BucketItem bucketItem) {
            slotBuilder.addFluidStack(bucketItem.getFluid(), 1000).addItemStack(item.getDefaultInstance());
        } else {
            slotBuilder.addItemStack(item.getDefaultInstance());
        }
    }

    private static <T> void addListedOutput(List<ItemStack> stacks, IIngredientAcceptor<?> slotBuilder) {
        slotBuilder.addItemStacks(stacks);
    }

    protected static IDrawable asDrawable(final AllGuiTextures texture) {
        return new IDrawable() {
            public int getWidth() {
                return texture.getWidth();
            }

            public int getHeight() {
                return texture.getHeight();
            }

            @Override
            public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
                texture.render(graphics, xOffset, yOffset);
            }
        };
    }
}
