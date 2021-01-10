package ca.concordia.mccord.discord.commands;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.mccord.utils.StringUtils;
import net.dv8tion.jda.api.entities.Message;
import net.minecraft.util.text.ITextComponent;

public abstract class Command {
    public static String COMMAND_PREFIX = "!";

    protected abstract String commandKey();
    public abstract ITextComponent execute(Message message) throws Exception;

    public static boolean isCommand(String message) {
        return message.startsWith(COMMAND_PREFIX);
    }

    /**
     * Returns argument list from message.
     * @param message Message to parse.
     * @return List of arguments.
     */
    protected static List<String> getArguments(String message) {
        List<String> tokens = StringUtils.tokenize(message);

        if(tokens.size() < 1) {
            return new ArrayList<String>();
        }

        return tokens.subList(1, tokens.size());
    }
}