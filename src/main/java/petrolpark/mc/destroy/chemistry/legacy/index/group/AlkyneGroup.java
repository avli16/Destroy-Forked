package petrolpark.mc.destroy.chemistry.legacy.index.group;

import petrolpark.mc.destroy.chemistry.legacy.LegacyAtom;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;
import petrolpark.mc.destroy.chemistry.legacy.LegacyFunctionalGroupType;

public class AlkyneGroup extends SaturatedCarbonGroup {

    public AlkyneGroup(LegacyAtom highDegreeCarbon, LegacyAtom lowDegreeCarbon, Boolean hasHydrogen) {
        super(highDegreeCarbon, lowDegreeCarbon, hasHydrogen);
    };

    @Override
    public LegacyFunctionalGroupType<? extends SaturatedCarbonGroup> getType() {
        return DestroyGroupTypes.ALKYNE;
    };
    
};
