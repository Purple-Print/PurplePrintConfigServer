package com.purpleprint.network.purpleprintemailbatch.user.query.service;

import com.purpleprint.network.purpleprintemailbatch.analysis.command.domain.model.Analysis;
import com.purpleprint.network.purpleprintemailbatch.character.command.domain.model.CharacterFile;
import com.purpleprint.network.purpleprintemailbatch.user.command.application.dto.PlayFriendDTO;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.Child;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.Login;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.Logout;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.repository.ChildRepository;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.repository.LoginRepository;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.repository.LogoutRepository;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.service.AwnerCharacterService;
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
    private final LoginRepository loginRepository;
    private final LogoutRepository logoutRepository;
    private final AwnerCharacterService awnerCharacterService;

    @Autowired
    public UserService(ChildRepository childRepository, AwnerCharacterService awnerCharacterService,
                       LoginRepository loginRepository, LogoutRepository logoutRepository) {
        this.childRepository = childRepository;
        this.awnerCharacterService = awnerCharacterService;
        this.loginRepository = loginRepository;
        this.logoutRepository = logoutRepository;
    }

    public Child selectRecipient(int recipient) {

        return childRepository.findById(recipient).get();
    }

    public List<PlayFriendDTO> selectFriendList(Analysis analysis) {

        List<PlayFriendDTO> playFriendList = new ArrayList<>();

        if(analysis.getFriend1() != 0) {
            PlayFriendDTO playFriendDTO = new PlayFriendDTO();

            Child child = childRepository.findById(analysis.getFriend1()).get();

            playFriendDTO.setFriendId(child.getId());
            playFriendDTO.setFriendName(child.getName());
            playFriendDTO.setFriendComment(analysis.getFriend1Comment());
            CharacterFile characterfile = awnerCharacterService.selectAwnerCharacter(child.getId());

            playFriendDTO.setFriendCharacterUrl(characterfile.getUrl());
            playFriendList.add(playFriendDTO);
        }

        if(analysis.getFriend2() != 0) {
            PlayFriendDTO playFriendDTO = new PlayFriendDTO();

            Child child = childRepository.findById(analysis.getFriend2()).get();

            playFriendDTO.setFriendId(child.getId());
            playFriendDTO.setFriendName(child.getName());
            playFriendDTO.setFriendComment(analysis.getFriend2Comment());
            CharacterFile characterfile = awnerCharacterService.selectAwnerCharacter(child.getId());

            playFriendDTO.setFriendCharacterUrl(characterfile.getUrl());
            playFriendList.add(playFriendDTO);
        }

        return playFriendList;
    }

    public String selectPlayTime(Integer id, Date analysisAt) {

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

        List<Login> loginList = loginRepository.findAllByChildIdAndLoginAtBetweenOrderByIdAsc(id, date1, date2);

        long playtime = 0;
        String alpha = "";
        for(Login login : loginList) {
            Logout logout = logoutRepository.findByLoginId(login.getId());
            if(logout == null) {
                alpha = "α";
                break;
            }
            System.out.println(playtime);
            System.out.println( logout.getLogoutAt().getTime() - login.getLoginAt().getTime());
            playtime = playtime + logout.getLogoutAt().getTime() - login.getLoginAt().getTime();

            System.out.println(playtime);
        }

        String playTime = "";
        int minute = 0;


        if((playtime / (60 * 60 * 1000)) != 0) {
            playTime = playTime + (playtime / (60 * 60 * 1000)) + "시간 ";
        }

        if((playtime / (60 * 1000)) != 0) {
            playTime = playTime + ((playtime / (60 * 1000)) -  (playtime / (60 * 60 * 1000) * 60)) + "분 ";
        }

        if((playtime / 1000) != 0) {
            playTime = playTime + ((playtime / 1000) -(playtime / (60 * 1000) * 60))  + "초 ";
        }

        if(alpha.equals("α")) {
            playTime = playTime + "+ α(게임 플레이 중)";
        }

        return playTime;
    }
}
