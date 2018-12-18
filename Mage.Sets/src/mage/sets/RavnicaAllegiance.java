package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

public final class RavnicaAllegiance extends ExpansionSet {

    private static final RavnicaAllegiance instance = new RavnicaAllegiance();

    public static RavnicaAllegiance getInstance() {
        return instance;
    }

    private RavnicaAllegiance() {
        super("Ravnica Allegiance", "RNA", ExpansionSet.buildDate(2019, 1, 25), SetType.EXPANSION);
        this.blockName = "Guilds of Ravnica";
        this.hasBoosters = true;
        this.hasBasicLands = false; // this is temporary until the actual basics have been spoiled to prevent a test fail
        this.numBoosterSpecial = 1;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 259;

        cards.add(new SetCardInfo("Growth Spiral", 178, Rarity.COMMON, mage.cards.g.GrowthSpiral.class));
        cards.add(new SetCardInfo("Mortify", 192, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("The Haunt of Hightower", 273, Rarity.MYTHIC, mage.cards.t.TheHauntOfHightower.class));
    }

    @Override
    public List<CardInfo> getCardsByRarity(Rarity rarity) {
        if (rarity == Rarity.COMMON) {
            List<CardInfo> savedCardsInfos = savedCards.get(rarity);
            if (savedCardsInfos == null) {
                CardCriteria criteria = new CardCriteria();
                criteria.setCodes(this.code).notTypes(CardType.LAND);
                criteria.rarities(rarity).doubleFaced(false);
                savedCardsInfos = CardRepository.instance.findCards(criteria);
                if (maxCardNumberInBooster != Integer.MAX_VALUE) {
                    savedCardsInfos.removeIf(next -> next.getCardNumberAsInt() > maxCardNumberInBooster);
                }
                savedCards.put(rarity, savedCardsInfos);
            }
            // Return a copy of the saved cards information, as not to modify the original.
            return new ArrayList<>(savedCardsInfos);
        } else {
            return super.getCardsByRarity(rarity);
        }
    }

    @Override
    public List<CardInfo> getSpecialCommon() {
        List<CardInfo> specialCards = getCardsByRarity(Rarity.SPECIAL);
        if (specialCards.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.rarities(Rarity.COMMON).setCodes(this.code).name("Guildgate");
            List<CardInfo> specialCardsSave = CardRepository.instance.findCards(criteria);
            savedCards.put(Rarity.SPECIAL, specialCardsSave);
            specialCards.addAll(specialCardsSave);
        }
        return specialCards;
    }
}