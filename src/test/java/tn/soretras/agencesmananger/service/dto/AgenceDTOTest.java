package tn.soretras.agencesmananger.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.agencesmananger.web.rest.TestUtil;

class AgenceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgenceDTO.class);
        AgenceDTO agenceDTO1 = new AgenceDTO();
        agenceDTO1.setId("id1");
        AgenceDTO agenceDTO2 = new AgenceDTO();
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
        agenceDTO2.setId(agenceDTO1.getId());
        assertThat(agenceDTO1).isEqualTo(agenceDTO2);
        agenceDTO2.setId("id2");
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
        agenceDTO1.setId(null);
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
    }
}
