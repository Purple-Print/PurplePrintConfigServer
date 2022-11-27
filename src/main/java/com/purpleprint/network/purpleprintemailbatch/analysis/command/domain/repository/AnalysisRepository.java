package com.purpleprint.network.purpleprintemailbatch.analysis.command.domain.repository;

import com.purpleprint.network.purpleprintemailbatch.analysis.command.domain.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Class : AnalysisRepositroy
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
public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {

    List<Analysis> findAllBySendYn(String n);
}
