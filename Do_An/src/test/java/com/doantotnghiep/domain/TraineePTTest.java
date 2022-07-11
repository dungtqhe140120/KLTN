package com.doantotnghiep.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.doantotnghiep.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraineePTTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraineePT.class);
        TraineePT traineePT1 = new TraineePT();
        traineePT1.setId(1L);
        TraineePT traineePT2 = new TraineePT();
        traineePT2.setId(traineePT1.getId());
        assertThat(traineePT1).isEqualTo(traineePT2);
        traineePT2.setId(2L);
        assertThat(traineePT1).isNotEqualTo(traineePT2);
        traineePT1.setId(null);
        assertThat(traineePT1).isNotEqualTo(traineePT2);
    }
}
