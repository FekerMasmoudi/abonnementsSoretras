package tn.soretras.centremanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CenterMapperTest {

    private CenterMapper centerMapper;

    @BeforeEach
    public void setUp() {
        centerMapper = new CenterMapperImpl();
    }
}
