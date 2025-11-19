# Kerpackie's Kitchen

Kerpackie's Kitchen is a Minecraft Forge addon extracted from the `some-assembly-required` project. It provides custom functionality for the game and can be built, tested, and released independently.

## Project Structure

- `src/` – Java source code for the mod.
- `build.gradle` – Gradle build script.
- `.github/workflows/build_release.yml` – GitHub Actions workflow that builds the mod and creates a release when a version tag is pushed.
- `README.md` – This documentation.

## Setup

1. **Clone the repository**
   ```sh
   git clone https://github.com/yourusername/kerpackieskitchen.git
   cd kerpackieskitchen
   ```

2. **Configure dependencies**
   Open `build.gradle` and ensure the `some-assembly-required` dependency is correctly referenced:
   - If the original project is published on CurseForge:
     ```gradle
     implementation fg.deobf("curse.maven:some-assembly-required-XXXXX:YYYYY")
     ```
   - If you have a local copy, publish it to a local Maven repository and add the appropriate `mavenLocal()` reference.

3. **Import into IDE**
   Open the project in your favourite IDE (IntelliJ IDEA, Eclipse, VS Code with Java extensions) and let Gradle import the project.

## Building the Mod

Run the Gradle wrapper to build the JAR:

```sh
./gradlew build
```

The compiled JAR will be located at `build/libs/kerpackieskitchen-<version>.jar`.

## Creating Custom Sandwich Names

Kerpackie's Kitchen allows you to define custom names for sandwiches based on their specific ingredients. Custom sandwich names are loaded **at runtime via Minecraft datapacks**, so you don't need to rebuild the mod to add new sandwich definitions!

### For End Users (Adding Custom Sandwiches)

You can add custom sandwich names to your Minecraft world using a datapack:

1. **Create a datapack folder** in your world's `datapacks` directory:
   ```
   .minecraft/saves/YourWorldName/datapacks/custom_sandwiches/
   ```

2. **Create the datapack structure**:
   ```
   custom_sandwiches/
   ├── pack.mcmeta
   └── data/
       └── kerpackieskitchen/
           └── sandwich_names/
               ├── veggie_delight.json
               ├── blt.json
               └── your_custom_sandwich.json
   ```

3. **Create `pack.mcmeta`** in the datapack root:
   ```json
   {
       "pack": {
           "pack_format": 15,
           "description": "Custom sandwich names for Kerpackie's Kitchen"
       }
   }
   ```
   > **Note**: `pack_format` 15 is for Minecraft 1.20.x. Adjust if using a different version.

4. **Add your sandwich JSON files** (see format below).

5. **Reload datapacks** in-game with `/reload` or restart the world.

### For Mod Developers (Bundling Default Sandwiches)

To include default sandwich names with the mod, place JSON files in:

```
src/main/resources/data/kerpackieskitchen/sandwich_names/
```

These will be bundled with the mod JAR and loaded automatically.

### JSON Format

Each sandwich definition file must follow this structure:

```json
{
    "ingredients": [
        "mod_id:item_id",
        "mod_id:item_id",
        "mod_id:item_id"
    ],
    "name": {
        "text": "Display Name",
        "color": "green",
        "italic": true
    }
}
```

**Fields:**

- **`ingredients`** (required): An array of item resource locations in the format `mod_id:item_id`. List all ingredients that make up the sandwich.
  - **Order doesn't matter** – the mod automatically sorts ingredients when matching.
  - Include bread slices and all fillings.
  
- **`name`** (required): A Minecraft text component defining how the sandwich name appears.
  - **`text`**: The display name of the sandwich.
  - **`color`**: Optional. Text color (e.g., `"green"`, `"gold"`, `"aqua"`).
  - **`italic`**: Optional. Whether the text is italicized (`true` or `false`).
  - **`bold`**: Optional. Whether the text is bold (`true` or `false`).

### Example

Here's an example from `veggie_delight.json`:

```json
{
    "ingredients": [
        "some_assembly_required:bread_slice",
        "some_assembly_required:bread_slice",
        "some_assembly_required:tomato_slices",
        "some_assembly_required:sliced_onion"
    ],
    "name": {
        "text": "Veggie Delight",
        "color": "green",
        "italic": true
    }
}
```

This will display any sandwich containing two bread slices, tomato slices, and sliced onion as **"Veggie Delight"** in green italic text.

### How It Works

1. The `SandwichNameManager` loads all JSON files from the `sandwich_names` directory at game startup and whenever resources are reloaded (e.g., `/reload` command).
2. JSON files are loaded from both the mod's internal resources and any active datapacks.
3. When a sandwich is created, the mod checks if its ingredients match any defined custom names.
4. If a match is found, the custom name is applied; otherwise, the default naming convention is used.
5. Ingredient order doesn't matter – `[bread, tomato, onion]` matches `[onion, bread, tomato]`.

### Testing Your Custom Sandwiches

After adding new sandwich definitions:

1. Use `/reload` in-game to reload datapacks (no restart needed!).
2. Craft a sandwich with the specified ingredients.
3. The custom name should appear when you hover over the sandwich item.

## Continuous Integration

The repository includes a GitHub Actions workflow (`.github/workflows/build_release.yml`) that:

- Triggers on pushes of version tags (`v*.*.*`) and on manual dispatch.
- Sets up JDK 17, caches Gradle dependencies, builds the project, and uploads the JAR as an artifact.
- Automatically creates a GitHub Release with the built JAR attached.

To create a new release:

1. Tag the commit: `git tag v1.0.0 && git push origin v1.0.0`
2. The workflow will run and publish the release.

## Testing

If the project contains unit tests, run them with:

```sh
./gradlew test
```

Tests will execute on the CI workflow as part of the `build` job.

## Contributing

Contributions are welcome! Please fork the repository, make your changes, and open a pull request. Ensure that the CI workflow passes before merging.

## License

This project is licensed under the MIT License – see the `LICENSE` file for details.
