package petrolpark.mc.destroy.chemistry.legacy;

import petrolpark.mc.destroy.chemistry.legacy.genericreaction.GenericReaction;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyMolecules;

import java.util.List;
import java.util.Map;

public class LegacyChemistryUtils {
    public static LegacySpecies getHalideIon(LegacyAtom atom) {
        LegacyElement el = atom.getElement();
        if (el == LegacyElement.FLUORINE) return DestroyMolecules.FLUORIDE;
        if (el == LegacyElement.CHLORINE) return DestroyMolecules.CHLORIDE;
        if (el == LegacyElement.BROMINE) return DestroyMolecules.BROMIDE;
        if (el == LegacyElement.IODINE) return DestroyMolecules.IODIDE;
        throw new GenericReaction.GenericReactionGenerationException(el.toString() + " is not a halogen.");
    }

    public static LegacySpecies getHalideAcid(LegacyAtom atom) {
        LegacyElement el = atom.getElement();
        if (el == LegacyElement.FLUORINE) return DestroyMolecules.HYDROFLUORIC_ACID;
        if (el == LegacyElement.CHLORINE) return DestroyMolecules.HYDROCHLORIC_ACID;
        if (el == LegacyElement.BROMINE) return DestroyMolecules.HYDROGEN_BROMIDE;
        if (el == LegacyElement.IODINE) return DestroyMolecules.HYDROGEN_IODIDE;
        throw new GenericReaction.GenericReactionGenerationException(el.toString() + " is not a halogen.");
    }

    public static boolean isAromatic(Map<LegacyAtom, List<LegacyBond>> structure, LegacyAtom atom) {
        for (LegacyBond bond : structure.get(atom)) {
            if (bond.getType() == LegacyBond.BondType.AROMATIC) return true;
        }

        return false;
    }
}
