package it.oiritaly.batch.processors.utils;

import it.oiritaly.data.models.amazon.*;

/**
 * Created by marco on 13/04/17.
 */
public class AmazonCategoryUtils {

    public enum Category {
        ANELLI(FineRing.class, 1),
        COLLANE(FineNecklaceBraceletAnklet.class, 3),
        BRACCIALI(FineNecklaceBraceletAnklet.class, 6),
        CHARMS(FineNecklaceBraceletAnklet.class, 7),
        FEDI(FineRing.class, 8),
        GEMELLI(FineOther.class, 9),
        ORECCHINI(FineEarring.class, 10),
        PENDENTI(FineOther.class, 11),
        PIETRE_DA_INVESTIMENTO(FineOther.class, 12),
        SPILLE(FineOther.class, 13),
        ACCESSORI_PER_OROLOGI(Watch.class, 14),
        ANALOGICI(Watch.class, 15),
        CRONOGRAFI(Watch.class, 16),
        DIGITALI(Watch.class, 17),
        DUAL_TIME(Watch.class, 18),
        MECCANICI(Watch.class, 19),
        MULTIFUNZIONE(Watch.class, 20),
        QUARZO(Watch.class, 21),
        SOLO_TEMPO(Watch.class, 22),
        OLTRE_10_ATM_WATERPROOF(Watch.class, 23),
        FINO_10_ATM_WATER_RESISTANT(Watch.class, 24),
        SOLARE(Watch.class, 25),
        AUTOMATICI(Watch.class, 26),
        PIERCING(FineOther.class, 43),
        DA_TASCA(Watch.class, 47),
        WATER_RESISTANT_03_ATM(FineOther.class, 54),
        WATER_RESISTANT_05_ATM(FineOther.class, 55);

        private Class value;
        private long code;

        Category(Class value, long code) {
            this.value = value;
            this.code= code;
        }

        public Class getAmazonClass() {
            return value;
        }

        public long getCode() { return code; }

        public static Category getEnum(Class value) {
            for(Category v : values())
                if(v.getAmazonClass().equals(value)) return v;
            throw new IllegalArgumentException();
        }

        public static Category getEnum(long code) {
            for(Category v : values())
                if(v.getCode() == code) return v;
            throw new IllegalArgumentException();
        }
    }

}
