package petrolpark.mc.destroy.chemistry.legacy.index.genericreaction;

import petrolpark.mc.destroy.Destroy;
import petrolpark.mc.destroy.chemistry.legacy.LegacyBond;
import petrolpark.mc.destroy.chemistry.legacy.LegacyElement;
import petrolpark.mc.destroy.chemistry.legacy.LegacyMolecularStructure;
import petrolpark.mc.destroy.chemistry.legacy.LegacyReaction;
import petrolpark.mc.destroy.chemistry.legacy.ReadOnlyMixture;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.GenericReactant;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyMolecules;
import petrolpark.mc.destroy.chemistry.legacy.index.group.CarbonylGroup;

public class OximeFormation extends SingleGroupGenericReaction<CarbonylGroup> {

    public OximeFormation() {
        super(Destroy.asResource("oxime_formation"), DestroyGroupTypes.CARBONYL);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.HYDROXYLAMINE) > 0f;
    }

    public LegacyReaction generateReaction(GenericReactant<CarbonylGroup> reactant) {
        LegacyMolecularStructure structure = reactant.molecule.shallowCopyStructure();
        structure.moveTo(reactant.group.carbon)
                .remove(reactant.group.oxygen)
                .addGroup(LegacyMolecularStructure.atom(LegacyElement.NITROGEN)
                                .addGroup(LegacyMolecularStructure.alcohol()),
                        true, LegacyBond.BondType.DOUBLE);

        if (reactant.molecule.isHypothetical())
            structure.addAllHydrogens();

        return reactionBuilder()
                .addReactant(reactant.molecule)
                .addReactant(DestroyMolecules.HYDROXYLAMINE)
                .addProduct(moleculeBuilder().structure(structure).build())
                .addProduct(DestroyMolecules.WATER)
                // TODO kinetics
                .build();
    }
}
