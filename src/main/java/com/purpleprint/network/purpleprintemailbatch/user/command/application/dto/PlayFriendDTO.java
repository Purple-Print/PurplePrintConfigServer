package com.purpleprint.network.purpleprintemailbatch.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Class : PlayFriendDTO
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-24       전현정           최초 생성
 * </pre>
 *
 * @author 전현정(최초 작성자)
 * @version 1(클래스 버전)
 * @see
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayFriendDTO {
    private int friendId;
    private String friendName;
    private String friendCharacterUrl;

}
