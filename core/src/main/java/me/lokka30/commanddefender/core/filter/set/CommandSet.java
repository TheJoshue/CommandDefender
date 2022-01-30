package me.lokka30.commanddefender.core.filter.set;

import me.lokka30.commanddefender.core.filter.CommandAccessStatus;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.PostProcessOption;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.PreProcessOption;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public record CommandSet(
        /* General */
        @NotNull String identifier,
        @NotNull CommandAccessStatus type,
        @NotNull HashSet<CommandSetPreset> presets,

        /* Conditions */
        @NotNull HashSet<Condition> conditions,
        double conditionsPercentageRequired,

        /* Actions */
        @NotNull HashSet<Action> actions,

        /* Options */
        @NotNull HashSet<PreProcessOption> preProcessOptions,
        @NotNull HashSet<PostProcessOption> postProcessOptions
) {

    // get if a command set wants to allow/deny a command, or if it doesn't care about the command.
    public CommandAccessStatus getAccessStatus(final UniversalPlayer player, final String[] args) {
        int conditionsMet = 0;
        int totalConditions = conditions().size();

        // loop thru conditions
        for(Condition condition : conditions()) {
            if(condition.appliesTo(player, args)) {
                // 0.0 = only one condition is required.
                if(conditionsPercentageRequired() == 0.0) { return type(); }

                // increment conditions met
                conditionsMet++;
            }

            // check if % of conditions met / total conditions matches requirement
            if(((double) conditionsMet / (double) totalConditions) >= conditionsPercentageRequired()) {
                // if enough conditions are met, then return the type, e.g. `"`DENY`.
                return type();
            }
        }

        // command set doesn't want to do anything with this command,
        // so return `UNKNOWN`.
        return CommandAccessStatus.UNKNOWN;
    }
}
