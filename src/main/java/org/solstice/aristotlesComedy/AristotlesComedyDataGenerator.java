package org.solstice.aristotlesComedy;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.LecternBlock;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.content.block.*;
import org.solstice.euclidsElements.autoDatagen.api.generator.*;
import org.solstice.euclidsElements.autoDatagen.api.supplier.BlockModelSupplier;

import java.util.Optional;

public class AristotlesComedyDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(AutoLanguageGenerator::new);
		pack.addProvider(AutoModelGenerator::new);
		pack.addProvider(AutoLootTableGenerator::new);

		BlockModelSupplier.register(LecternBlock.class, AristotlesComedyDataGenerator::registerRotatable);
		BlockModelSupplier.register(TankBlock.class, AristotlesComedyDataGenerator::registerTank);
		BlockModelSupplier.register(PipeBlock.class, AristotlesComedyDataGenerator::registerPipe);
		BlockModelSupplier.register(ClusterBlock.class, AristotlesComedyDataGenerator::registerCluster);
		BlockModelSupplier.register(BrazierBlock.class, AristotlesComedyDataGenerator::registerBrazier);
	}

	public static void registerRotatable(BlockStateModelGenerator generator, Block block, Identifier id) {
		generator.registerNorthDefaultHorizontalRotation(block);
		generator.registerParentedItemModel(block, id);
	}

	public static void registerPipe(BlockStateModelGenerator generator, Block block, Identifier id) {
		MultipartBlockStateSupplier supplier = MultipartBlockStateSupplier.create(block)
			.with(When.create().set(PipeBlock.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, id.withSuffixedPath("/positive")).put(VariantSettings.X, VariantSettings.Rotation.R270))
			.with(When.create().set(PipeBlock.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, id.withSuffixedPath("/negative")).put(VariantSettings.X, VariantSettings.Rotation.R90))
			.with(When.create().set(PipeBlock.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, id.withSuffixedPath("/positive")))
			.with(When.create().set(PipeBlock.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, id.withSuffixedPath("/positive")).put(VariantSettings.Y, VariantSettings.Rotation.R90))
			.with(When.create().set(PipeBlock.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, id.withSuffixedPath("/negative")).put(VariantSettings.Y, VariantSettings.Rotation.R180))
			.with(When.create().set(PipeBlock.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, id.withSuffixedPath("/negative")).put(VariantSettings.Y, VariantSettings.Rotation.R270));

		generator.blockStateCollector.accept(supplier);
		generator.registerParentedItemModel(block, id);
	}

	public static void registerTank(BlockStateModelGenerator generator, Block block, Identifier id) {
		Identifier blockId = id.withPrefixedPath("block/");

		Models.CUBE_COLUMN.upload(
			blockId.withSuffixedPath("/default"),
			TextureMap.sideEnd(blockId.withSuffixedPath("/default"), blockId.withSuffixedPath("/end")),
			generator.modelCollector
		);
		Models.CUBE_COLUMN.upload(
			blockId.withSuffixedPath("/top"),
			TextureMap.sideEnd(blockId.withSuffixedPath("/top"), blockId.withSuffixedPath("/end")),
			generator.modelCollector
		);
		Models.CUBE_COLUMN.upload(
			blockId.withSuffixedPath("/middle"),
			TextureMap.sideEnd(blockId.withSuffixedPath("/middle"), blockId.withSuffixedPath("/end")),
			generator.modelCollector
		);
		Models.CUBE_COLUMN.upload(
			blockId.withSuffixedPath("/bottom"),
			TextureMap.sideEnd(blockId.withSuffixedPath("/bottom"), blockId.withSuffixedPath("/end")),
			generator.modelCollector
		);

		VariantsBlockStateSupplier supplier = VariantsBlockStateSupplier.create(block).coordinate(
			BlockStateVariantMap.create(TankBlock.TOP, TankBlock.BOTTOM)
				.register(true, true, BlockStateVariant.create()
					.put(VariantSettings.MODEL, blockId.withSuffixedPath("/default")))
				.register(true, false, BlockStateVariant.create()
					.put(VariantSettings.MODEL, blockId.withSuffixedPath("/top")))
				.register(false, true, BlockStateVariant.create()
					.put(VariantSettings.MODEL, blockId.withSuffixedPath("/bottom")))
				.register(false, false, BlockStateVariant.create()
					.put(VariantSettings.MODEL, blockId.withSuffixedPath("/middle")))
		);

		generator.blockStateCollector.accept(supplier);
		generator.registerParentedItemModel(block, blockId.withSuffixedPath("/default"));
	}

	public static void registerBrazier(BlockStateModelGenerator generator, Block block, Identifier id) {
		Identifier blockId = id.withPrefixedPath("block/");

		Model litModel = new Model(
			Optional.of(AristotlesComedy.of("block/template/brazier")),
			Optional.empty(),
			TextureKey.FIRE
		);
		TextureMap litTextures = new TextureMap()
			.put(TextureKey.FIRE, blockId.withSuffixedPath("_fire"));
		litModel.upload(block, litTextures, generator.modelCollector);

		Model offModel = new Model(
			Optional.of(AristotlesComedy.of("block/template/brazier")),
			Optional.empty(),
			TextureKey.FIRE
		);
		TextureMap offTextures = new TextureMap()
			.put(TextureKey.FIRE, AristotlesComedy.of("block/empty"));
		offModel.upload(block, "_off", offTextures, generator.modelCollector);

		VariantsBlockStateSupplier supplier = VariantsBlockStateSupplier.create(block).coordinate(
			BlockStateVariantMap.create(Properties.LIT)
				.register(true, BlockStateVariant.create()
					.put(VariantSettings.MODEL, blockId))
				.register(false, BlockStateVariant.create()
					.put(VariantSettings.MODEL, blockId.withSuffixedPath("_off")))
		);

		generator.blockStateCollector.accept(supplier);
		generator.registerParentedItemModel(block, id);
	}

	public static void registerCluster(BlockStateModelGenerator generator, Block block, Identifier id) {
		BlockStateVariant variant = BlockStateVariant.create()
			.put(VariantSettings.MODEL, Models.CROSS.upload(block, TextureMap.cross(block), generator.modelCollector));

		BlockStateVariantMap stageVariantMap = BlockStateVariantMap.create(ClusterBlock.STAGE).register(i -> {
			Identifier identifier = generator.createSubModel(block, "/" + i, Models.CROSS, TextureMap::cross);
			return BlockStateVariant.create().put(VariantSettings.MODEL, identifier);
		});

		VariantsBlockStateSupplier supplier = VariantsBlockStateSupplier
			.create(block, variant)
			.coordinate(generator.createUpDefaultFacingVariantMap())
			.coordinate(stageVariantMap);
		generator.blockStateCollector.accept(supplier);

		Models.GENERATED.upload(id.withPrefixedPath("item/"), TextureMap.layer0(id.withPrefixedPath("block/").withSuffixedPath("/0")), generator.modelCollector);
	}

}
