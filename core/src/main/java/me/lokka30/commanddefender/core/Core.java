package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalLogger;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public interface Core {

    @NotNull UniversalLogger logger();

    @NotNull String colorize(final @NotNull String msg);

    @NotNull FileHandler fileHandler();

    @NotNull HashSet<ConditionHandler> conditionHandlers();
    @NotNull HashSet<ActionHandler> actionHandlers();
    @NotNull HashSet<OptionHandler> optionHandlers();

    void updateTabCompletionForAllPlayers();

}