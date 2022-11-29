package ru.devoir.commons.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemUtil {
    private ItemStack itemstack;
    private ItemMeta itemMeta;

    public ItemUtil() {
        this.itemstack = new ItemStack(Material.BEDROCK);
        this.itemMeta = this.itemstack.getItemMeta();
    }

    public ItemUtil(Material material) {
        this.itemstack = new ItemStack(material);
        this.itemMeta = this.itemstack.getItemMeta();
    }

    public ItemUtil(Material material, int amount) {
        this.itemstack = new ItemStack(material, amount);
        this.itemMeta = this.itemstack.getItemMeta();
    }

    public ItemUtil(Material material, int amount, int data) {
        this.itemstack = new ItemStack(material, amount, (short)data);
        this.itemMeta = this.itemstack.getItemMeta();
    }

    public ItemUtil(ItemStack itemstack) {
        this.itemstack = itemstack;
        this.itemMeta = itemstack.getItemMeta();
    }

    public ItemUtil(ItemUtil clone) {
        this.itemstack = clone.build().clone();
        this.itemMeta = this.itemstack.getItemMeta();
    }

    private void update(ItemStack itemStack) {
        itemStack.setItemMeta(this.itemMeta);
        this.itemstack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemUtil setItem(ItemStack itemstack) {
        this.itemstack = itemstack;
        this.itemMeta = itemstack.getItemMeta();
        return this;
    }

    public ItemUtil makeSkull(String skullowner) {

        Material material = Material.getMaterial("SKULL_ITEM");
        if ( material == null ) {
            material = Material.getMaterial("PLAYER_HEAD");
        }

        this.itemstack = new ItemStack(material, 1, (short) 3);

        if(skullowner == null || skullowner.isEmpty()){
            this.update(this.itemstack);
            return this;
        }

        this.itemMeta = this.itemstack.getItemMeta();
        SkullMeta skullMeta = (SkullMeta)this.itemMeta;
        skullMeta.setOwner(skullowner);
        this.update(this.itemstack);
        return this;
    }

    public List<String> getLore() {
        return (this.itemMeta != null && this.itemMeta.getLore() != null ? this.itemMeta.getLore() : new ArrayList<>());
    }

    public ItemUtil setLore(String... lines) {
        return setLore(Arrays.asList(lines));
    }

    public ItemUtil setLore(List<String> lines) {
        List<String> lore = new ArrayList<>();

        for (String line : lines) {
            lore.add("§f" + line.replace('&', '§'));
        }

        this.itemMeta.setLore(lore);
        return this;
    }

    public ItemUtil addLore(List<String> add) {
        List<String> lore = this.itemMeta != null && this.itemMeta.getLore() != null ? this.itemMeta.getLore() : new ArrayList();
        lore.addAll(add);
        this.setLore(lore);
        return this;
    }

    public ItemUtil setDurability(int data) {
        this.itemstack.setDurability((short)data);
        this.update(this.itemstack);
        return this;
    }

    public ItemUtil setTypeById(int id) {
        this.itemstack.setTypeId(id);
        this.update(this.itemstack);
        return this;
    }

    public ItemUtil setType(String string) {
        this.itemstack.setType(this.getMaterial(string.toUpperCase()));
        this.update(this.itemstack);
        return this;
    }

    public ItemUtil setColor(Color color) {
        try {
            LeatherArmorMeta meta = (LeatherArmorMeta)this.itemMeta;
            meta.setColor(color);
            this.itemstack.setItemMeta(meta);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return this;
    }

    public String parseString(ItemUtil item) {
        return this.parseString(item.build());
    }

    public String parseString(ItemStack item) {
        StringBuilder stringBuilder = new StringBuilder(item.getType().name() + ":" + item.getAmount() + ":" + item.getDurability());
        if (item.getItemMeta().hasEnchants()) {
            stringBuilder.append(":");

            for (Enchantment enchantment : item.getItemMeta().getEnchants().keySet()) {
                stringBuilder.append(enchantment.getName()).append("-").append(item.getItemMeta().getEnchants().get(enchantment)).append(",");
            }

            stringBuilder.substring(0, stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

    public ItemUtil makeProfileSkull(String skullhash) {

        Material material = Material.getMaterial("SKULL_ITEM");

        if ( material == null ) {
            material = Material.getMaterial("PLAYER_HEAD");
        }

        this.itemstack = new ItemStack(material, 1, (short) 3);

        if(skullhash == null || skullhash.isEmpty()){
            this.update(this.itemstack);
            return this;
        }

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = gameProfile.getProperties();
        propertyMap.put("textures", new Property("textures", skullhash));
        this.itemMeta = this.itemstack.getItemMeta();
        SkullMeta skullMeta = (SkullMeta)this.itemMeta;
        Class csMeta = skullMeta.getClass();

        try {
            Field fp = csMeta.getDeclaredField("profile");
            fp.setAccessible(true);
            fp.set(skullMeta, gameProfile);
            fp.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException var7) {
            var7.printStackTrace();
        }

        this.itemMeta = skullMeta;
        return this;
    }

    public ItemUtil addEnchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemUtil removeEnchant(Enchantment enchantment) {
        this.itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemUtil addFlag(ItemFlag flag) {
        this.itemMeta.addItemFlags(flag);
        return this;
    }

    public ItemUtil setUnbreakable(boolean bool) {
        this.itemMeta.spigot().setUnbreakable(bool);
        return this;
    }

    public ItemStack build() {
        this.itemstack.setItemMeta(this.itemMeta);
        this.update(this.itemstack);
        return this.itemstack.clone();
    }

    public Material getMaterial(String string) {
        string = string.toUpperCase();
        return NumberUtils.isDigits(string) ? Material.getMaterial(Integer.parseInt(string)) : Material.getMaterial(string);
    }

    public int getAmount() {
        return this.itemstack.getAmount();
    }

    public ItemUtil setAmount(int count) {
        this.itemstack.setAmount(count);
        this.update(this.itemstack);
        return this;
    }

    public String getNamed() {
        return this.itemMeta.getDisplayName();
    }

    public ItemUtil setNamed(String named) {
        this.itemMeta.setDisplayName(named.replace('&', '§'));
        return this;
    }

    private Enchantment getEnchant(String string) {
        string = string.toUpperCase();
        return Enchantment.getByName(string);
    }

    public ItemUtil placeholder(String placeholder, Object replacement) {
        List<String> lore = new ArrayList<>();
        if (this.itemMeta != null && this.itemMeta.getLore() != null) {

            for (String string : this.itemMeta.getLore()) {
                lore.add(string.replace(placeholder, replacement.toString()));
            }
        }

        this.setLore(lore);
        this.setNamed(this.getNamed().replace(placeholder, replacement.toString()));
        return this;
    }

    public Material getType() {
        return this.itemstack.getType();
    }

    public ItemUtil setType(Material material) {
        this.itemstack.setType(material);
        this.update(this.itemstack);
        return this;
    }

    public ItemMeta getMeta() {
        return this.itemMeta;
    }

    public void setMeta(ItemMeta meta) {
        this.itemMeta = meta;
    }
    private Integer hasNumb(String string) {
        return NumberUtils.isDigits(string) ? Integer.parseInt(string) : 0;
    }

    public ItemUtil glowing() {
        if (!this.itemMeta.hasEnchant(Enchantment.ARROW_INFINITE)) {
            this.addEnchant(Enchantment.ARROW_INFINITE, 1);
        } else {
            this.removeEnchant(Enchantment.ARROW_INFINITE);
        }

        this.addFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public short getData() {
        return this.itemstack.getDurability();
    }
}
