package com.purpleprint.network.purpleprintemailbatch.job.dto;

import com.purpleprint.network.purpleprintemailbatch.heart.command.application.dto.RecipientDTO;
import com.purpleprint.network.purpleprintemailbatch.user.command.application.dto.PlayFriendDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * Class : MailDTO
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-25       전현정           최초 생성
 * </pre>
 *
 * @author 전현정(최초 작성자)
 * @version 1(클래스 버전)
 * @see
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

    private String childName;   //자녀계정이름
    private String parentsMail;
    private List<RecipientDTO> recipientList; //아이가 하트를 준 친구들
    private List<PlayFriendDTO> playFriendList; //아이와 친하게 논 친구들
    private String playPlace;   //논 장소(타입)
    private int conCurrentFriend; //같이 논 친구들 수
    private String playTime;
    private int giveHeartCount;     //하트를 준 개수
    private String comment;
    private String analysisDate;
}
