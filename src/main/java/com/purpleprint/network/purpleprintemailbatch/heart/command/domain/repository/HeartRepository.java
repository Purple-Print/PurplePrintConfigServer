package com.purpleprint.network.purpleprintemailbatch.heart.command.domain.repository;

import com.purpleprint.network.purpleprintemailbatch.heart.command.domain.model.Heart;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Class : HeartRepository
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

@Repository
public interface HeartRepository extends JpaRepository<Heart, Integer> {
    List<Heart> findByGiverAndGaveAtBetween(int childId, Date date1, Date date2);
}
