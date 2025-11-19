package kerpackieskitchen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("kerpackieskitchen")
public class KerpackiesKitchen {

    private static final Logger LOGGER = LogManager.getLogger();

    public KerpackiesKitchen() {
        LOGGER.info("Initializing Kerpackie's Kitchen");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        LOGGER.info("Registering SandwichNameManager reload listener");
        event.addListener(SandwichNameManager.getInstance());
    }
}
