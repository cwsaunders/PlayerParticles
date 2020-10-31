package dev.esophose.playerparticles.particles;

import java.util.Collections;
import java.util.UUID;
import org.bukkit.command.CommandSender;

public class OtherPPlayer extends PPlayer {
    
    private CommandSender sender;

    public OtherPPlayer(CommandSender sender) {
        super(UUID.randomUUID(), Collections.emptyMap(), Collections.emptyMap(), false);

        this.sender = sender;
    }
    
    public OtherPPlayer(CommandSender sender, PPlayer other) {
        super(other.getUniqueId(), other.getParticleGroups(), other.getFixedParticlesMap(), !other.canSeeParticles());
        
        this.sender = sender;
    }

    /**
     * @return the underlying CommandSender who executed the command
     */
    @Override
    public CommandSender getUnderlyingExecutor() {
        return this.sender;
    }

}
