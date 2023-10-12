package tn.soretras.stationsmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.stationsmanager.web.rest.TestUtil;

class StationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Station.class);
        Station station1 = new Station();
        station1.setId("id1");
        Station station2 = new Station();
        station2.setId(station1.getId());
        assertThat(station1).isEqualTo(station2);
        station2.setId("id2");
        assertThat(station1).isNotEqualTo(station2);
        station1.setId(null);
        assertThat(station1).isNotEqualTo(station2);
    }
}
