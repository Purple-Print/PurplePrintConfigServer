package com.purpleprint.network.purpleprintemailbatch.heart.command.infra.service;

import com.purpleprint.network.purpleprintemailbatch.heart.command.domain.service.RecipientService;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.Child;
import com.purpleprint.network.purpleprintemailbatch.user.query.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Class : RecipientServiceImpl
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
@Service
public class RecipientServiceImpl implements RecipientService {

    private final UserService userService;

    @Autowired
    public RecipientServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Child selectRecipient(int recipient) {
        return userService.selectRecipient(recipient);
    }
}
