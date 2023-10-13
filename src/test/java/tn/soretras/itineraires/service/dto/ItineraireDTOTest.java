package tn.soretras.itineraires.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.itineraires.web.rest.TestUtil;

class ItineraireDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItineraireDTO.class);
        ItineraireDTO itineraireDTO1 = new ItineraireDTO();
        itineraireDTO1.setId("id1");
        ItineraireDTO itineraireDTO2 = new ItineraireDTO();
        assertThat(itineraireDTO1).isNotEqualTo(itineraireDTO2);
        itineraireDTO2.setId(itineraireDTO1.getId());
        assertThat(itineraireDTO1).isEqualTo(itineraireDTO2);
        itineraireDTO2.setId("id2");
        assertThat(itineraireDTO1).isNotEqualTo(itineraireDTO2);
        itineraireDTO1.setId(null);
        assertThat(itineraireDTO1).isNotEqualTo(itineraireDTO2);
    }
}
