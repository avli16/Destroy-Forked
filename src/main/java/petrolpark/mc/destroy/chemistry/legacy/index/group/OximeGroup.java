package petrolpark.mc.destroy.chemistry.legacy.index.group;

import petrolpark.mc.destroy.chemistry.legacy.LegacyAtom;
import petrolpark.mc.destroy.chemistry.legacy.LegacyFunctionalGroup;
import petrolpark.mc.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class OximeGroup extends LegacyFunctionalGroup<OximeGroup> {
    public final LegacyAtom carbon;
    public final LegacyAtom nitrogen;
    public final LegacyAtom oxygen;
    public final LegacyAtom hydrogen;

    // Whether this oxime is a ketoxime. If false, it is an aldoxime or its classification is unknown
    public final boolean isKetoxime;

    public OximeGroup(LegacyAtom carbon, LegacyAtom nitrogen, LegacyAtom oxygen, LegacyAtom hydrogen, boolean isKetoxime) {
        super();
        this.carbon = carbon;
        this.nitrogen = nitrogen;
        this.oxygen = oxygen;
        this.hydrogen = hydrogen;
        this.isKetoxime = isKetoxime;
    };

    @Override
    public LegacyFunctionalGroupType<OximeGroup> getType() {
        return DestroyGroupTypes.OXIME;
    };
};
