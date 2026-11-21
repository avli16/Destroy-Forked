package petrolpark.mc.destroy.chemistry.legacy.index.group;

import petrolpark.mc.destroy.chemistry.legacy.LegacyAtom;
import petrolpark.mc.destroy.chemistry.legacy.LegacyFunctionalGroup;
import petrolpark.mc.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class DiazoniumGroup extends LegacyFunctionalGroup<DiazoniumGroup> {
    public final LegacyAtom carbon;
    public final LegacyAtom bondedNitrogen;
    public final LegacyAtom terminalNitrogen;

    public DiazoniumGroup(LegacyAtom carbon, LegacyAtom bondedNitrogen, LegacyAtom terminalNitrogen) {
        this.carbon = carbon;
        this.bondedNitrogen = bondedNitrogen;
        this.terminalNitrogen = terminalNitrogen;
    }

    @Override
    public LegacyFunctionalGroupType<DiazoniumGroup> getType() {
        return DestroyGroupTypes.DIAZONIUM;
    }

}
