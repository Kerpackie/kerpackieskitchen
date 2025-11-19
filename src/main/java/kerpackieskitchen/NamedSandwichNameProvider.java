package kerpackieskitchen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import someassemblyrequired.registry.ModItems;

import java.util.List;

public class NamedSandwichNameProvider {

    public static Component getCustomName(List<ItemStack> ingredients) {
        return SandwichNameManager.getInstance().getCustomName(ingredients);
    }
}
