package com.purpleprint.network.purpleprintemailbatch.analysis.command.query.service;

import com.purpleprint.network.purpleprintemailbatch.analysis.command.domain.model.Analysis;
import com.purpleprint.network.purpleprintemailbatch.analysis.command.domain.repository.AnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Class : AnalysisService
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
public class AnalysisService {

    private final AnalysisRepository analysisRepository;

    @Autowired
    public AnalysisService(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }


    public List<Analysis> selectAnalysisList() {
        return analysisRepository.findAllBySendYn("N");
    }
}
