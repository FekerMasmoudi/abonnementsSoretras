package tn.soretras.itineraires.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItineraireMapperTest {

    private ItineraireMapper itineraireMapper;

    @BeforeEach
    public void setUp() {
        itineraireMapper = new ItineraireMapperImpl();
    }
}
