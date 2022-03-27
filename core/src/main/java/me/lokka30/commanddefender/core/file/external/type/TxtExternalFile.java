package me.lokka30.commanddefender.core.file.external.type;

import org.jetbrains.annotations.NotNull;

public interface TxtExternalFile extends ExternalFile {

    @NotNull
    @Override
    default String nameWithExtension() {
        return nameWithoutExtension() + ".txt";
    }

}