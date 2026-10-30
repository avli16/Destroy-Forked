package petrolpark.mc.destroy.chemistry.legacy.index.genericreaction;

import petrolpark.mc.destroy.Destroy;
import petrolpark.mc.destroy.chemistry.legacy.LegacyReaction;
import petrolpark.mc.destroy.chemistry.legacy.ReadOnlyMixture;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.GenericReactant;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;
import petrolpark.mc.destroy.chemistry.legacy.index.group.SaturatedCarbonGroup;

public class AlkyneDeprotonation extends SingleGroupGenericReaction<SaturatedCarbonGroup> {

    public AlkyneDeprotonation() {
        super(Destroy.asResource("alkyne_deprotonation"), DestroyGroupTypes.ALKYNE);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return true;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<SaturatedCarbonGroup> reactant) {
        return null;
    }
}
