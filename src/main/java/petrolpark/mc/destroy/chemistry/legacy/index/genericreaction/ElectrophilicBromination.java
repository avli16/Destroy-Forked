package petrolpark.mc.destroy.chemistry.legacy.index.genericreaction;

import petrolpark.mc.destroy.Destroy;
import petrolpark.mc.destroy.chemistry.legacy.LegacyElement;
import petrolpark.mc.destroy.chemistry.legacy.LegacyMolecularStructure;
import petrolpark.mc.destroy.chemistry.legacy.LegacySpecies;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyMolecules;

public class ElectrophilicBromination extends ElectrophilicAddition {

    public ElectrophilicBromination(boolean alkyne) {
        super(Destroy.MOD_ID, "bromination", alkyne);
    };

    @Override
    public LegacyMolecularStructure getLowDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.BROMINE);
    };

    @Override
    public LegacyMolecularStructure getHighDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.BROMINE);
    };

    @Override
    public LegacySpecies getElectrophile() {
        return DestroyMolecules.BROMINE;
    };

};