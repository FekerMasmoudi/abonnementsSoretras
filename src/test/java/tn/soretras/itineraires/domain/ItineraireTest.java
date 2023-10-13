package tn.soretras.itineraires.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.itineraires.web.rest.TestUtil;

class ItineraireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Itineraire.class);
        Itineraire itineraire1 = new Itineraire();
        itineraire1.setId("id1");
        Itineraire itineraire2 = new Itineraire();
        itineraire2.setId(itineraire1.getId());
        assertThat(itineraire1).isEqualTo(itineraire2);
        itineraire2.setId("id2");
        assertThat(itineraire1).isNotEqualTo(itineraire2);
        itineraire1.setId(null);
        assertThat(itineraire1).isNotEqualTo(itineraire2);
    }
}
