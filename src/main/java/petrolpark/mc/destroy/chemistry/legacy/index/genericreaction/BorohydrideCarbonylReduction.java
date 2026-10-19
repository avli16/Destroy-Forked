package petrolpark.mc.destroy.chemistry.legacy.index.genericreaction;

import petrolpark.mc.destroy.Destroy;
import petrolpark.mc.destroy.chemistry.legacy.LegacyBond.BondType;
import petrolpark.mc.destroy.chemistry.legacy.LegacyElement;
import petrolpark.mc.destroy.chemistry.legacy.LegacyMolecularStructure;
import petrolpark.mc.destroy.chemistry.legacy.LegacyReaction;
import petrolpark.mc.destroy.chemistry.legacy.ReadOnlyMixture;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.GenericReactant;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyMolecules;
import petrolpark.mc.destroy.chemistry.legacy.index.group.CarbonylGroup;

public class BorohydrideCarbonylReduction extends SingleGroupGenericReaction<CarbonylGroup> {

    public BorohydrideCarbonylReduction() {
        super(Destroy.asResource("borohydride_carbonyl_reduction"), DestroyGroupTypes.CARBONYL);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.BOROHYDRIDE) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<CarbonylGroup> reactant) {
        CarbonylGroup carbonyl = reactant.getGroup();

        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        structure.moveTo(carbonyl.carbon)
                .addAtom(LegacyElement.HYDROGEN)
                .replaceBondTo(carbonyl.oxygen, BondType.SINGLE)
                .remove(carbonyl.oxygen)
                .addGroup(LegacyMolecularStructure.alcohol());

        return reactionBuilder()
                .addReactant(reactant.getMolecule(), 4)
                .addReactant(DestroyMolecules.WATER, 4) // workup
                .addReactant(DestroyMolecules.BOROHYDRIDE)
                .addProduct(moleculeBuilder().structure(structure).build(), 4)
                .addProduct(DestroyMolecules.TETRAHYDROXYBORATE)
                .activationEnergy(50f)
                .build();
    }
}
