package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.humor.Humor;
import net.minecraft.registry.Registry;

public class AristotlesHumors {

    public static void init() {}

	public static final Humor EMPTY = register("empty", new Humor());
	public static final Humor BILE = register("bile", new Humor());
	public static final Humor BLOOD = register("blood", new Humor());
	public static final Humor PHLEGHM = register("phleghm", new Humor());
	public static final Humor FIAL = register("fial", new Humor());

    public static Humor register(String name, Humor humor) {
        return Registry.register(AristotlesRegistries.HUMOR, AristotlesComedy.of(name), humor);
    }

}
