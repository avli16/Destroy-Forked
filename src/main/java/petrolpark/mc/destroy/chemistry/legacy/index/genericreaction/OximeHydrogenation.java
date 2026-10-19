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
import petrolpark.mc.destroy.chemistry.legacy.index.group.OximeGroup;

public class OximeHydrogenation extends SingleGroupGenericReaction<OximeGroup> {

    public OximeHydrogenation() {
        super(Destroy.asResource("oxime_hydrogenation"), DestroyGroupTypes.OXIME);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.HYDROGEN) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<OximeGroup> reactant) {
        OximeGroup group = reactant.getGroup();
        LegacyMolecularStructure structure = reactant.molecule.shallowCopyStructure();

        structure.moveTo(group.carbon)
                .remove(group.nitrogen)
                .addAtom(LegacyElement.NITROGEN);
        structure.addAllHydrogens();

        return reactionBuilder()
                .addReactant(reactant.molecule)
                .addReactant(DestroyMolecules.HYDROGEN, 2)
                .addProduct(moleculeBuilder().structure(structure).build())
                .addProduct(DestroyMolecules.WATER)
                // TODO kinetics
                .build();
    }
}
