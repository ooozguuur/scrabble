package com.gunes.testing.services;

import com.gunes.dao.impl.BoardDaoImpl;
import com.gunes.service.impl.BoardServiceImpl;
import com.gunes.service.impl.MoveServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CellServiceTest {

    @Mock
    private MoveServiceImpl moveService;

    @Mock
    private BoardDaoImpl boardDao;

    @InjectMocks
    private BoardServiceImpl boardService;

}
