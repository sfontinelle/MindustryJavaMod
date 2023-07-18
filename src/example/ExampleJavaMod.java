package example;

import arc.*;
import arc.scene.style.Drawable;
import arc.scene.style.TextureRegionDrawable;
import arc.struct.ObjectMap;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.core.UI;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.type.Category;
import mindustry.ui.dialogs.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ExampleJavaMod extends Mod {

    boolean isLoaded = false;
    boolean containsKey = false;

    public ExampleJavaMod() {
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                loadIcons();
            });
        });
    }

    public void startScreen() {
        BaseDialog dialog = new BaseDialog("frog");
        dialog.cont.add("behold").row();
        dialog.cont.add("turret").row();
        dialog.cont.add("" + Icon.icons.size).row();
        dialog.cont.add("" + Icon.icons.size).row();
        dialog.cont.add("isLoaded: " + isLoaded).row();
        dialog.cont.add("containsKey?: " + Icon.icons.containsKey("liquid")).row();
        dialog.cont.add("containsKey?: " + containsKey).row();
        //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
        dialog.cont.image(Core.atlas.find("example-java-mod-frog")).pad(20f).row();
        dialog.cont.button("I see", dialog::hide).size(100f, 50f);
        dialog.show();
    }

    @Override
    public void loadContent() {
        Log.info("Loading some example content.");
    }

    public void loadIcons() {
        ObjectMap<String, TextureRegionDrawable> icons = Icon.icons;
        icons.put("turret", getCustomIcons("turrets"));
        icons.put("liquid", getCustomIcons("liquid"));
        icons.put("defense", getCustomIcons("defense"));
        icons.put("units", getCustomIcons("units"));
        icons.put("production", getCustomIcons("production"));
        icons.put("effect", getCustomIcons("utilities"));
        icons.put("logic", getCustomIcons("logic"));
        isLoaded = true;
    }

    public <T extends Drawable> T getCustomIcons(String name) {
        return Core.atlas.getDrawable("example-java-mod-" + name);
    }

}
