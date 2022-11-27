package com.purpleprint.network.purpleprintemailbatch.heart.query.service;

import com.purpleprint.network.purpleprintemailbatch.character.command.domain.model.CharacterFile;
import com.purpleprint.network.purpleprintemailbatch.heart.command.application.dto.RecipientDTO;
import com.purpleprint.network.purpleprintemailbatch.heart.command.domain.model.Heart;
import com.purpleprint.network.purpleprintemailbatch.heart.command.domain.repository.HeartRepository;
import com.purpleprint.network.purpleprintemailbatch.heart.command.domain.service.RecipientCharacterService;
import com.purpleprint.network.purpleprintemailbatch.heart.command.domain.service.RecipientService;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Class : HeartService
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-21       전현정           최초 생성
 * </pre>
 *
 * @author 전현정(최초 작성자)
 * @version 1(클래스 버전)
 * @see
 */

@Service
public class HeartService {

    private final HeartRepository heartRepository;
    private final RecipientService recipientService;
    private final RecipientCharacterService recipientCharacterService;

    @Autowired
    public HeartService(HeartRepository heartRepository, RecipientService recipientService, RecipientCharacterService recipientCharacterService) {
        this.heartRepository = heartRepository;
        this.recipientService = recipientService;
        this.recipientCharacterService = recipientCharacterService;
    }

    public List<RecipientDTO> selectHeartList(Child child, Date analysisAt) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String t1 = dateFormat.format(analysisAt) + " 00:00:00";
        String t2 = dateFormat.format(analysisAt) + " 23:59:59";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(t1);
            date2 = formatter.parse(t2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<Heart> heartList = heartRepository.findByGiverAndGaveAtBetween(child.getId(), date1, date2);
        List<RecipientDTO> heartGiveFriendList = new ArrayList<>();
        heartList.forEach(heart -> {
            RecipientDTO friendDTO = new RecipientDTO();

            Child recipient = recipientService.selectRecipient(heart.getRecipient());

            CharacterFile characterFile = recipientCharacterService.selectRecipientCharacter(recipient.getId());

            friendDTO.setFriendId(recipient.getId());
            friendDTO.setFriendName(recipient.getName());
            friendDTO.setFriendCharacterUrl(characterFile.getUrl());
            heartGiveFriendList.add(friendDTO);
        });

        return heartGiveFriendList;
    }
}
