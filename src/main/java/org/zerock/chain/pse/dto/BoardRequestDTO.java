package org.zerock.chain.pse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDTO {

    private Long boardNo;
    private String boardCategory;
    private String boardName;
    private String boardContent;
    private String boardLocation;
    private String boardFiles;
    private String boardAuthor;
    private LocalDate boardUploadDate = LocalDate.now();


}
