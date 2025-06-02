package org.solstice.aristotlesComedy.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.LecternBlock;
import net.minecraft.data.client.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.block.*;
import org.solstice.aristotlesComedy.content.block.tank.StackingTankBlock;
import org.solstice.aristotlesComedy.content.block.tank.TankBlock;
import org.solstice.euclidsElements.autoDatagen.api.generator.*;
import org.solstice.euclidsElements.autoDatagen.api.supplier.BlockModelSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AristotlesComedyDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(AutoLanguageGenerator::new);
		pack.addProvider(AutoModelGenerator::new);
		pack.addProvider(AutoLootTableGenerator::new);

		BlockModelSupplier.register(LecternBlock.class, AristotlesComedyDataGenerator::registerRotatable);
		BlockModelSupplier.register(TankBlock.class, AristotlesComedyDataGenerator::registerCubeTop);
		BlockModelSupplier.register(StackingTankBlock.class, AristotlesComedyDataGenerator::registerTank);
		BlockModelSupplier.register(PipeBlock.class, AristotlesComedyDataGenerator::registerPipe);
		BlockModelSupplier.register(ClusterBlock.class, AristotlesComedyDataGenerator::registerCluster);
		BlockModelSupplier.register(BrazierBlock.class, AristotlesComedyDataGenerator::registerBrazier);
	}

	@Deprecated
	public static void registerCubeTop(BlockStateModelGenerator generator, Block block, Identifier id) {
		generator.registerSingleton(block, TexturedModel.CUBE_TOP);
		generator.registerParentedItemModel(block, id);
	}

	public static void registerRotatable(BlockStateModelGenerator generator, Block block, Identifier id) {
		generator.registerNorthDefaultHorizontalRotation(block);
		generator.registerParentedItemModel(block, id);
	}

	public static void registerPipe(BlockStateModelGenerator generator, Block block, Identifier id) {
		Identifier blockId = id.withPrefixedPath("block/");

		TextureMap texture = new TextureMap()
			.put(TextureKey.TEXTURE, blockId);

		Model connectorModel = new Model(
			Optional.of(AristotlesComedy.of("block/template/pipe/connector")),
			Optional.empty(),
			TextureKey.TEXTURE
		);
		Model positiveModel = new Model(
			Optional.of(AristotlesComedy.of("block/template/pipe/positive")),
			Optional.empty(),
			TextureKey.TEXTURE
		);
		Model negativeModel = new Model(
			Optional.of(AristotlesComedy.of("block/template/pipe/negative")),
			Optional.empty(),
			TextureKey.TEXTURE
		);
		Model straightModel = new Model(
			Optional.of(AristotlesComedy.of("block/template/pipe/straight")),
			Optional.empty(),
			TextureKey.TEXTURE
		);

		Identifier connectorId = connectorModel.upload(block, "/connector", texture, generator.modelCollector);
		Identifier positiveId = positiveModel.upload(block, "/positive", texture, generator.modelCollector);
		Identifier negativeId = negativeModel.upload(block, "/negative", texture, generator.modelCollector);
		Identifier straightId = straightModel.upload(block, "/straight", texture, generator.modelCollector);

		MultipartBlockStateSupplier supplier = MultipartBlockStateSupplier.create(block)
			.with(getPipeConnectorConditions(), BlockStateVariant.create().put(VariantSettings.MODEL, connectorId))
			.with(When.create().set(PipeBlock.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, positiveId).put(VariantSettings.X, VariantSettings.Rotation.R270))
			.with(When.create().set(PipeBlock.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, negativeId).put(VariantSettings.X, VariantSettings.Rotation.R90))
			.with(When.create().set(PipeBlock.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, positiveId))
			.with(When.create().set(PipeBlock.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, positiveId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
			.with(When.create().set(PipeBlock.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, negativeId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
			.with(When.create().set(PipeBlock.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, negativeId).put(VariantSettings.Y, VariantSettings.Rotation.R270));

		generator.blockStateCollector.accept(supplier);

		generator.registerParentedItemModel(block, straightId);
	}

	public static When getPipeConnectorConditions() {
		List<When> connectorConditions = new ArrayList<>();

		for (Direction vertical : Direction.Type.VERTICAL) {
			BooleanProperty verticalProperty = PipeBlock.DIRECTION_PROPERTIES.get(vertical);
			for (Direction horizontal : Direction.Type.HORIZONTAL) {
				BooleanProperty horizontalProperty = PipeBlock.DIRECTION_PROPERTIES.get(horizontal);
				connectorConditions.add(When.allOf(When.create().set(verticalProperty, true), When.create().set(horizontalProperty, true)));
			}
		}

		connectorConditions.add(When.allOf(When.create().set(PipeBlock.NORTH, true), When.create().set(PipeBlock.EAST, true)));
		connectorConditions.add(When.allOf(When.create().set(PipeBlock.EAST, true), When.create().set(PipeBlock.SOUTH, true)));
		connectorConditions.add(When.allOf(When.create().set(PipeBlock.SOUTH, true), When.create().set(PipeBlock.WEST, true)));
		connectorConditions.add(When.allOf(When.create().set(PipeBlock.WEST, true), When.create().set(PipeBlock.NORTH, true)));

		List<When> allOfConnections = new ArrayList<>();
		for (BooleanProperty property : PipeBlock.DIRECTION_PROPERTIES.values()) {
			allOfConnections.add(When.create().set(property, false));
		}
		connectorConditions.add(When.allOf(allOfConnections.toArray(When[]::new)));

		return When.anyOf(connectorConditions.toArray(When[]::new));
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
			BlockStateVariantMap.create(StackingTankBlock.TOP, StackingTankBlock.BOTTOM)
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
