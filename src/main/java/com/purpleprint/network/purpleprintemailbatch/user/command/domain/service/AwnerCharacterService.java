package com.purpleprint.network.purpleprintemailbatch.user.command.domain.service;

import com.purpleprint.network.purpleprintemailbatch.character.command.domain.model.Character;
import com.purpleprint.network.purpleprintemailbatch.character.command.domain.model.CharacterFile;

/**
 * <pre>
 * Class : OwnerCharacterService
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
public interface AwnerCharacterService {
    CharacterFile selectAwnerCharacter(Integer id);
}
