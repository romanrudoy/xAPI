package com.babijon.commons.placeholders;

import com.mojang.datafixers.util.Pair;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class QuickPlaceholder {

    private String identifier;
    private String author;
    private String version;

    private Function<Pair<Player, String>, String> placeholder;

    public QuickPlaceholder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public QuickPlaceholder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public QuickPlaceholder withVersion(String version) {
        this.version = version;
        return this;
    }

    public QuickPlaceholder withPlaceholder(Function<Pair<Player, String>, String> placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public void register() {
        new PlaceholderExpansion() {

            @Override
            public @org.jetbrains.annotations.NotNull String getIdentifier() {
                return identifier;
            }

            @Override
            public @org.jetbrains.annotations.NotNull String getAuthor() {
                return author;
            }

            @Override
            public @org.jetbrains.annotations.NotNull String getVersion() {
                return version;
            }

            @Override
            public boolean persist() {
                return true;
            }

            @Override
            public boolean canRegister() {
                return true;
            }

            @Override
            public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
                return placeholder.apply(Pair.of(player, params));
            }

        }.register();
    }

}
