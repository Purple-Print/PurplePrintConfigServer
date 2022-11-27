package com.purpleprint.network.purpleprintemailbatch.user.command.infra.service;

import com.purpleprint.network.purpleprintemailbatch.character.command.domain.model.Character;
import com.purpleprint.network.purpleprintemailbatch.character.command.domain.model.CharacterFile;
import com.purpleprint.network.purpleprintemailbatch.character.query.service.CharacterService;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.service.AwnerCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * Class : AwnserCharacterServiceImpl
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

@Repository
public class AwnserCharacterServiceImpl implements AwnerCharacterService {

    private final CharacterService characterService;

    @Autowired
    public AwnserCharacterServiceImpl(CharacterService characterService) {
        this.characterService = characterService;
    }
    @Override
    public CharacterFile selectAwnerCharacter(Integer id) {
        return characterService.selectCharacter(id);
    }
}
