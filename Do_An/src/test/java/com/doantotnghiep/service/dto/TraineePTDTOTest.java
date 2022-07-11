package com.doantotnghiep.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.doantotnghiep.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraineePTDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraineePTDTO.class);
        TraineePTDTO traineePTDTO1 = new TraineePTDTO();
        traineePTDTO1.setId(1L);
        TraineePTDTO traineePTDTO2 = new TraineePTDTO();
        assertThat(traineePTDTO1).isNotEqualTo(traineePTDTO2);
        traineePTDTO2.setId(traineePTDTO1.getId());
        assertThat(traineePTDTO1).isEqualTo(traineePTDTO2);
        traineePTDTO2.setId(2L);
        assertThat(traineePTDTO1).isNotEqualTo(traineePTDTO2);
        traineePTDTO1.setId(null);
        assertThat(traineePTDTO1).isNotEqualTo(traineePTDTO2);
    }
}
