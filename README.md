# Hex Research

An addon to Hex Casting that provides alternative ways to accomplish Hexcasting progression.

The general philosophy is that there should be a tedious, but benign way to accomplish any given goal, as well as a faster route, if one is willing to abuse friendly NPCs.

The documentation may be found on the web at https://dashkal16.github.io/HexResearch/v/latest/main/en_us/

## New Spells

### Thought Sieve

Finds the correct stroke order of a Great Spell by filtering possibilities through the mind of a villager. May have adverse effects on the villager.

### Imbue Mind (Great)

Imbues a mind formed by a Cognitive Inducer into a block. Follows the Flay Mind recipes.

## New Block

### Cognitive Inducer

Creates an artificial mind by induction from nearby villagers. Used by the new Imbue Mind spell.

## Requirements

* Minecraft: 1.19.2
* Forge: 1.19.2-43.2.11
* Fabric: 0.14.21
* Hex Casting: 0.10.3
* Patchouli: 1.19.2-77

Note: Some of the above have dependencies of their own

## Troubleshooting

* I can't find scrolls or sift for Imbue Mind!
  * Adding a great spell to an existing world does not automatically generate the per-world pattern
  * As an operator or at the server console, run `/hexcasting recalcPatterns`

## Future Plans

* Programmable Mind
  * A specialized artificial mind that may be encoded with a hex
  * Can be loaded into a flayed villager or a slate golem
  * May cast hexes, but draws media from their inventory
  * May move, interact with its inventory, fight, etc.
* Reprogrammed Villager
  * Repurposed flayed villager housing a programmable mind
  * Can wear armor
* Slate Golem
  * Artificial construct meant to house programmable minds
  * Cannot wear armor
    * Has natural armor equivalent to iron
    * May allow upgraded parts to improve stats
  * Does not naturally regenerate (use the regen zenith to heal)
