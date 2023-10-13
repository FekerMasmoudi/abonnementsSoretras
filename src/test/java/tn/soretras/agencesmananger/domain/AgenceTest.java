package tn.soretras.agencesmananger.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.agencesmananger.web.rest.TestUtil;

class AgenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agence.class);
        Agence agence1 = new Agence();
        agence1.setId("id1");
        Agence agence2 = new Agence();
        agence2.setId(agence1.getId());
        assertThat(agence1).isEqualTo(agence2);
        agence2.setId("id2");
        assertThat(agence1).isNotEqualTo(agence2);
        agence1.setId(null);
        assertThat(agence1).isNotEqualTo(agence2);
    }
}
