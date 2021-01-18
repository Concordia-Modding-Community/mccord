package ca.concordia.discochat.chat;

import ca.concordia.discochat.chat.text.ChannelTextComponent;
import ca.concordia.discochat.chat.text.DiscordTextComponent;
import ca.concordia.discochat.chat.text.FormatTextComponent;
import ca.concordia.discochat.chat.text.UserTextComponent;
import ca.concordia.discochat.entity.ModUser;
import ca.concordia.discochat.utils.IMod;
import ca.concordia.discochat.utils.IModProvider;
import net.dv8tion.jda.api.entities.TextChannel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class ChatMessage implements IModProvider {
    private IMod mod;
    private ModUser user;
    private TextChannel textChannel;
    private DiscordTextComponent message;

    public ChatMessage(IMod mod, ModUser user, TextChannel textChannel, DiscordTextComponent message) {
        this.mod = mod;
        this.user = user;
        this.textChannel = textChannel;
        this.message = message;
    }

    @Override
    public IMod getMod() {
        return mod;
    }

    public String getDiscordText() {
        StringTextComponent discordMessage = new StringTextComponent(message.getDiscordString());

        StringTextComponent discordName = new StringTextComponent(user.getDiscordName());

        return new FormatTextComponent(getMod().getConfigManager().getDiscordTextFormat()).put("m", discordMessage)
                .put("p", discordName).build().getString();
    }

    public ITextComponent getMCText(PlayerEntity playerEntity) {
        UserTextComponent userText = new UserTextComponent(mod, user.getDiscordUUID(), textChannel);

        Style style = Style.EMPTY;

        if (message.getMentionedMCUUID().contains(playerEntity.getUniqueID().toString())) {
            style = style.setColor(Color.fromTextFormatting(TextFormatting.YELLOW));
        }

        FormatTextComponent fullText = new FormatTextComponent(mod.getConfigManager().getMCTextFormat())
                .put("c", new ChannelTextComponent(mod, textChannel)).put("m", message).put("p", userText).build();

        fullText.setStyle(style);

        return fullText;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public ModUser getUser() {
        return this.user;
    }

    public boolean isAuthor(ModUser modUser) {
        return this.user.equals(modUser);
    }
}
