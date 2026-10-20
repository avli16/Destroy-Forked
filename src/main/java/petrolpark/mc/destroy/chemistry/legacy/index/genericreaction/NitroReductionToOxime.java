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
import petrolpark.mc.destroy.chemistry.legacy.index.group.NitroGroup;

public class NitroReductionToOxime extends SingleGroupGenericReaction<NitroGroup> {

    public NitroReductionToOxime() {
        super(Destroy.asResource("nitro_reduction_to_oxime"), DestroyGroupTypes.NITRO);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture readOnlyMixture) {
        return readOnlyMixture.getConcentrationOf(DestroyMolecules.CHROMIUM_II) > 0f && readOnlyMixture.getConcentrationOf(DestroyMolecules.PROTON) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<NitroGroup> reactant) {
        NitroGroup group = reactant.getGroup();
        if (group.carbon == null) // FIXME This reaction requires an alpha hydrogen
          return null;

        LegacyMolecularStructure structure = reactant.molecule.shallowCopyStructure();
        structure.moveTo(group.carbon)
                .remove(group.nitrogen)
                .remove(group.firstOxygen)
                .remove(group.secondOxygen)
                .addGroup(LegacyMolecularStructure.atom(LegacyElement.NITROGEN)
                                .addGroup(LegacyMolecularStructure.alcohol()),
                        true, LegacyBond.BondType.DOUBLE);

        if (reactant.molecule.isHypothetical())
            structure.addAllHydrogens();

        return reactionBuilder()
                .addReactant(reactant.getMolecule())
                .addReactant(DestroyMolecules.PROTON, 2)
                .addReactant(DestroyMolecules.CHROMIUM_II, 2)
                .addProduct(moleculeBuilder().structure(structure).build())
                .addProduct(DestroyMolecules.CHROMIUM_III, 2)
                .addProduct(DestroyMolecules.WATER)
                // TODO kinetics
                .build();
    }
}
