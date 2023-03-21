package com.babijon.commons.item;

import com.babijon.commons.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class ItemUtils {

    public static String itemToBase64(ItemStack itemStack) {
        if (itemStack == null) {
            return "null";
        } else {
            byte[] itemBytes;
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Throwable var3 = null;

                try {
                    BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
                    Throwable var5 = null;

                    try {
                        dataOutput.writeObject(itemStack);
                        itemBytes = outputStream.toByteArray();
                    } catch (Throwable var30) {
                        var5 = var30;
                        throw var30;
                    } finally {
                        if (dataOutput != null) {
                            if (var5 != null) {
                                try {
                                    dataOutput.close();
                                } catch (Throwable var29) {
                                    var5.addSuppressed(var29);
                                }
                            } else {
                                dataOutput.close();
                            }
                        }

                    }
                } catch (Throwable var32) {
                    var3 = var32;
                    throw var32;
                } finally {
                    if (outputStream != null) {
                        if (var3 != null) {
                            try {
                                outputStream.close();
                            } catch (Throwable var28) {
                                var3.addSuppressed(var28);
                            }
                        } else {
                            outputStream.close();
                        }
                    }

                }
            } catch (IOException var34) {
                throw new IllegalStateException("Unexpected exception due write ItemStack to BukkitObjectOutputStream", var34);
            }

            return Base64.getEncoder().encodeToString(itemBytes);
        }
    }

    public static ItemStack itemFromBase64(String data) {
        if ("null".equals(data)) {
            return null;
        } else {
            byte[] decodedBytes = Base64.getDecoder().decode(data);

            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
                Throwable var3 = null;

                Object var6;
                try {
                    BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
                    Throwable var5 = null;

                    try {
                        var6 = (ItemStack)dataInput.readObject();
                    } catch (Throwable var31) {
                        var6 = var31;
                        var5 = var31;
                        throw var31;
                    } finally {
                        if (dataInput != null) {
                            if (var5 != null) {
                                try {
                                    dataInput.close();
                                } catch (Throwable var30) {
                                    var5.addSuppressed(var30);
                                }
                            } else {
                                dataInput.close();
                            }
                        }

                    }
                } catch (Throwable var33) {
                    var3 = var33;
                    throw var33;
                } finally {
                    if (inputStream != null) {
                        if (var3 != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable var29) {
                                var3.addSuppressed(var29);
                            }
                        } else {
                            inputStream.close();
                        }
                    }

                }

                return (ItemStack)var6;
            } catch (IOException | ClassNotFoundException var35) {
                throw new IllegalStateException("Unexpected exception due read ItemStack from BukkitObjectInputStream", var35);
            }
        }
    }

    public static ItemUtil getItemFromConfig(FileConfiguration config, String key) {

        ItemUtil itemUtil;

        int amount = config.getConfigurationSection(key).getKeys(false).contains("amount") ?
                config.getInt(key + ".amount") : 1;
        int modelData = config.getConfigurationSection(key).getKeys(false).contains("custom-model-data") ?
                config.getInt(key + ".custom-model-data") : 1;

        String material = config.getString(key + ".material");
        if (material.length() > 30)
            itemUtil = new ItemUtil().makeProfileSkull(material);
        else
            itemUtil = new ItemUtil(Material.matchMaterial(material), amount, modelData);

        String name = MessageUtil.colorize(config.getString(key + ".name"));
        List<String> lore = MessageUtil.getColorList(config.getStringList(key + ".lore"));

        itemUtil.setNamed(name);
        itemUtil.setLore(lore);

        if (config.getConfigurationSection(key).getKeys(false).contains("enchantments")) {
            for (String ench : config.getStringList(key + ".enchantments")) {
                itemUtil.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(ench.split(";")[0])),
                        Integer.parseInt(ench.split(";")[1]));
            }
        }

        if (config.getConfigurationSection(key).getKeys(false).contains("flags")) {
            for (String flag : config.getStringList(key + ".flags")) {
                itemUtil.addFlag(ItemFlag.valueOf(flag));
            }
        }

        return itemUtil;

    }

}
