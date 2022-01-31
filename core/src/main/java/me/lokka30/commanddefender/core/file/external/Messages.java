package me.lokka30.commanddefender.core.file.external;

import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.file.external.type.YamlVersionedExternalFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Messages implements YamlVersionedExternalFile {

    private final Core core;
    public Messages(final @NotNull Core core) { this.core = core; }

    private Yaml data;
    @Override
    public Yaml data() {
        return data;
    }

    @Override
    public @NotNull String nameWithoutExtension() {
        return "messages";
    }

    @Override
    public @NotNull String resourcePath() {
        return nameWithExtension();
    }

    @Override
    public void load(boolean fromReload) {
        core.logger().info("Loading file '&b" + nameWithExtension() + "&7'...");
        if (fromReload) {
            data.forceReload();
        } else {
            data = LightningBuilder
                    .fromFile(new File(fullPath()))
                    .setReloadSettings(ReloadSettings.MANUALLY)
                    .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                    .setDataType(DataType.SORTED)
                    .createYaml();
        }
        migrate();
        core.logger().info("Loaded file.");
    }

    @Override
    public void migrate() {
        if(installedVersion() == currentVersion()) return;

        if(installedVersion() < 12345) {
            core.logger().warning(
                    "Your '&b" + nameWithExtension() + "&7' file is too old to be migrated. It has been " +
                            "backed up. CommandDefender is now using the default latest file instead. " +
                            "Edit this file with any of the changes you wish ASAP.");
            backup();
            replaceWithDefault();
            return;
        }

        for(int i = installedVersion(); i < currentVersion(); i++) {
            core.logger().info("Attempting to migrate file '&b" + nameWithExtension() + "&7' from version &b" + installedVersion() + "&7 to &b" + i + "&7...");
            switch(installedVersion()) {
                case 999: //TODO
                    break;
                default:
                    core.logger().error(
                            "No migration logic available for file '&b" + nameWithExtension() + "&7' @ version " +
                                    "&b" + i + "&7. Inform CommandDefender developers ASAP.");
                    return;
            }
            core.logger().info("Migrated file '&b" + nameWithExtension() + "&7' to version &b" + i + "&7 successfully.");
        }
    }

    @Override
    public int currentVersion() {
        return 9999; //TODO
    }
}