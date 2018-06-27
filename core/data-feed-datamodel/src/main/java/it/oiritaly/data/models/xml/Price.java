package it.oiritaly.data.models.xml;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Price {
    private String currency;
    private BigDecimal value;
}
