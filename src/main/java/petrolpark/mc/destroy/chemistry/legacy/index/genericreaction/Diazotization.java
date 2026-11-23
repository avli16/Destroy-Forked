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
import petrolpark.mc.destroy.chemistry.legacy.index.group.PrimaryAmineGroup;

public class Diazotization extends SingleGroupGenericReaction<PrimaryAmineGroup> {

    public Diazotization() {
        super(Destroy.asResource("diazotization"), DestroyGroupTypes.PRIMARY_AMINE);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.NITROUS_ACID) > 0f && mixture.getConcentrationOf(DestroyMolecules.PROTON) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<PrimaryAmineGroup> reactant) {
        // FIXME: currently crashes
        LegacyMolecularStructure structure = reactant.molecule.shallowCopyStructure();
        structure.moveTo(reactant.group.carbon)
                .remove(reactant.group.nitrogen)
                .remove(reactant.group.firstHydrogen)
                .remove(reactant.group.secondHydrogen)
                .addGroup(LegacyMolecularStructure.atom(LegacyElement.NITROGEN, 1)
                        .addGroup(LegacyMolecularStructure.atom(LegacyElement.NITROGEN), true, LegacyBond.BondType.TRIPLE));
        // TODO check if group.carbon is aromatic
        if(reactant.molecule.isHypothetical())
            structure.addAllHydrogens();

        return reactionBuilder()
                .addReactant(reactant.molecule)
                .addReactant(DestroyMolecules.NITROUS_ACID)
                .addReactant(DestroyMolecules.PROTON, 1, 0)
                .addProduct(moleculeBuilder().structure(structure).build())
                .addProduct(DestroyMolecules.WATER, 2) // We're taking in account creating the nitrosonium too. In the future it'd be better to make it separate ig
                .build();
    }
}
