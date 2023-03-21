package com.babijon.commons.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class VersionUtil {

    public int getServerVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        return Integer.parseInt(version.split("_")[1]);
    }

    public boolean isNewVersion() {
        return getServerVersion() >= 16;
    }

}
