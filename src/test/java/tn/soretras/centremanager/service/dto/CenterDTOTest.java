package tn.soretras.centremanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.centremanager.web.rest.TestUtil;

class CenterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterDTO.class);
        CenterDTO centerDTO1 = new CenterDTO();
        centerDTO1.setId("id1");
        CenterDTO centerDTO2 = new CenterDTO();
        assertThat(centerDTO1).isNotEqualTo(centerDTO2);
        centerDTO2.setId(centerDTO1.getId());
        assertThat(centerDTO1).isEqualTo(centerDTO2);
        centerDTO2.setId("id2");
        assertThat(centerDTO1).isNotEqualTo(centerDTO2);
        centerDTO1.setId(null);
        assertThat(centerDTO1).isNotEqualTo(centerDTO2);
    }
}
