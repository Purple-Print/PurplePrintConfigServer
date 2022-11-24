package com.purpleprint.network.purpleprintemailbatch.user.query.service;

import com.purpleprint.network.purpleprintemailbatch.analysis.command.domain.model.Analysis;
import com.purpleprint.network.purpleprintemailbatch.character.command.domain.model.Character;
import com.purpleprint.network.purpleprintemailbatch.character.command.domain.model.CharacterFile;
import com.purpleprint.network.purpleprintemailbatch.user.command.application.dto.PlayFriendDTO;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.Child;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.repository.ChildRepository;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.service.AwnerCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Class : userService
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
@Service("{query.UserService}")
public class UserService {

    private final ChildRepository childRepository;
    private final AwnerCharacterService awnerCharacterService;

    @Autowired
    public UserService(ChildRepository childRepository, AwnerCharacterService awnerCharacterService) {
        this.childRepository = childRepository;
        this.awnerCharacterService = awnerCharacterService;
    }

    public Child selectRecipient(int recipient) {

        return childRepository.findById(recipient).get();
    }
}
