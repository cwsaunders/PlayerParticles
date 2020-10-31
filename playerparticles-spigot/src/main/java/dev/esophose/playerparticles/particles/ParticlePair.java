package dev.esophose.playerparticles.particles;

import dev.esophose.playerparticles.PlayerParticles;
import dev.esophose.playerparticles.manager.LocaleManager;
import dev.esophose.playerparticles.manager.ParticleManager;
import dev.esophose.playerparticles.particles.ParticleEffect.ParticleProperty;
import dev.esophose.playerparticles.particles.data.NoteColor;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.particles.data.ParticleColor;
import dev.esophose.playerparticles.styles.DefaultStyles;
import dev.esophose.playerparticles.styles.ParticleStyle;
import dev.esophose.playerparticles.util.ParticleUtils;
import dev.esophose.playerparticles.util.StringPlaceholders;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

public class ParticlePair {

    private UUID ownerUUID;
    private int id;

    private ParticleEffect effect;
    private ParticleStyle style;

    private Material itemMaterial;
    private Material blockMaterial;
    private OrdinaryColor color;
    private NoteColor noteColor;

    public ParticlePair(UUID ownerUUID, int id, ParticleEffect effect, ParticleStyle style, Material itemMaterial, Material blockMaterial, OrdinaryColor color, NoteColor noteColor) {
        this.ownerUUID = ownerUUID;
        this.id = id;

        this.effect = effect;
        this.style = style;

        this.setEffect(effect);
        this.setStyle(style);
        this.setItemMaterial(itemMaterial);
        this.setBlockMaterial(blockMaterial);
        this.setColor(color);
        this.setNoteColor(noteColor);
    }

    /**
     * Updates the particle's owner if it hasn't been set yet
     *
     * @param pplayer The new owner
     */
    public void setOwner(PPlayer pplayer) {
        if (this.ownerUUID == null)
            this.ownerUUID = pplayer.getUniqueId();
    }

    /**
     * Sets the player's particle effect
     * 
     * @param effect The player's new particle effect
     */
    public void setEffect(ParticleEffect effect) {
        if (effect == null) 
            this.effect = getDefault().getEffect();
        else
            this.effect = effect;
    }

    /**
     * Sets the player's particle style
     * 
     * @param style The player's new particle style
     */
    public void setStyle(ParticleStyle style) {
        if (style == null) 
            this.style = getDefault().getStyle();
        else
            this.style = style;
    }

    /**
     * Sets the player's item material
     * 
     * @param itemMaterial The player's new item material
     */
    public void setItemMaterial(Material itemMaterial) {
        if (itemMaterial == null || itemMaterial.isBlock()) 
            this.itemMaterial = getDefault().getItemMaterial();
        else 
            this.itemMaterial = itemMaterial;
    }

    /**
     * Sets the player's block material
     * 
     * @param blockMaterial The player's new block material
     */
    public void setBlockMaterial(Material blockMaterial) {
        if (blockMaterial == null || !blockMaterial.isBlock()) 
            this.blockMaterial = getDefault().getBlockMaterial();
        else 
            this.blockMaterial = blockMaterial;
    }

    /**
     * Sets the player's color data
     * 
     * @param colorData The player's new color data
     */
    public void setColor(OrdinaryColor colorData) {
        if (colorData == null) 
            this.color = getDefault().getColor();
        else 
            this.color = colorData;
    }

    /**
     * Sets the player's note color data
     * 
     * @param noteColorData The player's new note color data
     */
    public void setNoteColor(NoteColor noteColorData) {
        if (noteColorData == null) 
            this.noteColor = getDefault().getNoteColor();
        else 
            this.noteColor = noteColorData;
    }

    /**
     * Get the UUID of the PPlayer that owns this ParticlePair
     * 
     * @return The owner's UUID
     */
    public UUID getOwnerUniqueId() {
        return this.ownerUUID;
    }

    /**
     * Get the id of this particle
     * 
     * @return The id of this particle
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the ParticleEffect that this ParticlePair represents
     * 
     * @return The effect
     */
    public ParticleEffect getEffect() {
        return this.effect;
    }

    /**
     * Get the ParticleStyle that this ParticlePair represents
     * 
     * @return The style
     */
    public ParticleStyle getStyle() {
        return this.style;
    }

    /**
     * Get the item Material this particle uses
     * 
     * @return The item Material
     */
    public Material getItemMaterial() {
        return this.itemMaterial;
    }

    /**
     * Get the block Material this particle uses
     * 
     * @return The block Material
     */
    public Material getBlockMaterial() {
        return this.blockMaterial;
    }

    /**
     * Get the color this particle uses
     * 
     * @return The color
     */
    public OrdinaryColor getColor() {
        return this.color;
    }

    /**
     * Get the note color this particle uses
     * 
     * @return The note color
     */
    public NoteColor getNoteColor() {
        return this.noteColor;
    }

    /**
     * Gets the color the current particle effect will spawn with
     * 
     * @return Gets the ParticleColor the current particle effect will spawn with
     */
    public ParticleColor getSpawnColor() {
        ParticleManager particleManager = PlayerParticles.getInstance().getManager(ParticleManager.class);

        if (this.effect.hasProperty(ParticleProperty.COLORABLE)) {
            if (this.effect == ParticleEffect.NOTE) {
                if (this.noteColor.equals(NoteColor.RAINBOW)) {
                    return particleManager.getRainbowNoteParticleColor();
                } else if (this.noteColor.equals(NoteColor.RANDOM)) {
                    return particleManager.getRandomNoteParticleColor();
                }
                return this.noteColor;
            } else {
                if (this.color.equals(OrdinaryColor.RAINBOW)) {
                    return particleManager.getRainbowParticleColor();
                } else if (this.color.equals(OrdinaryColor.RANDOM)) {
                    return particleManager.getRandomParticleColor();
                } else {
                    return this.color;
                }
            }
        }
        return null;
    }

    /**
     * Gets the material the current particle effect will spawn with
     * 
     * @return The Material the current particle effect requires
     */
    public Material getSpawnMaterial() {
        if (this.effect.hasProperty(ParticleProperty.REQUIRES_MATERIAL_DATA)) {
            if (this.effect == ParticleEffect.ITEM) {
                return this.itemMaterial;
            } else {
                return this.blockMaterial;
            }
        }
        return null;
    }

    /**
     * Gets the current particle data as a string
     * 
     * @return The particle data in a human-readable string
     */
    public String getDataString() {
        LocaleManager localeManager = PlayerParticles.getInstance().getManager(LocaleManager.class);
        if (this.effect == ParticleEffect.BLOCK || this.effect == ParticleEffect.FALLING_DUST) {
            return this.blockMaterial.toString().toLowerCase();
        } else if (this.effect == ParticleEffect.ITEM) {
            return this.itemMaterial.toString().toLowerCase();
        } else if (this.effect.hasProperty(ParticleProperty.COLORABLE)) {
            if (this.effect == ParticleEffect.NOTE) {
                if (this.noteColor.equals(NoteColor.RAINBOW)) {
                    return localeManager.getLocaleMessage("rainbow");
                } else if (this.noteColor.equals(NoteColor.RANDOM)) {
                    return localeManager.getLocaleMessage("random");
                }
                return localeManager.getLocaleMessage("gui-select-data-note", StringPlaceholders.single("note", this.noteColor.getNote()));
            } else {
                if (this.color.equals(OrdinaryColor.RAINBOW)) {
                    return localeManager.getLocaleMessage("rainbow");
                } else if (this.color.equals(OrdinaryColor.RANDOM)) {
                    return localeManager.getLocaleMessage("random");
                } else {
                    return ChatColor.RED + "" + this.color.getRed() + " " + ChatColor.GREEN + this.color.getGreen() + " " + ChatColor.AQUA + this.color.getBlue();
                }
            }
        }
        return localeManager.getLocaleMessage("gui-data-none");
    }
    
    /**
     * Gets a copy of this ParticlePair
     */
    public ParticlePair clone() {
        return new ParticlePair(this.ownerUUID, this.id, this.effect, this.style, this.itemMaterial, this.blockMaterial, this.color, this.noteColor);
    }
    
    /**
     * Gets a ParticlePair with the default values applied
     * Used for getting internal default values in the cases that null is specified
     * 
     * @return A ParticlePair with default values
     */
    private static ParticlePair getDefault() {
        return new ParticlePair(null, // @formatter:off
                                -1, 
                                ParticleEffect.FLAME, 
                                DefaultStyles.NORMAL,
                                ParticleUtils.closestMatchWithFallback(true, "IRON_SHOVEL", "IRON_SPADE"),
                                Material.STONE, 
                                new OrdinaryColor(0, 0, 0), 
                                new NoteColor(0)); // @formatter:on
    }

    /**
     * Gets a ParticlePair for a PPlayer with the default values applied
     * 
     * @param pplayer The PPlayer that will own this ParticlePair
     * @return A ParticlePair with default values
     */
    public static ParticlePair getNextDefault(PPlayer pplayer) {
        return new ParticlePair(pplayer.getUniqueId(), // @formatter:off
                                pplayer.getNextActiveParticleId(), 
    							ParticleEffect.FLAME, 
    							DefaultStyles.NORMAL, 
    							ParticleUtils.closestMatchWithFallback(true, "IRON_SHOVEL", "IRON_SPADE"), 
    							Material.STONE, 
    							new OrdinaryColor(0, 0, 0), 
    							new NoteColor(0)); // @formatter:on
    }

}
