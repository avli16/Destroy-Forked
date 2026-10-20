package petrolpark.mc.destroy.chemistry.legacy.index.genericreaction;

import petrolpark.mc.destroy.Destroy;
import petrolpark.mc.destroy.chemistry.legacy.LegacyElement;
import petrolpark.mc.destroy.chemistry.legacy.LegacyMolecularStructure;
import petrolpark.mc.destroy.chemistry.legacy.LegacyReaction;
import petrolpark.mc.destroy.chemistry.legacy.ReadOnlyMixture;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.GenericReactant;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyMolecules;
import petrolpark.mc.destroy.chemistry.legacy.index.group.CarbonylGroup;

public class LeuckartReaction extends SingleGroupGenericReaction<CarbonylGroup> {

    public LeuckartReaction() {
        super(Destroy.asResource("leuckart_reaction"), DestroyGroupTypes.CARBONYL);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.FORMIC_ACID) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<CarbonylGroup> reactant) {
        CarbonylGroup group = reactant.getGroup();
        LegacyMolecularStructure structure = reactant.molecule.shallowCopyStructure();
        structure.moveTo(group.carbon)
                .remove(group.oxygen)
                .addAtom(LegacyElement.HYDROGEN)
                .addGroup(LegacyMolecularStructure.atom(LegacyElement.NITROGEN)
                                .addAtom(LegacyElement.HYDROGEN)
                                .addAtom(LegacyElement.HYDROGEN));

        return reactionBuilder()
                .addReactant(reactant.getMolecule())
                .addReactant(DestroyMolecules.FORMIC_ACID) // formate and
                .addReactant(DestroyMolecules.AMMONIA) // ammonium?
                .addProduct(DestroyMolecules.WATER)
                .addProduct(DestroyMolecules.CARBON_DIOXIDE)
                .addProduct(moleculeBuilder().structure(structure).build())
                // TODO kinetics
                .build();
    }
}
