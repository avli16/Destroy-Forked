package petrolpark.mc.destroy.chemistry.legacy.index.genericreaction;

import petrolpark.mc.destroy.Destroy;
import petrolpark.mc.destroy.chemistry.legacy.LegacyBond;
import petrolpark.mc.destroy.chemistry.legacy.LegacyElement;
import petrolpark.mc.destroy.chemistry.legacy.LegacyMolecularStructure;
import petrolpark.mc.destroy.chemistry.legacy.LegacyReaction;
import petrolpark.mc.destroy.chemistry.legacy.LegacySpecies;
import petrolpark.mc.destroy.chemistry.legacy.ReadOnlyMixture;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.GenericReactant;
import petrolpark.mc.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyGroupTypes;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyMolecules;
import petrolpark.mc.destroy.chemistry.legacy.index.group.SaturatedCarbonGroup;

public class AlkyneHydrolysis extends SingleGroupGenericReaction<SaturatedCarbonGroup> {

    public AlkyneHydrolysis() {
        super(Destroy.asResource("alkyne_hydrolysis"), DestroyGroupTypes.ALKYNE);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.WATER) > 0f && mixture.getConcentrationOf(DestroyMolecules.PROTON) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<SaturatedCarbonGroup> reactant) {
        SaturatedCarbonGroup group = reactant.getGroup();
        LegacySpecies substrate = reactant.getMolecule();
        LegacySpecies product = moleculeBuilder().structure(substrate
                .shallowCopyStructure()
                .moveTo(group.highDegreeCarbon)
                .replaceBondTo(group.lowDegreeCarbon, LegacyBond.BondType.SINGLE)
                .addAtom(LegacyElement.OXYGEN, LegacyBond.BondType.DOUBLE)
                .moveTo(group.lowDegreeCarbon)
                .addGroup(LegacyMolecularStructure.atom(LegacyElement.HYDROGEN))
                .addGroup(LegacyMolecularStructure.atom(LegacyElement.HYDROGEN))
        ).build();

        return reactionBuilder()
                .addReactant(reactant.getMolecule(), 1, 1)
                .addReactant(DestroyMolecules.WATER)
                .addCatalyst(DestroyMolecules.PROTON, 2)
                .addProduct(product, 1)
                .activationEnergy(10f)
                .displayAsReversible()
                .build();
    }
}
