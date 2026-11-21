package petrolpark.mc.destroy.chemistry.legacy;

import petrolpark.mc.destroy.chemistry.legacy.genericreaction.GenericReaction;
import petrolpark.mc.destroy.chemistry.legacy.index.DestroyMolecules;

public class IonUtil {
    public static LegacySpecies getHalideIon(LegacyAtom atom) {
        LegacyElement el = atom.getElement();
        if (el == LegacyElement.FLUORINE) return DestroyMolecules.FLUORIDE;
        if (el == LegacyElement.CHLORINE) return DestroyMolecules.CHLORIDE;
        if (el == LegacyElement.BROMINE) return DestroyMolecules.BROMIDE;
        if (el == LegacyElement.IODINE) return DestroyMolecules.IODIDE;
        throw new GenericReaction.GenericReactionGenerationException(el.toString() + " is not a halogen.");
    }
}
