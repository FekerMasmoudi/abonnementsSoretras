package tn.soretras.stationsmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.stationsmanager.web.rest.TestUtil;

class StationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StationDTO.class);
        StationDTO stationDTO1 = new StationDTO();
        stationDTO1.setId("id1");
        StationDTO stationDTO2 = new StationDTO();
        assertThat(stationDTO1).isNotEqualTo(stationDTO2);
        stationDTO2.setId(stationDTO1.getId());
        assertThat(stationDTO1).isEqualTo(stationDTO2);
        stationDTO2.setId("id2");
        assertThat(stationDTO1).isNotEqualTo(stationDTO2);
        stationDTO1.setId(null);
        assertThat(stationDTO1).isNotEqualTo(stationDTO2);
    }
}
