package com.justanothervitor.api_2.models.Enums;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

@Table(name = "enumTags")
public enum EnumTag {
    BILLS,
    ENTERTAINMENT,
    UPKEEP,
    FOOD,
    ELECTRONICS,
    FUEL
}
