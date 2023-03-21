package com.babijon.commons.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class LocationUtil {

    public Location parseLocation(String string, boolean player) {

        String[] args = string.split(";");

        World world = Bukkit.getWorld(args[0]);
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);

        if (!player) return new Location(world,x,y,z);

        float yaw = Float.parseFloat(args[4]);
        float pitch = Float.parseFloat(args[5]);

        return new Location(world,x,y,z,yaw,pitch);

    }

    public String parseString(Location location, boolean player) {

        StringBuilder builder = new StringBuilder(location.getWorld().getName() + ";" + location.getX() + ";" +
                location.getY() + ";" + location.getZ());
        if (player) builder.append(";").append(location.getYaw()).append(";").append(location.getPitch());

        return builder.toString();

    }

    public Location getCircleLocationX(Location center, double degree, double radius) {

        double x = Math.sin(Math.toRadians(degree)) * radius;
        double y = Math.cos(Math.toRadians(degree)) * radius;

        return center.clone().add(x,y,0);

    }

    public Location getCircleLocationZ(Location center, double degree, double radius) {

        double x = Math.sin(Math.toRadians(degree)) * radius;
        double y = Math.cos(Math.toRadians(degree)) * radius;

        return center.clone().add(0,y,x);

    }

}
