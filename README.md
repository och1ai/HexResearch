# Hex Research

An addon to Hex Casting that provides alternative ways to accomplish Hexcasting progression.

The general philosophy is that there should be a tedious, but benign way to accomplish any given goal, as well as a faster route, if one is willing to abuse friendly NPCs.

The documentation may be found on the web at https://dashkal16.github.io/HexResearch/v/latest/main/en_us/

## Requirements

### Mandatory
* Minecraft: 1.19.2
* Forge: 1.19.2-43.2.11
* Fabric: 0.14.21
* Hex Casting: 0.10.3
* Patchouli: 1.19.2-77

### Optional
* HexGloop: 1.19.2-0.2.0

Note: Some of the above have dependencies of their own

## Content

### New Spells

#### Thought Sieve

Finds the correct stroke order of a Great Spell by filtering possibilities through the mind of a villager. May have adverse effects on the villager.

#### Imbue Mind (Great)

Imbues a mind formed by a Cognitive Inducer into a block. Follows the Flay Mind recipes.

### New Block

#### Cognitive Inducer

Creates an artificial mind by induction from nearby villagers. Used by the new Imbue Mind spell.

## Troubleshooting

* I can't find scrolls or sift for Imbue Mind!
  * Adding a great spell to an existing world does not automatically generate the per-world pattern
  * As an operator or at the server console, run `/hexcasting recalcPatterns`

## Building

This project uses the Architectury Loom suite.

To build, run:

Linux or similar: `./gradlew clean build`

Windows: `.\gradlew.bat clean build`

The mod jars will appear in `forge/build/libs` and `fabric/build/libs`
