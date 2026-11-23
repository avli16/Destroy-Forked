package petrolpark.mc.destroy.chemistry.legacy.index.genericreaction;

import petrolpark.mc.destroy.Destroy;
import petrolpark.mc.destroy.DestroyItems;
import petrolpark.mc.destroy.chemistry.legacy.LegacyAtom;
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
import petrolpark.mc.destroy.chemistry.legacy.index.group.AlkyneGroup;
import petrolpark.mc.destroy.chemistry.legacy.index.group.SaturatedCarbonGroup;

import java.util.List;

public class AlkyneDeprotonation extends SingleGroupGenericReaction<SaturatedCarbonGroup> {

    public AlkyneDeprotonation() {
        super(Destroy.asResource("alkyne_deprotonation"), DestroyGroupTypes.ALKYNE);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return true;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<SaturatedCarbonGroup> reactant) {
        SaturatedCarbonGroup alkyne = reactant.getGroup();
        if (!alkyne.hasHydrogen) return null;
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        List<LegacyAtom> hydrogens = structure.moveTo(alkyne.lowDegreeCarbon).getBondedAtomsOfElement(LegacyElement.HYDROGEN);
        if (hydrogens.isEmpty()) return null;
        structure.remove(hydrogens.getFirst())
                .moveTo(alkyne.highDegreeCarbon)
                .remove(alkyne.lowDegreeCarbon)
                .addAtom(new LegacyAtom(LegacyElement.CARBON, -1), LegacyBond.BondType.TRIPLE);

        return reactionBuilder()
                .addReactant(reactant.getMolecule(), 1)
                .addSimpleItemReactant(DestroyItems.SODIUM_HYDRIDE::get, 1f)
                .addProduct(moleculeBuilder().structure(structure).build(), 1)
                .addProduct(DestroyMolecules.SODIUM_ION,1)
                .addProduct(DestroyMolecules.HYDROGEN,1)
                .build();
    }

    @Override
    public LegacyReaction generateExampleReaction() {
        LegacyAtom lowDegreeCarbon = new LegacyAtom(LegacyElement.CARBON);
        LegacyAtom highDegreeCarbon = new LegacyAtom(LegacyElement.CARBON);
        LegacyAtom rGroup = new LegacyAtom(LegacyElement.R_GROUP);
        rGroup.rGroupNumber = 1;
        LegacySpecies exampleMolecule = moleculeBuilder().structure(
                new LegacyMolecularStructure(highDegreeCarbon)
                        .addAtom(rGroup)
                        .addAtom(lowDegreeCarbon, LegacyBond.BondType.TRIPLE)
                        .moveTo(lowDegreeCarbon)
                        .addAtom(LegacyElement.HYDROGEN)

        ).build();
        return generateReaction(new GenericReactant<>(exampleMolecule, new AlkyneGroup(highDegreeCarbon, lowDegreeCarbon, true) {}));
    }
}
