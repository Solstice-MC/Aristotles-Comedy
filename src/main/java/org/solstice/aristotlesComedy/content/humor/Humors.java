package org.solstice.aristotlesComedy.content.humor;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.registry.Registry;
import org.solstice.aristotlesComedy.registry.ModRegistries;

public class Humors {

    public static void init() {}

	public static final Humor EMPTY = register("empty", new Humor());
	public static final Humor BLOOD = register("blood", new Humor());
    public static final Humor YELLOW_BILE = register("yellow_bile", new Humor());
    public static final Humor BLACK_BILE = register("black_bile", new Humor());
    public static final Humor PHLEGHM = register("phleghm", new Humor());

    public static Humor register(String name, Humor humor) {
        return Registry.register(ModRegistries.HUMOR, AristotlesComedy.of(name), humor);
    }

}
