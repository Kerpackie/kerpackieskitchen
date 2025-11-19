package kerpackieskitchen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class SandwichNameManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final SandwichNameManager INSTANCE = new SandwichNameManager();
    private final Map<List<Item>, Component> sandwichNames = new HashMap<>();

    public SandwichNameManager() {
        super(GSON, "sandwich_names");
    }

    public static SandwichNameManager getInstance() {
        return INSTANCE;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        sandwichNames.clear();
        object.forEach((location, json) -> {
            try {
                JsonObject jsonObject = json.getAsJsonObject();
                List<Item> ingredients = new ArrayList<>();
                for (JsonElement element : jsonObject.getAsJsonArray("ingredients")) {
                    ResourceLocation itemLocation = new ResourceLocation(element.getAsString());
                    Item item = ForgeRegistries.ITEMS.getValue(itemLocation);
                    if (item != null) {
                        ingredients.add(item);
                    }
                }
                // Sort to ensure order doesn't matter for the key
                ingredients.sort(Comparator.comparing(ForgeRegistries.ITEMS::getKey));

                Component name = Component.Serializer.fromJson(jsonObject.get("name"));
                sandwichNames.put(ingredients, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Component getCustomName(List<ItemStack> ingredients) {
        List<Item> ingredientItems = new ArrayList<>();
        for (ItemStack stack : ingredients) {
            ingredientItems.add(stack.getItem());
        }
        ingredientItems.sort(Comparator.comparing(ForgeRegistries.ITEMS::getKey));

        return sandwichNames.get(ingredientItems);
    }
}
