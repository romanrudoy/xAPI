package ru.devoir.commons.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

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

}
