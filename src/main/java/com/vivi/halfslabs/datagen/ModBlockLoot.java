package com.vivi.halfslabs.datagen;

import com.vivi.halfslabs.HalfSlabBlock;
import com.vivi.halfslabs.HalfSlabBlock.HalfSlabType;
import com.vivi.halfslabs.Registration;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLoot extends BlockLoot {


    @Override
    protected void addTables() {
        Registration.BLOCKS.getEntries().forEach(block -> {
            this.add(block.get(), ModBlockLoot::halfSlab);
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }


    private static LootTable.Builder halfSlab(Block block) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(
                                applyExplosionDecay(
                                        block, LootItem.lootTableItem(block)
                                        .apply(
                                                SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                                .when(
                                                        AlternativeLootItemCondition.alternative(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(
                                                                        StatePropertiesPredicate.Builder.properties()
                                                                        .hasProperty(HalfSlabBlock.TYPE, HalfSlabType.TOP_TWO)
                                                                ),
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(
                                                                        StatePropertiesPredicate.Builder.properties()
                                                                        .hasProperty(HalfSlabBlock.TYPE, HalfSlabType.MIDDLE_TWO)
                                                                ),
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(
                                                                        StatePropertiesPredicate.Builder.properties()
                                                                        .hasProperty(HalfSlabBlock.TYPE, HalfSlabType.BOTTOM_TWO)
                                                                )
                                                        )
                                                )
                                        )
                                        .apply(
                                                SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))
                                                .when(
                                                        AlternativeLootItemCondition.alternative(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(
                                                                        StatePropertiesPredicate.Builder.properties()
                                                                        .hasProperty(HalfSlabBlock.TYPE, HalfSlabType.TOP_THREE)
                                                                ),
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(
                                                                        StatePropertiesPredicate.Builder.properties()
                                                                        .hasProperty(HalfSlabBlock.TYPE, HalfSlabType.BOTTOM_THREE)
                                                                )
                                                        )
                                                )
                                        )
                                        .apply(
                                                SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(
                                                                StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(HalfSlabBlock.TYPE, HalfSlabType.FULL)
                                                        )
                                                )
                                        )
                                )
                        )
                )
        ;
    }
}



/*
{
    "type": "minecraft:block",
    "pools": [
        {
            "bonus_rolls": 0.0,
            "entries": [
                {
                    "type": "minecraft:item",
                    "functions": [
                        {
                            "add": false,
                            "conditions": [
                                {
                                    "condition": "alternative",
                                    "terms": [
                                        {
                                            "block": "half_slabs:oak_half_slab",
                                            "condition": "block_state_property",
                                            "properties": {
                                                "type": "top_two"
                                            }
                                        },
                                        {
                                            "block": "half_slabs:oak_half_slab",
                                            "condition": "block_state_property",
                                            "properties": {
                                                "type": "middle_two"
                                            }
                                        },
                                        {
                                            "block": "half_slabs:oak_half_slab",
                                            "condition": "block_state_property",
                                            "properties": {
                                                "type": "bottom_two"
                                            }
                                        }
                                    ]
                                }
                            ],
                            "count": 2.0,
                            "function": "minecraft:set_count"
                        },
                        {
                            "add": false,
                            "conditions": [
                                {
                                    "condition": "alternative",
                                    "terms": [
                                        {
                                            "block": "half_slabs:oak_half_slab",
                                            "condition": "block_state_property",
                                            "properties": {
                                                "type": "top_three"
                                            }
                                        },
                                        {
                                            "block": "half_slabs:oak_half_slab",
                                            "condition": "block_state_property",
                                            "properties": {
                                                "type": "bottom_three"
                                            }
                                        }
                                    ]
                                }
                            ],
                            "count": 3.0,
                            "function": "minecraft:set_count"
                        },
                        {
                            "add": false,
                            "conditions": [
                                {
                                    "block": "half_slabs:oak_half_slab",
                                    "condition": "block_state_property",
                                    "properties": {
                                        "type": "full"
                                    }
                                }
                            ],
                            "count": 4.0,
                            "function": "minecraft:set_count"
                        },
                        {
                            "function": "minecraft:explosion_decay"
                        }
                    ],
                    "name": "half_slabs:oak_half_slab"
                }
            ],
            "rolls": 1
        }
    ],
    "random_sequence": "half_slabs:blocks/oak_half_slab"
}
 */