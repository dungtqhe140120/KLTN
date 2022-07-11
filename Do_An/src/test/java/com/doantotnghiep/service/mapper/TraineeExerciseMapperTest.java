package com.doantotnghiep.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraineeExerciseMapperTest {

    private TraineeExerciseMapper traineeExerciseMapper;

    @BeforeEach
    public void setUp() {
        traineeExerciseMapper = new TraineeExerciseMapperImpl();
    }
}
