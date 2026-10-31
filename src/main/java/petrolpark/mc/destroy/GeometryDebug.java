package petrolpark.mc.destroy;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import petrolpark.mc.destroy.core.chemistry.MoleculeRenderer;

import java.util.Locale;

@EventBusSubscriber(modid = Destroy.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class GeometryDebug {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("geometry_debug")
                        .then(Commands.argument("geometry", StringArgumentType.word())
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();

                                    MoleculeRenderer.Geometry geometry;
                                    try {
                                        geometry = MoleculeRenderer.Geometry.valueOf(
                                                StringArgumentType.getString(context, "geometry")
                                                        .toUpperCase(Locale.ROOT)
                                        );
                                    } catch (IllegalArgumentException e) {
                                        context.getSource().sendFailure(
                                                Component.literal("Unknown geometry!")
                                        );
                                        return 0;
                                    }

                                    renderGeometry(player.serverLevel(), player.position().add(0, 2, 0), geometry);

                                    return 1;
                                })
                        )
        );
    }

    public static void renderGeometry(ServerLevel level, Vec3 origin, MoleculeRenderer.Geometry geometry) {
        double scale = 1.5d;
        int steps = 20;

        level.sendParticles(ParticleTypes.FLAME, origin.x, origin.y, origin.z, 1, 0, 0, 0, 0);
        for (Vec3 direction : geometry.connections) {
            for (int i = 1; i <= steps; i++) {
                double distance = scale * i / steps;
                Vec3 point = origin.add(direction.scale(distance));

                level.sendParticles(ParticleTypes.END_ROD, point.x, point.y, point.z, 1, 0, 0, 0, 0);
            }
        }
    }
}