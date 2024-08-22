package com.team3.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateBoardDto {
    private String title;
    private String content;
    private Integer userId;
}
