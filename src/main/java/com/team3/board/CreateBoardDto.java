package com.team3.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBoardDto {
    private String title;
    private String content;
    private Integer userId;  // User의 ID를 받도록 설정
}
