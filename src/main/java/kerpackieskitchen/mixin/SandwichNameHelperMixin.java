package kerpackieskitchen.mixin;

import kerpackieskitchen.NamedSandwichNameProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import someassemblyrequired.item.sandwich.SandwichItemHandler;
import someassemblyrequired.item.sandwich.SandwichNameHelper;

import java.util.List;
import java.util.Optional;

@Mixin(SandwichNameHelper.class)
public class SandwichNameHelperMixin {

    @Inject(method = "getSandwichDisplayName", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getSandwichDisplayName(ItemStack stack, CallbackInfoReturnable<Component> cir) {
        Optional<SandwichItemHandler> sandwichOptional = SandwichItemHandler.get(stack);
        if (sandwichOptional.isPresent()) {
            SandwichItemHandler sandwich = sandwichOptional.get();
            List<ItemStack> items = sandwich.getItems();
            Component customName = NamedSandwichNameProvider.getCustomName(items);
            if (customName != null) {
                cir.setReturnValue(customName);
            }
        }
    }
}
