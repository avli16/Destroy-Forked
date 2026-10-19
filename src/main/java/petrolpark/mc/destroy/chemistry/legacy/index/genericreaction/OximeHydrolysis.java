package petrolpark.mc.destroy.chemistry.legacy.index.genericreaction;

import petrolpark.mc.destroy.Destroy;
import petrolpark.mc.destroy.chemistry.legacy.LegacyMolecularStructure;
import petrolpark.mc.destroy.chemistry.legacy.LegacyReaction;
import petrolpark.mc.destroy.chemistry.legacy.ReadOnlyMixture;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.GenericReactant;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyMolecules;
import petrolpark.mc.destroy.chemistry.legacy.index.group.OximeGroup;

public class OximeHydrolysis extends SingleGroupGenericReaction<OximeGroup> {

    public OximeHydrolysis() {
        super(Destroy.asResource("oxime_hydrolysis"), DestroyGroupTypes.OXIME);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.WATER) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<OximeGroup> reactant) {
        OximeGroup group = reactant.getGroup();
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();

        structure.moveTo(group.carbon)
                .remove(group.nitrogen)
                .remove(group.oxygen)
                .addCarbonyl();

        if (reactant.molecule.isHypothetical())
            structure.addAllHydrogens();

        return reactionBuilder()
                .addReactant(reactant.getMolecule())
                .addReactant(DestroyMolecules.WATER)
                .addReactant(DestroyMolecules.PROTON)
                .addProduct(moleculeBuilder().structure(structure).build())
                .addProduct(DestroyMolecules.HYDROXYLAMMONIUM)
                // TODO kinetics
                .build();
    }
}
