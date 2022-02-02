package me.lokka30.commanddefender.core.filter.set.action.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.util.Message;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class SendMessage implements ActionHandler {

    @Override
    public @NotNull String identifier() {
        return "send-message";
    }

    @Override
    public @NotNull Optional<Action> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        final String path = "actions." + identifier();

        if(!section.contains(path)) {
            return Optional.empty();
        }

        return Optional.of(
                new SendMessageAction(section.getStringList(path))
        );
    }

    public record SendMessageAction(
            @NotNull List<String> messages,
            @NotNull Message.Placeholder... placeholders
    ) implements Action {

        @Override
        public void run(@NotNull UniversalPlayer player) {
            new Message(messages(), placeholders()).send(player);
        }

    }

}