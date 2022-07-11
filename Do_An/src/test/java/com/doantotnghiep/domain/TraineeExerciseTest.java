package com.doantotnghiep.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.doantotnghiep.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraineeExerciseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraineeExercise.class);
        TraineeExercise traineeExercise1 = new TraineeExercise();
        traineeExercise1.setId(1L);
        TraineeExercise traineeExercise2 = new TraineeExercise();
        traineeExercise2.setId(traineeExercise1.getId());
        assertThat(traineeExercise1).isEqualTo(traineeExercise2);
        traineeExercise2.setId(2L);
        assertThat(traineeExercise1).isNotEqualTo(traineeExercise2);
        traineeExercise1.setId(null);
        assertThat(traineeExercise1).isNotEqualTo(traineeExercise2);
    }
}
