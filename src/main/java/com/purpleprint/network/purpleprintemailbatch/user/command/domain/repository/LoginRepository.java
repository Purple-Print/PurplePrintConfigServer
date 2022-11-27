package com.purpleprint.network.purpleprintemailbatch.user.command.domain.repository;

import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Class : LoginRepository
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
@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
    List<Login> findAllByChildIdAndLoginAtBetweenOrderByIdAsc(Integer id, Date date1, Date date2);
}
