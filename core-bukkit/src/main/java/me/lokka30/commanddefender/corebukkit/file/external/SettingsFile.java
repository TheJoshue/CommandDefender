package me.lokka30.commanddefender.corebukkit.file.external;

import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.lokka30.commanddefender.corebukkit.BukkitCore;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SettingsFile implements YamlExternalFile {

    private Yaml data;
    @Override
    public Yaml data() {
        return data;
    }

    @Override
    public @NotNull String nameWithoutExtension() {
        return "settings";
    }

    @Override
    public @NotNull String resourcePath() {
        return nameWithExtension();
    }

    @Override
    public void load(boolean fromReload) {
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
    }

    @Override
    public void migrate() {
        if(getInstalledVersion() == getCurrentVersion()) return;

        if(getInstalledVersion() < 12345) {
            BukkitCore.getInstance().logger().warning(
                    "Your '&b" + nameWithExtension() + "&7' file is too old to be migrated. It has been " +
                            "backed up. CommandDefender is now using the default latest file instead. " +
                            "Edit this file with any of the changes you wish ASAP.");
            backup();
            replaceWithDefault();
            return;
        }

        for(int i = getInstalledVersion(); i < getCurrentVersion(); i++) {
            BukkitCore.getInstance().logger().info("Attempting to migrate file '&b" + nameWithExtension() + "&7' from version &b" + getInstalledVersion() + "&7 to &b" + i + "&7...");
            switch(getInstalledVersion()) {
                case 999: //TODO
                    break;
                default:
                    BukkitCore.getInstance().logger().error(
                            "No migration logic available for file '&b" + nameWithExtension() + "&7' @ version " +
                                    "&b" + i + "&7. Inform CommandDefender developers ASAP.");
                    return;
            }
            BukkitCore.getInstance().logger().info("Migrated file '&b" + nameWithExtension() + "&7' to version &b" + i + "&7 successfully.");
        }
    }

    @Override
    public int getCurrentVersion() {
        return 9999; //TODO
    }
}