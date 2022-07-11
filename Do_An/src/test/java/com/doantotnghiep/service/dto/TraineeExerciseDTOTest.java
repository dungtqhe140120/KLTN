package com.doantotnghiep.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.doantotnghiep.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraineeExerciseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraineeExerciseDTO.class);
        TraineeExerciseDTO traineeExerciseDTO1 = new TraineeExerciseDTO();
        traineeExerciseDTO1.setId(1L);
        TraineeExerciseDTO traineeExerciseDTO2 = new TraineeExerciseDTO();
        assertThat(traineeExerciseDTO1).isNotEqualTo(traineeExerciseDTO2);
        traineeExerciseDTO2.setId(traineeExerciseDTO1.getId());
        assertThat(traineeExerciseDTO1).isEqualTo(traineeExerciseDTO2);
        traineeExerciseDTO2.setId(2L);
        assertThat(traineeExerciseDTO1).isNotEqualTo(traineeExerciseDTO2);
        traineeExerciseDTO1.setId(null);
        assertThat(traineeExerciseDTO1).isNotEqualTo(traineeExerciseDTO2);
    }
}
